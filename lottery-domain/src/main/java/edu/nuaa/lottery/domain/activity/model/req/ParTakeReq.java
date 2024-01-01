package edu.nuaa.lottery.domain.activity.model.req;

import java.util.Date;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 15:43
 */
public class ParTakeReq {
    private String uId;
    private Long activityId;
    private Date parTakeDate;

    public ParTakeReq(String uId, Long activityId, Date parTakeDate) {
        this.uId = uId;
        this.activityId = activityId;
        this.parTakeDate = parTakeDate;
    }

    public ParTakeReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Date getParTakeDate() {
        return parTakeDate;
    }

    public void setParTakeDate(Date parTakeDate) {
        this.parTakeDate = parTakeDate;
    }

    @Override
    public String toString() {
        return "ParTakeReq{" +
                "uId='" + uId + '\'' +
                ", activityId=" + activityId +
                ", parTakeDate=" + parTakeDate +
                '}';
    }
}
