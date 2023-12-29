package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 22:49
 */
@Mapper
public interface IStrategyDao {
    /**
     * 查询策略信息
     * @param strategyId 策略ID
     * @return           策略配置信息
     */
    Strategy queryStrategy(Long strategyId);

    /**
     * 插入抽奖策略信息
     * @param strategy
     */
    void insertStrategy(Strategy strategy);
}
