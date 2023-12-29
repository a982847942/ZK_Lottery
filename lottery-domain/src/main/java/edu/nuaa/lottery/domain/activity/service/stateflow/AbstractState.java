package edu.nuaa.lottery.domain.activity.service.stateflow;

import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 11:13
 */
public abstract class AbstractState {
    @Resource
    public IActivityRepository activityRepository;
    /**
     * 提审
     * @param activityId    活动ID
     * @param currentState 当前状态
     * @return              审核结果
     */
    public abstract Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentState);

    /**
     * 审核通过
     * @param activityId    活动ID
     * @param currentState 当前状态
     * @return              审核结果
     */
    public abstract Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentState);

    /**
     * 审核拒绝
     * @param activityId    活动ID
     * @param currentState 当前状态
     * @return              审核结果
     */
    public abstract Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentState);

    /**
     * 撤销审核
     * @param activityId    活动ID
     * @param currentState 当前状态
     * @return              审核结果
     */
    public abstract Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentState);

    /**
     * 关闭
     * @param activityId    活动ID
     * @param currentState 当前状态
     * @return              审核结果
     */
    public abstract Result close(Long activityId, Enum<ActivityConstants.ActivityState> currentState);

    /**
     * 开启
     * @param activityId    活动ID
     * @param currentState 当前状态
     * @return              审核结果
     */
    public abstract Result open(Long activityId, Enum<ActivityConstants.ActivityState> currentState);

    /**
     * 运行活动中
     * @param activityId    活动ID
     * @param currentState 当前状态
     * @return              审核结果
     */
    public abstract Result doing(Long activityId, Enum<ActivityConstants.ActivityState> currentState);
}
