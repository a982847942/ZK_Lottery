package edu.nuaa.lottery.rpc;

import edu.nuaa.lottery.rpc.req.DrawReq;
import edu.nuaa.lottery.rpc.req.QuantificationDrawReq;
import edu.nuaa.lottery.rpc.res.DrawRes;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/5 10:45
 */
public interface ILotteryActivityBooth {

    /**
     * 指定活动抽奖
     * @param drawReq 请求参数
     * @return        抽奖结果
     */
    DrawRes doDraw(DrawReq drawReq);

    /**
     * 量化人群抽奖
     * @param quantificationDrawReq 请求参数
     * @return                      抽奖结果
     */
    DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq);

}
