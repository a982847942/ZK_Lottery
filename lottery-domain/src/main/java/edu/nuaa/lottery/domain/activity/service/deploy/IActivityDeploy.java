package edu.nuaa.lottery.domain.activity.service.deploy;

import edu.nuaa.lottery.domain.activity.model.req.ActivityConfigReq;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 14:51
 */
public interface IActivityDeploy {
    /**
     * 创建活动信息
     *
     * @param activityConfigReq 活动配置信息
     */
    void createActivity(ActivityConfigReq activityConfigReq);
    /**
     * 修改活动信息
     *
     * @param activityConfigReq 活动配置信息
     */
    void updateActivity(ActivityConfigReq activityConfigReq);
}
