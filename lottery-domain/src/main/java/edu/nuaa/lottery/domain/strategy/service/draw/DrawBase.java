package edu.nuaa.lottery.domain.strategy.service.draw;

import edu.nuaa.lottery.domain.strategy.model.vo.AwardRateInfo;
import edu.nuaa.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import edu.nuaa.lottery.infrastructure.po.StrategyDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:57
 */
public class DrawBase extends DrawConfig {
    public void checkAndInitRateTuple(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        if (1 != strategyMode) return;
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);
        if (drawAlgorithm.isExistRateTuple(strategyId)) return;
        List<AwardRateInfo> awardRateInfoList = new ArrayList<>();
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(),strategyDetail.getAwardRate()));
        }
        drawAlgorithm.initRateTuple(strategyId,awardRateInfoList);
    }

}
