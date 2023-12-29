package edu.nuaa.lottery.interfaces;

import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.infrastructure.dao.IActivityDao;
import edu.nuaa.lottery.infrastructure.po.Activity;
import edu.nuaa.lottery.rpc.IActivityBooth;
import edu.nuaa.lottery.rpc.dto.ActivityDto;
import edu.nuaa.lottery.rpc.req.ActivityReq;
import edu.nuaa.lottery.rpc.res.ActivityRes;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 19:29
 */
@Service
public class ActivityBooth implements IActivityBooth {
    @Resource
    private IActivityDao activityDao;

    @Override
    public ActivityRes queryActivityById(ActivityReq req) {
        ActivityDto activityDto = null;
        try {
            Activity activity = activityDao.queryActivityById(req.getActivityId());
            activityDto = new ActivityDto();
            activityDto.setActivityId(activity.getActivityId());
            activityDto.setActivityName(activity.getActivityName());
            activityDto.setActivityDesc(activity.getActivityDesc());
            activityDto.setBeginDateTime(activity.getBeginDateTime());
            activityDto.setEndDateTime(activity.getEndDateTime());
            activityDto.setStockCount(activity.getStockCount());
            activityDto.setTakeCount(activity.getTakeCount());
            return new ActivityRes(Result.buildSuccessResult(), activityDto);
        } catch (Exception e) {
            return new ActivityRes(Result.buildErrorResult(), activityDto);
        }

    }
}
