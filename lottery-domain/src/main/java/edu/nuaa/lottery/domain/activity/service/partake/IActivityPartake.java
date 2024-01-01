package edu.nuaa.lottery.domain.activity.service.partake;

import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 14:49
 */
public interface IActivityPartake {
    /**
     * 参与活动
     * @param parTakeReq 入参
     * @return    领取结果
     */
    ParTakeRes doParTake(ParTakeReq parTakeReq);
}
