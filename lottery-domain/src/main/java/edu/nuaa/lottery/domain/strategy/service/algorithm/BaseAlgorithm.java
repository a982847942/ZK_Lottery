package edu.nuaa.lottery.domain.strategy.service.algorithm;

import edu.nuaa.lottery.domain.strategy.model.vo.AwardRateVO;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:32
 */
public abstract class BaseAlgorithm implements IDrawAlgorithm{
    // 斐波那契散列增量，逻辑：黄金分割点：(√5 - 1) / 2 = 0.6180339887，Math.pow(2, 32) * 0.6180339887 = 0x61c88647
    private final int HASH_INCREMENT = 0x61c88647;

    // 数组初始化长度
    private final int RATE_TUPLE_LENGTH = 128;

    // 存放概率与奖品对应的散列结果，strategyId -> rateTuple
    protected Map<Long, String[]> rateTupleMap = new ConcurrentHashMap<>();

    // 奖品区间概率值，strategyId -> [awardId->begin、awardId->end]
    protected Map<Long, List<AwardRateVO>> awardRateInfoMap = new ConcurrentHashMap<>();

    @Override
    public void initRateTuple(Long strategyId, List<AwardRateVO> awardRateVOList) {
//        if (awardRateInfoMap.containsKey(strategyId))return; // 会不会多次初始化？
        awardRateInfoMap.put(strategyId, awardRateVOList);
        String[] rateTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new String[RATE_TUPLE_LENGTH]);
        int cursorVal = 0;
        for (AwardRateVO awardRateVO : awardRateVOList) {
            int rateVal = awardRateVO.getAwardRate().multiply(new BigDecimal(100)).intValue();
            for (int i = cursorVal + 1; i <= (rateVal + cursorVal); i++) {
                rateTuple[hashIdx(i)] = awardRateVO.getAwardId();
            }
            cursorVal += rateVal;
        }
    }

    @Override
    public boolean isExistRateTuple(Long strategyId) {

        return rateTupleMap.containsKey(strategyId);
    }
    protected int hashIdx(int val) {
        int hashCode = val * HASH_INCREMENT + HASH_INCREMENT;
        return hashCode & (RATE_TUPLE_LENGTH - 1);
    }
    protected int generateSecureRandomIntCode(int bound){
        int random = new SecureRandom().nextInt(bound) + 1;
        return random;
    }
}
