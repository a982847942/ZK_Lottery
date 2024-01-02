package edu.nuaa.lottery.application.process.res;

import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardInfo;

import javax.naming.spi.DirStateFactory;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/1 21:14
 */
public class DrawProcessResult extends Result {
    private DrawAwardInfo drawAwardInfo;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardInfo drawAwardInfo) {
        super(code, info);
        this.drawAwardInfo = drawAwardInfo;
    }

    public DrawAwardInfo getDrawAwardInfo() {
        return drawAwardInfo;
    }

    public void setDrawAwardInfo(DrawAwardInfo drawAwardInfo) {
        this.drawAwardInfo = drawAwardInfo;
    }
}
