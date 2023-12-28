package edu.nuaa.lottery.domain.strategy.model.res;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:18
 */
public class DrawRes {
    private String uId;
    private Long strategyId;
    private String rewardId;
    private String awardName;

    public DrawRes(String uId, Long strategyId, String rewardId, String awardName) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.rewardId = rewardId;
        this.awardName = awardName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }
}
