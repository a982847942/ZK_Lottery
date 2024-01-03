package edu.nuaa.lottery.domain.rule.service.logic;

import edu.nuaa.lottery.common.RuleConstants;
import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.model.vo.TreeNodeLineVO;
import org.apache.tomcat.util.digester.Rule;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 15:56
 */
public abstract class BasicLogic implements LogicFilter{
    @Override
    public Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList) {
        for (TreeNodeLineVO treeNodeLineVO : treeNodeLineInfoList) {
            if (decisionLogic(matterValue,treeNodeLineVO)){
                //符合规则 跳转到下一个节点
                return treeNodeLineVO.getNodeIdTo();
            }
        }
        return RuleConstants.Global.TREE_NULL_NODE;
    }

    @Override
    public abstract String matterValue(DecisionMatterReq decisionMatter);

    protected boolean decisionLogic(String matterValue, TreeNodeLineVO treeNodeLineVO) {
        switch (treeNodeLineVO.getRuleLimitType()) {
            case RuleConstants.RuleLimitType.EQUAL:
                return matterValue.equals(treeNodeLineVO.getRuleLimitValue());
            case RuleConstants.RuleLimitType.GT:
                return Double.parseDouble(matterValue) > Double.parseDouble(treeNodeLineVO.getRuleLimitValue());
            case RuleConstants.RuleLimitType.LT:
                return Double.parseDouble(matterValue) < Double.parseDouble(treeNodeLineVO.getRuleLimitValue());
            case RuleConstants.RuleLimitType.GE:
                return Double.parseDouble(matterValue) >= Double.parseDouble(treeNodeLineVO.getRuleLimitValue());
            case RuleConstants.RuleLimitType.LE:
                return Double.parseDouble(matterValue) <= Double.parseDouble(treeNodeLineVO.getRuleLimitValue());
            default:
                return false;
        }
    }
}
