package edu.nuaa.lottery.domain.activity.service.partake;

import edu.nuaa.lottery.common.Constants;
import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;
import edu.nuaa.lottery.domain.activity.model.vo.ActivityBillVO;
import edu.nuaa.lottery.domain.activity.repository.IUserTakeActivityRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 17:17
 */
public abstract class BaseActivityParTake extends ActivityParTakeSupport implements IActivityPartake{
    @Override
    public ParTakeRes doParTake(ParTakeReq parTakeReq) {
        // 1.查询活动账单
        ActivityBillVO activityBillVO = activityRepository.queryActivityBills(parTakeReq);

        // 2.活动信息校验处理【活动库存、状态、日期、个人参与次数】
        Result checkResult = this.checkActivityBill(parTakeReq,activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())){
            return new ParTakeRes(checkResult.getCode(), checkResult.getInfo());
        }
        // 3.扣减活动库存【目前为直接对配置库中的 lottery.activity 直接操作表扣减库存，后续优化为Redis扣减】
        Result subtractionResult = this.subtractionActivityStock(parTakeReq);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(subtractionResult.getCode())){
            return new ParTakeRes(subtractionResult.getCode(),subtractionResult.getInfo());
        }
        // 4.领取活动信息【个人用户把活动信息写入到用户表】
        Result grabResult = this.grabActivity(parTakeReq,activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult)){
            return new ParTakeRes(grabResult.getCode(), grabResult.getInfo());
        }
        // 5.封装结果【返回的策略ID，用于继续完成抽奖步骤】
        ParTakeRes parTakeRes = new ParTakeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        parTakeRes.setStrategyId(activityBillVO.getStrategyId());
        return parTakeRes;
    }

    protected abstract Result grabActivity(ParTakeReq parTakeReq, ActivityBillVO activityBillVO);

    protected abstract Result subtractionActivityStock(ParTakeReq parTakeReq);

    protected abstract Result checkActivityBill(ParTakeReq parTakeReq, ActivityBillVO activityBillVO);
}
