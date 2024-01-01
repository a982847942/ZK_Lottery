package edu.nuaa.lottery.domain.activity.service.partake;

import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.vo.ActivityBillVO;
import edu.nuaa.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 17:17
 */
public class ActivityParTakeSupport {
    @Resource
    public IActivityRepository activityRepository;

    ActivityBillVO queryActivityBills(ParTakeReq parTakeReq){
        return activityRepository.queryActivityBills(parTakeReq);
    }
}
