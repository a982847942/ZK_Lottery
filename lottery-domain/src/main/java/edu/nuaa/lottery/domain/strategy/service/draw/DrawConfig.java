package edu.nuaa.lottery.domain.strategy.service.draw;

import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import edu.nuaa.lottery.domain.strategy.service.algorithm.impl.EntiretyRateRandomDrawAlgorithm;
import edu.nuaa.lottery.domain.strategy.service.algorithm.impl.SingleRateRandomDrawAlgorithm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:57
 */
public class DrawConfig {
    @Resource
    EntiretyRateRandomDrawAlgorithm entiretyRateRandomDrawAlgorithm;
    @Resource
    SingleRateRandomDrawAlgorithm singleRateRandomDrawAlgorithm;
    public static Map<Integer, IDrawAlgorithm> drawAlgorithmMap = new ConcurrentHashMap<>();
    @PostConstruct
    public void init(){
        drawAlgorithmMap.put(DrawAlgorithmConstants.StrategyMode.SINGLE.getCode(),singleRateRandomDrawAlgorithm);
        drawAlgorithmMap.put(DrawAlgorithmConstants.StrategyMode.ENTIRETY.getCode(), entiretyRateRandomDrawAlgorithm);
    }

}
