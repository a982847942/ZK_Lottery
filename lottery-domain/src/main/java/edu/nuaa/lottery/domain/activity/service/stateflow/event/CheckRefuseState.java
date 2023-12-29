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
public class CheckRefuseState extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "已审核状态不可重复提审");
    }

    @Override
    public Result checkPass(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "已审核状态不可重复审核");
    }

    @Override
    public Result checkRefuse(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动审核拒绝不可重复审核");
    }

    @Override
    public Result checkRevoke(Long activityId, Enum<ActivityConstants.ActivityState> currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, ActivityConstants.ActivityState.EDIT);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "撤销审核完成") : Result.buildErrorResult("活动状态变更失败");
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
        return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "审核拒绝不可执行活动为进行中");
    }
}
