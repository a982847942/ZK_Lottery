package edu.nuaa.lottery.domain.strategy.service.draw;

import edu.nuaa.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import edu.nuaa.lottery.domain.strategy.service.algorithm.impl.DefaultRateRandomDrawAlgorithm;
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
    DefaultRateRandomDrawAlgorithm defaultRateRandomDrawAlgorithm;
    @Resource
    SingleRateRandomDrawAlgorithm singleRateRandomDrawAlgorithm;
    public static Map<Integer, IDrawAlgorithm> drawAlgorithmMap = new ConcurrentHashMap<>();
    @PostConstruct
    public void init(){
        drawAlgorithmMap.put(1,defaultRateRandomDrawAlgorithm);
        drawAlgorithmMap.put(2,singleRateRandomDrawAlgorithm);
    }

}
