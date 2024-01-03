package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 18:50
 */
@Mapper
public interface IRuleTreeDao {
    /**
     * 规则树查询
     * @param treeId ID
     * @return   规则树
     */
    RuleTree queryRuleTreeByTreeId(Long treeId);

    /**
     * 规则树简要信息查询
     * @param treeId 规则树ID
     * @return       规则树
     */
    RuleTree queryTreeSummaryInfo(Long treeId);
}
