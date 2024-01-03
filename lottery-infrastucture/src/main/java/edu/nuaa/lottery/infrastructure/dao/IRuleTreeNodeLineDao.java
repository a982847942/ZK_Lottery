package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 18:51
 */
@Mapper
public interface IRuleTreeNodeLineDao {
    /**
     * 查询规则树节点连线集合
     * @param req   入参
     * @return      规则树节点连线集合
     */
    List<RuleTreeNodeLine> queryRuleTreeNodeLineList(RuleTreeNodeLine req);

    /**
     * 查询规则树连线数量
     *
     * @param treeId    规则树ID
     * @return          规则树连线数量
     */
    int queryTreeNodeLineCount(Long treeId);
}
