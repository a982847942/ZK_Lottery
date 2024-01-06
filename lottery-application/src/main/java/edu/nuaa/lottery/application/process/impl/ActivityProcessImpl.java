package edu.nuaa.lottery.application.process.impl;

import edu.nuaa.lottery.application.mq.producer.KafkaProducer;
import edu.nuaa.lottery.application.process.IActivityProcess;
import edu.nuaa.lottery.application.process.req.DrawProcessReq;
import edu.nuaa.lottery.application.process.res.DrawProcessResult;
import edu.nuaa.lottery.application.process.res.RuleQuantificationCrowdResult;
import edu.nuaa.lottery.common.*;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;
import edu.nuaa.lottery.domain.activity.model.vo.DrawOrderVO;
import edu.nuaa.lottery.domain.activity.model.vo.InvoiceVO;
import edu.nuaa.lottery.domain.activity.service.partake.IActivityPartake;
import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.model.res.EngineResult;
import edu.nuaa.lottery.domain.rule.service.engine.EngineFilter;
import edu.nuaa.lottery.domain.strategy.model.req.DrawReq;
import edu.nuaa.lottery.domain.strategy.model.res.DrawRes;
import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardVO;
import edu.nuaa.lottery.domain.strategy.service.draw.IDrawExec;
import edu.nuaa.lottery.domain.suppot.ids.IIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/1 21:20
 */
@Service
public class ActivityProcessImpl implements IActivityProcess {
    private final Logger logger = LoggerFactory.getLogger(ActivityProcessImpl.class);
    @Resource
    IActivityPartake activityPartake;
    @Resource
    IDrawExec drawExec;
    @Resource
    Map<SupportConstants.IDS, IIdGenerator> idGeneratorMap;
    @Resource
    EngineFilter engineFilter;
    
    @Resource
    KafkaProducer kafkaProducer;

    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq drawProcessReq) {
        //1.领取活动
        ParTakeRes parTakeRes = activityPartake.doParTake(new ParTakeReq(drawProcessReq.getuId(), drawProcessReq.getActivityId(),new Date()));
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(parTakeRes.getCode())){
            return new DrawProcessResult(parTakeRes.getCode(), parTakeRes.getInfo());
        }
        Long strategyId = parTakeRes.getStrategyId();
        Long takeId = parTakeRes.getTakeId();
        //2.执行抽奖
        DrawRes drawRes = drawExec.doDrawExec(new DrawReq(drawProcessReq.getuId(), strategyId, String.valueOf(takeId)));
        if (DrawAlgorithmConstants.DrawState.FAIL.equals(drawRes.getDrawState())){
            //未中奖
            return new DrawProcessResult(Constants.ResponseCode.LOSING_DRAW.getCode(), Constants.ResponseCode.LOSING_DRAW.getInfo());
        }

        //3.结果落库 更新take_activity中的state状态，写入信息到use_strategy_export
        DrawAwardVO drawAwardVO = drawRes.getDrawAwardInfo();
        DrawOrderVO drawOrderVO = buildDrawOrderVO(drawProcessReq, strategyId, takeId, drawAwardVO);
        Result recordResult = activityPartake.recordDrawOrder(drawOrderVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(recordResult.getCode())){
            return new DrawProcessResult(recordResult.getCode(), recordResult.getInfo());
        }
        // 4. 发送MQ，触发发奖流程
        InvoiceVO invoiceVO = buildInvoiceVO(drawOrderVO);
        ListenableFuture<SendResult<String, Object>> sendResultFuture = kafkaProducer.sendLotteryInvoice(invoiceVO);
        sendResultFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                //打印日志
                logger.info(KafkaProducer.TOPIC_INVOICE + " 生产者发送消息失败: " + ex.getMessage());
                //发送失败，更新消息发送失败，更新数据库表 user_strategy_export.mq_state = 2 【等待定时任务扫码补偿MQ消息】
                //或者由于某些原因可能会存在持续 user_strategy_export.mq_state = 0 也可用定时任务触发
                activityPartake.updateInvoiceMqState(invoiceVO.getuId(),invoiceVO.getOrderId(), MQConstants.MQState.FAIL.getCode());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                //打印日志
                logger.info(KafkaProducer.TOPIC_INVOICE + " 生产者发送消息发送成功: " + result.toString());
                //更新状态,结果落库
                // 4.1 MQ 消息发送完成，更新数据库表 user_strategy_export.mq_state = 1
                activityPartake.updateInvoiceMqState(invoiceVO.getuId(), invoiceVO.getOrderId(), MQConstants.MQState.COMPLETE.getCode());
            }
        });
        // 5. 返回结果
        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardVO);
    }

    @Override
    public RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req) {
        // 1. 量化决策
        EngineResult engineResult = engineFilter.process(req);

        if (!engineResult.isSuccess()) {
            return new RuleQuantificationCrowdResult(Constants.ResponseCode.RULE_ERR.getCode(), Constants.ResponseCode.RULE_ERR.getInfo());
        }

        // 2. 封装结果
        RuleQuantificationCrowdResult ruleQuantificationCrowdResult = new RuleQuantificationCrowdResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        ruleQuantificationCrowdResult.setActivityId(Long.valueOf(engineResult.getNodeValue()));

        return ruleQuantificationCrowdResult;
    }

    private InvoiceVO buildInvoiceVO(DrawOrderVO drawOrderVO) {
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setuId(drawOrderVO.getuId());
        invoiceVO.setOrderId(drawOrderVO.getOrderId());
        invoiceVO.setAwardId(drawOrderVO.getAwardId());
        invoiceVO.setAwardType(drawOrderVO.getAwardType());
        invoiceVO.setAwardName(drawOrderVO.getAwardName());
        invoiceVO.setAwardContent(drawOrderVO.getAwardContent());
        invoiceVO.setShippingAddress(null);
        invoiceVO.setExtInfo(null);
        return invoiceVO;
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardVO drawAwardVO) {
        long orderId = idGeneratorMap.get(SupportConstants.IDS.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setuId(req.getuId());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawAwardVO.getStrategyMode());
        drawOrderVO.setGrantType(drawAwardVO.getGrantType());
        drawOrderVO.setGrantDate(drawAwardVO.getGrantDate());
        drawOrderVO.setGrantState(AwardConstants.GrantState.INIT.getCode());
        drawOrderVO.setAwardId(drawAwardVO.getAwardId());
        drawOrderVO.setAwardType(drawAwardVO.getAwardType());
        drawOrderVO.setAwardName(drawAwardVO.getAwardName());
        drawOrderVO.setAwardContent(drawAwardVO.getAwardContent());
        return drawOrderVO;
    }
}
