package edu.nuaa.lottery.domain.strategy.model.res;

import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardInfo;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:18
 */
public class DrawRes {
    private String uId;
    private Long strategyId;

    private Integer drawState = DrawAlgorithmConstants.DrawState.FAIL.getCode();
    private DrawAwardInfo drawAwardInfo;

    public DrawRes(String uId, Long strategyId, Integer drawState) {
        this(uId,strategyId,drawState,null);
    }

    public DrawRes(String uId, Long strategyId, Integer drawState, DrawAwardInfo drawAwardInfo) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.drawState = drawState;
        this.drawAwardInfo = drawAwardInfo;
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

    public Integer getDrawState() {
        return drawState;
    }

    public void setDrawState(Integer drawState) {
        this.drawState = drawState;
    }

    public DrawAwardInfo getDrawAwardInfo() {
        return drawAwardInfo;
    }

    public void setDrawAwardInfo(DrawAwardInfo drawAwardInfo) {
        this.drawAwardInfo = drawAwardInfo;
    }
}
