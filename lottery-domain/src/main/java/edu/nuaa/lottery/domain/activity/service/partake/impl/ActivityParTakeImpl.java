package edu.nuaa.lottery.domain.activity.service.partake.impl;

import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.common.Constants;
import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.common.SupportConstants;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.vo.ActivityBillVO;
import edu.nuaa.lottery.domain.activity.model.vo.DrawOrderVO;
import edu.nuaa.lottery.domain.activity.model.vo.UserTakeActivityVO;
import edu.nuaa.lottery.domain.activity.repository.IUserTakeActivityRepository;
import edu.nuaa.lottery.domain.activity.service.partake.BaseActivityParTake;
import edu.nuaa.lottery.domain.suppot.ids.IIdGenerator;
import edu.nuaa.middleware.db.router.strategy.IDBRouterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 17:18
 */
@Service
public class ActivityParTakeImpl extends BaseActivityParTake {
    private Logger logger = LoggerFactory.getLogger(ActivityParTakeImpl.class);
    @Resource
    IUserTakeActivityRepository userTakeActivityRepository;


    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private IDBRouterStrategy dbRouter;
    @Override
    protected Result grabActivity(ParTakeReq parTakeReq, ActivityBillVO activityBillVO,Long takeId) {
        try {
            //设置库表信息 到ThreadLocal中 便于动态数据源选择数据库和mybatis选择表
            dbRouter.doRouter(parTakeReq.getuId());
            return transactionTemplate.execute(status -> {
                try {
                    // 扣减个人已参与次数
                    int updateCount = userTakeActivityRepository.subtractionLeftCount(activityBillVO.getActivityId(), activityBillVO.getActivityName(), activityBillVO.getTakeCount(), activityBillVO.getUserTakeLeftCount(), parTakeReq.getuId(), parTakeReq.getParTakeDate());
                    if (0 == updateCount) {
                        status.setRollbackOnly();
                        logger.error("领取活动，扣减个人已参与次数失败 activityId：{} uId：{}", parTakeReq.getActivityId(), parTakeReq.getuId());
                        return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                    }

                    // 插入领取活动信息
//                    Long takeId = idGeneratorMap.get(SupportConstants.IDS.SnowFlake).nextId();
                    userTakeActivityRepository.takeActivity(activityBillVO.getActivityId(),activityBillVO.getActivityName(), activityBillVO.getTakeCount(), activityBillVO.getUserTakeLeftCount(), parTakeReq.getuId(), parTakeReq.getParTakeDate(), takeId);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    logger.error("领取活动，唯一索引冲突 activityId：{} uId：{}", parTakeReq.getActivityId(), parTakeReq.getuId(), e);
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
                }
                return Result.buildSuccessResult();
            });
        } finally {
            dbRouter.clear();
        }
    }

    @Override
    protected Result subtractionActivityStock(ParTakeReq parTakeReq) {
        int count = activityRepository.subtractionActivityStock(parTakeReq.getActivityId());
        if (count == 0){
            logger.error("扣减活动库存失败 activityId：{}", parTakeReq.getActivityId());
            return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
        }
        return Result.buildSuccessResult();
    }

    @Override
    protected UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        return userTakeActivityRepository.queryNoConsumedTakeActivityOrder(activityId, uId);
    }

    @Override
    protected Result checkActivityBill(ParTakeReq parTakeReq, ActivityBillVO activityBillVO) {
        // 校验：活动状态
        if (!ActivityConstants.ActivityState.DOING.getCode().equals(activityBillVO.getState())){
            logger.warn("活动当前状态非可用 state：{}", activityBillVO.getState());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR,"活动当前状态非可用");
        }
        // 校验：活动日期
        if (activityBillVO.getBeginDateTime().after(parTakeReq.getParTakeDate()) || activityBillVO.getEndDateTime().before(parTakeReq.getParTakeDate())){
            logger.warn("活动时间范围非可用 beginDateTime：{} endDateTime：{}", activityBillVO.getBeginDateTime(), activityBillVO.getEndDateTime());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR,"活动时间范围非可用");
        }
        // 校验：活动库存
        if (activityBillVO.getStockSurplusCount() <= 0){
            logger.warn("活动剩余库存非可用 stockSurplusCount：{}", activityBillVO.getStockSurplusCount());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动剩余库存非可用");
        }
        // 校验：个人库存 - 个人活动剩余可领取次数
        if (activityBillVO.getUserTakeLeftCount() <= 0){
            logger.warn("个人领取次数非可用 userTakeLeftCount：{}", activityBillVO.getUserTakeLeftCount());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "个人领取次数非可用");
        }
        return Result.buildSuccessResult();
    }

    @Override
    public Result recordDrawOrder(DrawOrderVO drawOrder) {
        try {
            dbRouter.doRouter(drawOrder.getuId());
            return transactionTemplate.execute(status -> {
                try {
                    // 锁定活动领取记录 更新状态字段state
                    int lockCount = userTakeActivityRepository.lockTackActivity(drawOrder.getuId(), drawOrder.getActivityId(), drawOrder.getTakeId());
                    if (0 == lockCount) {
                        status.setRollbackOnly();
                        logger.error("记录中奖单，个人参与活动抽奖已消耗完 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getuId());
                        return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                    }

                    // 保存抽奖信息
                    userTakeActivityRepository.saveUserStrategyExport(drawOrder);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    logger.error("记录中奖单，唯一索引冲突 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getuId(), e);
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
                }
                return Result.buildSuccessResult();
            });
        } finally {
            dbRouter.clear();
        }

    }
}
