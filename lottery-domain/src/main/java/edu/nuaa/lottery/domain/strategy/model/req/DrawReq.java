package edu.nuaa.lottery.domain.strategy.model.req;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:18
 */
public class DrawReq {
    private String uId;
    private Long strategyId;

    private String uuid;

    public DrawReq(String uId, Long strategyId) {

        this.uId = uId;
        this.strategyId = strategyId;
    }

    public DrawReq(String uId, Long strategyId, String uuid) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
