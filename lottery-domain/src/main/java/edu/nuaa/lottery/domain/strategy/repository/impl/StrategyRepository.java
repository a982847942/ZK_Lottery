package edu.nuaa.lottery.domain.strategy.repository.impl;

import edu.nuaa.lottery.domain.strategy.model.aggregates.StrategyRich;
import edu.nuaa.lottery.domain.strategy.repository.IStrategyRepository;
import edu.nuaa.lottery.infrastructure.dao.IAwardDao;
import edu.nuaa.lottery.infrastructure.dao.IStrategyDao;
import edu.nuaa.lottery.infrastructure.dao.IStrategyDetailDao;
import edu.nuaa.lottery.infrastructure.po.Award;
import edu.nuaa.lottery.infrastructure.po.Strategy;
import edu.nuaa.lottery.infrastructure.po.StrategyDetail;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:26
 */
@Component
public class StrategyRepository implements IStrategyRepository {
    @Resource
    IStrategyDao iStrategyDao;
    @Resource
    IStrategyDetailDao iStrategyDetailDao;
    @Resource
    IAwardDao iAwardDao;
    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        Strategy strategy = iStrategyDao.queryStrategy(strategyId);
        List<StrategyDetail> strategyDetails = iStrategyDetailDao.queryStrategyDetailList(strategyId);
        StrategyRich strategyRich = new StrategyRich(strategy, strategyDetails);
        return strategyRich;
    }

    @Override
    public Award queryAwardInfo(String awardId) {
        return iAwardDao.queryAwardInfo(awardId);
    }

    @Override
    public List<String> queryNoStockStrategyAwardList(Long strategyId) {
        return iStrategyDetailDao.queryNoStockStrategyAwardList(strategyId);
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
        StrategyDetail req = new StrategyDetail();
        req.setStrategyId(strategyId);
        req.setAwardId(awardId);
        int count = iStrategyDetailDao.deductStock(req);
        return count == 1;
    }
}
