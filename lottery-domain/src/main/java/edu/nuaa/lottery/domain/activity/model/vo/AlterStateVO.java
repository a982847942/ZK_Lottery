package edu.nuaa.lottery.domain.activity.model.vo;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 10:37
 */
public class AlterStateVO {
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 变更前的状态
     */
    private Integer beforeState;
    /**
     * 变更后的状态
     */
    private Integer afterState;

    public AlterStateVO(Long activityId, Integer beforeState, Integer afterState) {
        this.activityId = activityId;
        this.beforeState = beforeState;
        this.afterState = afterState;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getBeforeState() {
        return beforeState;
    }

    public void setBeforeState(Integer beforeState) {
        this.beforeState = beforeState;
    }

    public Integer getAfterState() {
        return afterState;
    }

    public void setAfterState(Integer afterState) {
        this.afterState = afterState;
    }

    @Override
    public String toString() {
        return "AlterStateVO{" +
                "activityId=" + activityId +
                ", beforeState=" + beforeState +
                ", afterState=" + afterState +
                '}';
    }
}
