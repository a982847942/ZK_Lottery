package edu.nuaa.lottery.infrastructure.repository;

import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.vo.*;
import edu.nuaa.lottery.domain.activity.repository.IActivityRepository;
import edu.nuaa.lottery.infrastructure.dao.*;
import edu.nuaa.lottery.infrastructure.po.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 10:51
 */
@Repository
public class ActivityRepository implements IActivityRepository {
    @Resource
    IActivityDao activityDao;
    @Resource
    IAwardDao awardDao;
    @Resource
    IStrategyDao strategyDao;
    @Resource
    IStrategyDetailDao strategyDetailDao;
    @Resource
    IUserTakeActivityCountDao userTakeActivityDetailDao;

    @Override
    public void addActivity(ActivityVO activity) {
        Activity req = new Activity();
        BeanUtils.copyProperties(activity,req);
        activityDao.insert(req);
    }

    @Override
    public void addAward(List<AwardVO> awardList) {
        List<Award> req = new ArrayList<>();
        for (AwardVO awardVO : awardList) {
            Award award = new Award();
            BeanUtils.copyProperties(awardVO, award);
            req.add(award);
        }
        awardDao.insertAwardList(req);
    }

    @Override
    public void addStrategy(StrategyVO strategy) {
        Strategy req = new Strategy();
        BeanUtils.copyProperties(strategy, req);
        strategyDao.insertStrategy(req);
    }

    @Override
    public void addStrategyDetailList(List<StrategyDetailVO> strategyDetailList) {
        List<StrategyDetail> req = new ArrayList<>();
        for (StrategyDetailVO strategyDetailVO : strategyDetailList) {
            StrategyDetail strategyDetail = new StrategyDetail();
            BeanUtils.copyProperties(strategyDetailVO, strategyDetail);
            req.add(strategyDetail);
        }
        strategyDetailDao.insertStrategyDetail(req);
    }

    @Override
    public boolean alterStatus(Long activityId, Enum<ActivityConstants.ActivityState> beforeState, Enum<ActivityConstants.ActivityState> afterState) {
        AlterStateVO alterStateVO = new AlterStateVO(activityId,((ActivityConstants.ActivityState) beforeState).getCode(),((ActivityConstants.ActivityState) afterState).getCode());
        int count = activityDao.alterState(alterStateVO);
        return 1 == count;
    }

    @Override
    public ActivityBillVO queryActivityBills(ParTakeReq parTakeReq) {
        // 查询活动信息 采用默认路由 未分库分表
        Activity activity = activityDao.queryActivityById(parTakeReq.getActivityId());

        // 查询领取次数
        UserTakeActivityCount userTakeActivityCountReq = new UserTakeActivityCount();
        userTakeActivityCountReq.setuId(parTakeReq.getuId());
        userTakeActivityCountReq.setActivityId(parTakeReq.getActivityId());
        //未手动设置路由 采用@DBRouter注解让切面拦截  读取yml配置默认路由设置路由 routerKey: uId
        // TODO: 2024/1/1 如果此时只是发布了活动，但是用户信息不太可能要手动添加，应该是在用户参与时发现没有记录再执行初始化插入，这里逻辑有问题？
        UserTakeActivityCount userTakeActivityCount = userTakeActivityDetailDao.queryUserTakeActivityCount(userTakeActivityCountReq);

        // 封装结果信息
        ActivityBillVO activityBillVO = new ActivityBillVO();
        activityBillVO.setuId(parTakeReq.getuId());
        activityBillVO.setActivityId(parTakeReq.getActivityId());
        activityBillVO.setActivityName(activity.getActivityName());
        activityBillVO.setBeginDateTime(activity.getBeginDateTime());
        activityBillVO.setEndDateTime(activity.getEndDateTime());
        activityBillVO.setTakeCount(activity.getTakeCount());
        activityBillVO.setStockSurplusCount(activity.getStockSurplusCount());
        activityBillVO.setStrategyId(activity.getStrategyId());
        activityBillVO.setState(activity.getState());
        activityBillVO.setUserTakeLeftCount(null == userTakeActivityCount ? null : userTakeActivityCount.getLeftCount());

        return activityBillVO;
    }

    @Override
    public int subtractionActivityStock(Long activityId) {
        return activityDao.subtractionActivityStock(activityId);
    }
}
