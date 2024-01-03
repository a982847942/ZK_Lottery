package edu.nuaa.lottery.domain.rule.repository;

import edu.nuaa.lottery.domain.rule.model.aggregates.TreeRuleRich;

/**
 * @Description 规则信息仓储服务接口
 * @author brain
 * @version 1.0
 * @date 2024/1/2 18:46
 */
public interface IRuleRepository {
    /**
     * 查询规则决策树配置
     *
     * @param treeId    决策树ID
     * @return          决策树配置
     */
    TreeRuleRich queryTreeRuleRich(Long treeId);
}
