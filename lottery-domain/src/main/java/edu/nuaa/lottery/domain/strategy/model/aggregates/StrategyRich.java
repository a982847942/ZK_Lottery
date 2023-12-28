package edu.nuaa.lottery.domain.strategy.model.aggregates;

import edu.nuaa.lottery.infrastructure.po.Strategy;
import edu.nuaa.lottery.infrastructure.po.StrategyDetail;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:15
 */
public class StrategyRich {
    private Strategy strategy;
    private List<StrategyDetail> strategyDetails;

    public StrategyRich(Strategy strategy, List<StrategyDetail> strategyDetails) {
        this.strategy = strategy;
        this.strategyDetails = strategyDetails;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<StrategyDetail> getStrategyDetails() {
        return strategyDetails;
    }

    public void setStrategyDetails(List<StrategyDetail> strategyDetails) {
        this.strategyDetails = strategyDetails;
    }
}
