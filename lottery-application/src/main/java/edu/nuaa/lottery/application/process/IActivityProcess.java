package edu.nuaa.lottery.application.process;

import edu.nuaa.lottery.application.process.req.DrawProcessReq;
import edu.nuaa.lottery.application.process.res.DrawProcessResult;
import edu.nuaa.lottery.application.process.res.RuleQuantificationCrowdResult;
import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;

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
    /**
     * 规则量化人群，返回可参与的活动ID
     * @param req   规则请求
     * @return      量化结果，用户可以参与的活动ID
     */
    RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req);
}
