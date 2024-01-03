package edu.nuaa.lottery.domain.rule.service.logic.impl;

import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.service.logic.BasicLogic;
import org.springframework.stereotype.Service;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 15:56
 */
@Service
public class UserGenderFilter extends BasicLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }
}
