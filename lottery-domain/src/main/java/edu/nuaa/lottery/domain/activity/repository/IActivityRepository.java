package edu.nuaa.lottery.domain.activity.repository;

import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.vo.*;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 10:53
 */
public interface IActivityRepository {
    /**
     * 添加活动配置
     * @param activity 活动配置
     */
    void addActivity(ActivityVO activity);

    /**
     * 添加奖品配置集合
     *
     * @param awardList 奖品配置集合
     */
    void addAward(List<AwardVO> awardList);

    /**
     * 添加策略配置
     *
     * @param strategy 策略配置
     */
    void addStrategy(StrategyVO strategy);

    /**
     * 添加策略明细配置
     *
     * @param strategyDetailList 策略明细集合
     */
    void addStrategyDetailList(List<StrategyDetailVO> strategyDetailList);
    /**
     * 变更活动状态
     *
     * @param activityId    活动ID
     * @param beforeState   修改前状态
     * @param afterState    修改后状态
     * @return              更新结果
     */
    boolean alterStatus(Long activityId, Enum<ActivityConstants.ActivityState> beforeState,Enum<ActivityConstants.ActivityState> afterState);

    /**
     * 查询活动账单信息【库存、状态、日期、个人参与次数】
     * @param parTakeReq 参与活动请求
     * @return    活动账单
     */
    ActivityBillVO queryActivityBills(ParTakeReq parTakeReq);

    /**
     * 扣减活动库存
     * @param activityId   活动ID
     * @return      扣减结果
     */
    int subtractionActivityStock(Long activityId);


}
