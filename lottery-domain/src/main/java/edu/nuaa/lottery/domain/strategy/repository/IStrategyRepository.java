package edu.nuaa.lottery.domain.strategy.repository;

import edu.nuaa.lottery.domain.strategy.model.aggregates.StrategyRich;
import edu.nuaa.lottery.domain.strategy.model.vo.AwardRateInfo;
import edu.nuaa.lottery.infrastructure.po.Award;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:22
 */
public interface IStrategyRepository {
    StrategyRich queryStrategyRich(Long strategyId);
    Award queryAwardInfo(String awardId);
}
