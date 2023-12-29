package edu.nuaa.lottery.rpc;

import edu.nuaa.lottery.rpc.req.ActivityReq;
import edu.nuaa.lottery.rpc.res.ActivityRes;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 19:02
 */
public interface IActivityBooth {
    ActivityRes queryActivityById(ActivityReq req);
}
