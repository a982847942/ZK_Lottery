package edu.nuaa.lottery.rpc.res;

import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.rpc.dto.ActivityDto;

import java.io.Serializable;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 19:03
 */
public class ActivityRes implements Serializable {
    private Result result;
    private ActivityDto activityDto;

    public ActivityRes(Result result, ActivityDto activityDto) {
        this.result = result;
        this.activityDto = activityDto;
    }

    public ActivityRes(Result result) {
        this(result,null);
    }

    public ActivityRes() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public ActivityDto getActivityDto() {
        return activityDto;
    }

    public void setActivityDto(ActivityDto activityDto) {
        this.activityDto = activityDto;
    }
}
