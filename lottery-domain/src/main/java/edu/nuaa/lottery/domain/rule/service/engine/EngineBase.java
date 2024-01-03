package edu.nuaa.lottery.domain.rule.service.engine;

import edu.nuaa.lottery.common.RuleConstants;
import edu.nuaa.lottery.domain.rule.model.aggregates.TreeRuleRich;
import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.model.res.EngineResult;
import edu.nuaa.lottery.domain.rule.model.vo.TreeNodeVO;
import edu.nuaa.lottery.domain.rule.model.vo.TreeRootVO;
import edu.nuaa.lottery.domain.rule.service.logic.LogicFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 15:58
 */
public abstract class EngineBase extends EngineConfig implements EngineFilter {
    private final Logger logger = LoggerFactory.getLogger(EngineBase.class);

    @Override
    public EngineResult process(DecisionMatterReq matter) {
        throw new RuntimeException("未实现规则引擎服务");
    }
    protected TreeNodeVO engineDecisionMaker(TreeRuleRich treeRuleRich, DecisionMatterReq matter) {
        TreeRootVO treeRoot = treeRuleRich.getTreeRoot();
        Map<Long, TreeNodeVO> treeNodeMap = treeRuleRich.getTreeNodeMap();

        // 规则树根ID 从树根开始遍历决策树规则
        Long rootNodeId = treeRoot.getTreeRootNodeId();
        //根据规则树根ID获取节点信息
        TreeNodeVO treeNodeInfo = treeNodeMap.get(rootNodeId);

        // 节点类型[NodeType]；1子叶、2果实  直到决策树到果实节点或者出现matterValue和任意一个treeNodeLineInfo都不匹配
        while (RuleConstants.NodeType.STEM.equals(treeNodeInfo.getNodeType())) {
            //规则键
            String ruleKey = treeNodeInfo.getRuleKey();
            //根据规则键获取对应的过滤器
            LogicFilter logicFilter = logicFilterMap.get(ruleKey);
            String matterValue = logicFilter.matterValue(matter);
            Long nextNode = logicFilter.filter(matterValue, treeNodeInfo.getTreeNodeLineInfoList());
            //下一个决策节点信息  如果nextNode返回RuleConstants.Global.TREE_NULL_NODE; 此时 treeNodeInfo为null，下一次循环会抛出异常
            treeNodeInfo = treeNodeMap.get(nextNode);
            logger.info("决策树引擎=>{} userId：{} treeId：{} treeNode：{} ruleKey：{} matterValue：{}", treeRoot.getTreeName(), matter.getUserId(), matter.getTreeId(), treeNodeInfo.getTreeNodeId(), ruleKey, matterValue);
        }
        //返回果实节点
        return treeNodeInfo;
    }

}
