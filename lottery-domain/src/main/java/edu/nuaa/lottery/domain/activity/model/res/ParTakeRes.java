package edu.nuaa.lottery.domain.activity.model.res;

import edu.nuaa.lottery.common.Result;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 17:15
 */
public class ParTakeRes extends Result {
    /**
     * 策略ID
     */
    private Long strategyId;

    public ParTakeRes(String code, String info) {
        super(code, info);
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }
}
