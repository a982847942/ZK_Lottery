package edu.nuaa.lottery.domain.strategy.model.vo;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 15:36
 */
public class DrawAwardInfo {
    private String rewardId;
    private String awardName;

    public DrawAwardInfo(String rewardId, String awardName) {
        this.rewardId = rewardId;
        this.awardName = awardName;
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
