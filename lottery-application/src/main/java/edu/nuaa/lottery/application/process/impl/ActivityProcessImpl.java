package edu.nuaa.lottery.application.process.impl;

import edu.nuaa.lottery.application.process.IActivityProcess;
import edu.nuaa.lottery.application.process.req.DrawProcessReq;
import edu.nuaa.lottery.application.process.res.DrawProcessResult;
import edu.nuaa.lottery.application.process.res.RuleQuantificationCrowdResult;
import edu.nuaa.lottery.common.*;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;
import edu.nuaa.lottery.domain.activity.model.vo.DrawOrderVO;
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
import org.springframework.stereotype.Service;

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
        DrawAwardVO drawAwardVO = drawRes.getDrawAwardInfo();
        //3.结果落库
        activityPartake.recordDrawOrder(buildDrawOrderVO(drawProcessReq, strategyId, takeId, drawAwardVO));
        // 4. 发送MQ，触发发奖流程

        // 5. 返回结果
        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardVO);
    }

    @Override
    public RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req) {
        // 1. 量化决策
        EngineResult engineResult = engineFilter.process(req);

        if (!engineResult.isSuccess()) {
            return new RuleQuantificationCrowdResult(Constants.ResponseCode.RULE_ERR.getCode(),Constants.ResponseCode.RULE_ERR.getInfo());
        }

        // 2. 封装结果
        RuleQuantificationCrowdResult ruleQuantificationCrowdResult = new RuleQuantificationCrowdResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        ruleQuantificationCrowdResult.setActivityId(Long.valueOf(engineResult.getNodeValue()));

        return ruleQuantificationCrowdResult;
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
