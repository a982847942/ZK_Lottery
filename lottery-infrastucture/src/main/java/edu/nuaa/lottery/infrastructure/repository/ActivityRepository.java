package edu.nuaa.lottery.infrastructure.repository;

import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.domain.activity.model.vo.*;
import edu.nuaa.lottery.domain.activity.repository.IActivityRepository;
import edu.nuaa.lottery.infrastructure.dao.IActivityDao;
import edu.nuaa.lottery.infrastructure.dao.IAwardDao;
import edu.nuaa.lottery.infrastructure.dao.IStrategyDao;
import edu.nuaa.lottery.infrastructure.dao.IStrategyDetailDao;
import edu.nuaa.lottery.infrastructure.po.Activity;
import edu.nuaa.lottery.infrastructure.po.Award;
import edu.nuaa.lottery.infrastructure.po.Strategy;
import edu.nuaa.lottery.infrastructure.po.StrategyDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 10:51
 */
@Component
public class ActivityRepository implements IActivityRepository {
    @Resource
    IActivityDao activityDao;
    @Resource
    IAwardDao awardDao;
    @Resource
    IStrategyDao strategyDao;
    @Resource
    IStrategyDetailDao strategyDetailDao;

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
}
