package edu.nuaa.lottery.domain.activity.service.deploy.impl;

import com.alibaba.fastjson.JSON;
import edu.nuaa.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import edu.nuaa.lottery.domain.activity.model.req.ActivityConfigReq;
import edu.nuaa.lottery.domain.activity.model.vo.ActivityVO;
import edu.nuaa.lottery.domain.activity.model.vo.AwardVO;
import edu.nuaa.lottery.domain.activity.model.vo.StrategyDetailVO;
import edu.nuaa.lottery.domain.activity.model.vo.StrategyVO;
import edu.nuaa.lottery.domain.activity.repository.IActivityRepository;
import edu.nuaa.lottery.domain.activity.service.deploy.IActivityDeploy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 14:53
 */
@Service
public class ActivityDeployImpl implements IActivityDeploy {
    private Logger logger = LoggerFactory.getLogger(ActivityDeployImpl.class);

    @Resource
    private IActivityRepository activityRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createActivity(ActivityConfigReq activityConfigReq) {
        logger.info("创建活动配置开始，activityId：{}", activityConfigReq.getActivityId());
        ActivityConfigRich activityConfigRich = activityConfigReq.getActivityConfigRich();
        try {
            // 添加活动配置
            ActivityVO activity = activityConfigRich.getActivity();
            activityRepository.addActivity(activity);

            // 添加奖品配置
            List<AwardVO> awardList = activityConfigRich.getAwardList();
            activityRepository.addAward(awardList);

            // 添加策略配置
            StrategyVO strategy = activityConfigRich.getStrategy();
            activityRepository.addStrategy(strategy);

            // 添加策略明细配置
            List<StrategyDetailVO> strategyDetailList = activityConfigRich.getStrategy().getStrategyDetailList();
            activityRepository.addStrategyDetailList(strategyDetailList);

            logger.info("创建活动配置完成，activityId：{}", activityConfigReq.getActivityId());
        } catch (DuplicateKeyException e) {
            logger.error("创建活动配置失败，唯一索引冲突 activityId：{} reqJson：{}", activityConfigReq.getActivityId(), JSON.toJSONString(activityConfigReq), e);
            throw e;
        }
    }

    @Override
    public void updateActivity(ActivityConfigReq req) {
        // TODO: 非核心功能后续补充
    }
}
