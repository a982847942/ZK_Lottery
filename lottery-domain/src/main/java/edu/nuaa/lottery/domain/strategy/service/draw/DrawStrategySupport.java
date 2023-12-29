package edu.nuaa.lottery.domain.strategy.service.draw;

import edu.nuaa.lottery.domain.strategy.model.aggregates.StrategyRich;
import edu.nuaa.lottery.domain.strategy.model.vo.AwardBriefVO;
import edu.nuaa.lottery.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 14:47
 */
public class DrawStrategySupport extends DrawConfig{
    @Resource
    IStrategyRepository iStrategyRepository;

    StrategyRich queryStrategyRich(Long strategyId){
        return iStrategyRepository.queryStrategyRich(strategyId);
    }

    AwardBriefVO queryAwardInfoById(String awardId){
        return iStrategyRepository.queryAwardInfo(awardId);
    }
}
