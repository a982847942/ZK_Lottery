package edu.nuaa.lottery.application.process.res;

import edu.nuaa.lottery.common.Result;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/3 14:52
 */
public class RuleQuantificationCrowdResult extends Result {
    /** 活动ID */
    private Long activityId;

    public RuleQuantificationCrowdResult(String code, String info) {
        super(code, info);
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
