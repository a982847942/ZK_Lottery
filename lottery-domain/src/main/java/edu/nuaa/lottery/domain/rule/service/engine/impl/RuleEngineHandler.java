package edu.nuaa.lottery.domain.rule.service.engine.impl;

import edu.nuaa.lottery.domain.rule.model.aggregates.TreeRuleRich;
import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.model.res.EngineResult;
import edu.nuaa.lottery.domain.rule.model.vo.TreeNodeVO;
import edu.nuaa.lottery.domain.rule.repository.IRuleRepository;
import edu.nuaa.lottery.domain.rule.service.engine.EngineBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 规则引擎处理器
 * @author brain
 * @version 1.0
 * @date 2024/1/2 15:59
 */
@Service("ruleEngineHandle")
public class RuleEngineHandler extends EngineBase {

    @Resource
    private IRuleRepository ruleRepository;

    @Override
    public EngineResult process(DecisionMatterReq matter) {
        // 决策规则树
        TreeRuleRich treeRuleRich = ruleRepository.queryTreeRuleRich(matter.getTreeId());
        if (null == treeRuleRich) {
            throw new RuntimeException("Tree Rule is null!");
        }

        // 决策节点
        TreeNodeVO treeNodeInfo = engineDecisionMaker(treeRuleRich, matter);

        // 决策结果  返回决策treeId，NodeId，NodeValue(这里既为活动号)，请求userId
        return new EngineResult(matter.getUserId(), treeNodeInfo.getTreeId(), treeNodeInfo.getTreeNodeId(), treeNodeInfo.getNodeValue());
    }

}