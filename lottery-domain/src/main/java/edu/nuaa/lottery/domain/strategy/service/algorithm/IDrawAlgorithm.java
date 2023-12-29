package edu.nuaa.lottery.domain.strategy.service.algorithm;

import edu.nuaa.lottery.domain.strategy.model.vo.AwardRateInfo;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:31
 */
public interface IDrawAlgorithm {
    /**
     *
     * 初始化奖品数组
     * @param strategyId
     * @param awardRateInfoList
     */
    void initRateTuple(Long strategyId, List<AwardRateInfo> awardRateInfoList);

    /**
     * 判断该活动下 ID为StrategyID的策略是否进行过初始化
     * @param strategyId
     * @return
     */
    boolean isExistRateTuple(Long strategyId);

    /**
     * 根据随机数 匹配相应的奖品的ID(awardId)
     * @param strategyId
     * @param excludeAwardIds
     * @return
     */
    String randomDraw(Long strategyId, List<String> excludeAwardIds);

}
