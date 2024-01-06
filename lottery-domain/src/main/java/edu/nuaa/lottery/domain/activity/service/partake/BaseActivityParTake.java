package edu.nuaa.lottery.domain.activity.service.partake;

import edu.nuaa.lottery.common.Constants;
import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.common.SupportConstants;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;
import edu.nuaa.lottery.domain.activity.model.vo.ActivityBillVO;
import edu.nuaa.lottery.domain.activity.model.vo.UserTakeActivityVO;
import edu.nuaa.lottery.domain.activity.repository.IUserTakeActivityRepository;
import edu.nuaa.lottery.domain.suppot.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 17:17
 */
public abstract class BaseActivityParTake extends ActivityParTakeSupport implements IActivityPartake{
    //IdContext中配置由该bean
    @Resource
    private Map<SupportConstants.IDS, IIdGenerator> idGeneratorMap;
    @Override
    public ParTakeRes doParTake(ParTakeReq parTakeReq) {
        /**
         * 1.先跟去请求的ActivityId和uId去take_activity表中查询是否有未消费的活动单 state=0
         */
        // TODO: 2024/1/6 查询时候根据activityId和uId可能会有多条记录符合条件，这一块可以根据需求进行修改
        UserTakeActivityVO userTakeActivityVO = this.queryNoConsumedTakeActivityOrder(parTakeReq.getActivityId(), parTakeReq.getuId());
        if (null != userTakeActivityVO){
            return buildParTakeResult(userTakeActivityVO.getStrategyId(),userTakeActivityVO.getTakeId());
        }
        // 2.查询活动账单
        ActivityBillVO activityBillVO = super.queryActivityBills(parTakeReq);

        // 3.活动信息校验处理【活动库存、状态、日期、个人参与次数】
        Result checkResult = this.checkActivityBill(parTakeReq,activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())){
            return new ParTakeRes(checkResult.getCode(), checkResult.getInfo());
        }
        // 4.扣减活动库存【目前为直接对配置库中的 lottery.activity 直接操作表扣减库存，后续优化为Redis扣减】
        Result subtractionResult = this.subtractionActivityStock(parTakeReq);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(subtractionResult.getCode())){
            return new ParTakeRes(subtractionResult.getCode(),subtractionResult.getInfo());
        }
        // 5.领取活动信息【个人用户把活动信息写入到用户表】
        // TODO: 2024/1/2 扣减活动库存和领取活动是不是应该原子完成？
        Long takeId = idGeneratorMap.get(SupportConstants.IDS.SnowFlake).nextId();
        Result grabResult = this.grabActivity(parTakeReq,activityBillVO,takeId);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())){
            return new ParTakeRes(grabResult.getCode(), grabResult.getInfo());
        }
        // 6.封装结果【返回的策略ID，用于继续完成抽奖步骤】
//        ParTakeRes parTakeRes = new ParTakeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
//        parTakeRes.setStrategyId(activityBillVO.getStrategyId());
//        return parTakeRes;
        return buildParTakeResult(activityBillVO.getStrategyId(), takeId);
    }

    private ParTakeRes buildParTakeResult(Long strategyId, Long takeId) {
        ParTakeRes parTakeRes = new ParTakeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        parTakeRes.setTakeId(takeId);
        parTakeRes.setStrategyId(strategyId);
        return parTakeRes;
    }

    /**
     * 领取活动
     *
     * @param parTakeReq 参与活动请求
     * @param activityBillVO   活动账单
     * @param takeId  领取活动ID
     * @return 领取结果
     */

    protected abstract Result grabActivity(ParTakeReq parTakeReq, ActivityBillVO activityBillVO,Long takeId);

    /**
     * 扣减活动库存
     *
     * @param parTakeReq 参与活动请求
     * @return 扣减结果
     */
    protected abstract Result subtractionActivityStock(ParTakeReq parTakeReq);
    /**
     * 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
     *
     * @param activityId 活动ID
     * @param uId        用户ID
     * @return 领取单
     */
    protected abstract UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);

    /**
     * 活动信息校验处理，把活动库存、状态、日期、个人参与次数
     *
     * @param parTakeReq 参与活动请求
     * @param activityBillVO    活动账单
     * @return 校验结果
     */
    protected abstract Result checkActivityBill(ParTakeReq parTakeReq, ActivityBillVO activityBillVO);
}
