package edu.nuaa.lottery.domain.rule.service.engine;

import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.model.res.EngineResult;

/**
 * @description: 规则过滤器引擎
 * @author brain
 * @version 1.0
 * @date 2024/1/2 15:57
 */
public interface EngineFilter {
    /**
     * 规则过滤器接口
     * @param matter      规则决策物料
     * @return            规则决策结果
     */
    EngineResult process(final DecisionMatterReq matter);
}
