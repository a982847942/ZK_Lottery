package edu.nuaa.lottery.application.process;

import edu.nuaa.lottery.application.process.req.DrawProcessReq;
import edu.nuaa.lottery.application.process.res.DrawProcessResult;

/**
 * @Descrtption 活动流程编排
 * @author brain
 * @version 1.0
 * @date 2024/1/1 21:12
 */
public interface IActivityProcess {
    /**
     * 执行抽奖流程
     * @param drawProcessReq 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessResult doDrawProcess(DrawProcessReq drawProcessReq);
}
