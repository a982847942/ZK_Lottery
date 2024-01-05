package edu.nuaa.lottery.application.process.res;

import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/1 21:14
 */
public class DrawProcessResult extends Result {
    private DrawAwardVO drawAwardVO;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardVO drawAwardVO) {
        super(code, info);
        this.drawAwardVO = drawAwardVO;
    }

    public DrawAwardVO getDrawAwardVO() {
        return drawAwardVO;
    }

    public void setDrawAwardVO(DrawAwardVO drawAwardVO) {
        this.drawAwardVO = drawAwardVO;
    }
}
