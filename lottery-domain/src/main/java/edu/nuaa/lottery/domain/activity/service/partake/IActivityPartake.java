package edu.nuaa.lottery.domain.activity.service.partake;

import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;
import edu.nuaa.lottery.domain.activity.model.vo.DrawOrderVO;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 14:49
 */
public interface IActivityPartake {
    /**
     * 参与活动
     *
     * @param parTakeReq 入参
     * @return 领取结果
     */
    ParTakeRes doParTake(ParTakeReq parTakeReq);

    /**
     * 保存奖品单
     *
     * @param drawOrder 奖品单
     * @return 保存结果
     */
    Result recordDrawOrder(DrawOrderVO drawOrder);

    /**
     * 更新发货单MQ状态
     *
     * @param uId     用户ID
     * @param orderId 订单ID
     * @param mqState MQ 发送状态
     */
    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);
}
