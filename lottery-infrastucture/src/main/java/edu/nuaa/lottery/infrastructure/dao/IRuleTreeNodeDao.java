package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 18:51
 */
@Mapper
public interface IRuleTreeNodeDao {
    /**
     * 查询规则树节点
     * @param treeId    规则树ID
     * @return          规则树节点集合
     */
    List<RuleTreeNode> queryRuleTreeNodeList(Long treeId);

    /**
     * 查询规则树节点数量
     * @param treeId    规则树ID
     * @return          节点数量
     */
    int queryTreeNodeCount(Long treeId);

    /**
     * 查询规则树节点
     *
     * @param treeId    规则树ID
     * @return          节点集合
     */
    List<RuleTreeNode> queryTreeRulePoint(Long treeId);
}
