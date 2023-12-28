package edu.nuaa.lottery.rpc.req;

import java.io.Serializable;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 19:03
 */
public class ActivityReq implements Serializable {
    private Long activityId;

    public ActivityReq(Long activityId) {
        this.activityId = activityId;
    }

    public ActivityReq() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
