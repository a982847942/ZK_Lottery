package edu.nuaa.lottery.domain.strategy.service.draw;

import edu.nuaa.lottery.domain.strategy.model.req.DrawReq;
import edu.nuaa.lottery.domain.strategy.model.res.DrawRes;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:57
 */
public interface IDrawExec {
    DrawRes doDrawExec(DrawReq req);
}
