package edu.nuaa.lottery.domain.rule.model.aggregates;

import edu.nuaa.lottery.domain.rule.model.vo.TreeNodeVO;
import edu.nuaa.lottery.domain.rule.model.vo.TreeRootVO;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 18:34
 */
public class TreeRuleRich {
    /** 树根信息 */
    private TreeRootVO treeRoot;
    /** 树节点ID -> 子节点 */
    private Map<Long, TreeNodeVO> treeNodeMap;

    public TreeRootVO getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(TreeRootVO treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Map<Long, TreeNodeVO> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<Long, TreeNodeVO> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }
}
