package edu.nuaa.lottery.application.process.impl;

import edu.nuaa.lottery.application.process.IActivityProcess;
import edu.nuaa.lottery.application.process.req.DrawProcessReq;
import edu.nuaa.lottery.application.process.res.DrawProcessResult;
import edu.nuaa.lottery.common.AwardConstants;
import edu.nuaa.lottery.common.Constants;
import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.common.SupportConstants;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;
import edu.nuaa.lottery.domain.activity.model.vo.DrawOrderVO;
import edu.nuaa.lottery.domain.activity.service.partake.IActivityPartake;
import edu.nuaa.lottery.domain.strategy.model.req.DrawReq;
import edu.nuaa.lottery.domain.strategy.model.res.DrawRes;
import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardInfo;
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
        DrawAwardInfo drawAwardInfo = drawRes.getDrawAwardInfo();
        //3.结果落库
        activityPartake.recordDrawOrder(buildDrawOrderVO(drawProcessReq, strategyId, takeId, drawAwardInfo));
        // 4. 发送MQ，触发发奖流程

        // 5. 返回结果
        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(),drawAwardInfo);
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardInfo drawAwardInfo) {
        long orderId = idGeneratorMap.get(SupportConstants.IDS.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setuId(req.getuId());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawAwardInfo.getStrategyMode());
        drawOrderVO.setGrantType(drawAwardInfo.getGrantType());
        drawOrderVO.setGrantDate(drawAwardInfo.getGrantDate());
        drawOrderVO.setGrantState(AwardConstants.GrantState.INIT.getCode());
        drawOrderVO.setAwardId(drawAwardInfo.getAwardId());
        drawOrderVO.setAwardType(drawAwardInfo.getAwardType());
        drawOrderVO.setAwardName(drawAwardInfo.getAwardName());
        drawOrderVO.setAwardContent(drawAwardInfo.getAwardContent());
        return drawOrderVO;
    }
}
