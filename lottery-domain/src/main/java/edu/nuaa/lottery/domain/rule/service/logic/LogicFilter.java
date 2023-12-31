package edu.nuaa.lottery.domain.rule.service.logic;

import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 15:56
 */
public interface LogicFilter {
    /**
     * 逻辑决策器
     * @param matterValue          决策值
     * @param treeNodeLineInfoList 决策节点
     * @return                     下一个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList);

    /**
     * 获取决策值
     *
     * @param decisionMatter 决策物料
     * @return               决策值
     */
    String matterValue(DecisionMatterReq decisionMatter);

}
