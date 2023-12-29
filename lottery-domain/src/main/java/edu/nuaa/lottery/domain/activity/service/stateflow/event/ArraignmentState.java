package edu.nuaa.lottery.domain.activity.service.stateflow.event;

import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.common.Constants;
import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.domain.activity.service.stateflow.AbstractState;
import edu.nuaa.lottery.domain.activity.service.stateflow.IStateHandler;
import org.springframework.stereotype.Component;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 11:31
 */
@Component
public class ArraignmentState extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "待审核状态不可重复提审");
    }

    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, ActivityConstants.ActivityState.PASS);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核通过完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, ActivityConstants.ActivityState.REFUSE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核拒绝完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, ActivityConstants.ActivityState.EDIT);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核撤销回到编辑中") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result close(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, ActivityConstants.ActivityState.CLOSE);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动审核关闭完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "非关闭活动不可开启");
    }

    @Override
    public Result doing(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "待审核活动不可执行活动中变更");
    }
}
