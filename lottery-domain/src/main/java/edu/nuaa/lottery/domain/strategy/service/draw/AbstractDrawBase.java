package edu.nuaa.lottery.domain.strategy.service.draw;

import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.domain.strategy.model.aggregates.StrategyRich;
import edu.nuaa.lottery.domain.strategy.model.req.DrawReq;
import edu.nuaa.lottery.domain.strategy.model.res.DrawRes;
import edu.nuaa.lottery.domain.strategy.model.vo.*;
import edu.nuaa.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 14:47
 */
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec{
    private Logger logger = LoggerFactory.getLogger(AbstractDrawBase.class);
    @Override
    public DrawRes doDrawExec(DrawReq req) {
        // 1. 获取抽奖策略
        StrategyRich strategyRich = super.queryStrategyRich(req.getStrategyId());
        StrategyBriefVO strategy = strategyRich.getStrategy();

        // 2. 校验抽奖策略是否已经初始化到内存
        this.checkAndInitAwardTuple(req.getStrategyId(), strategy.getStrategyMode(), strategyRich.getStrategyDetailList());

        // 3. 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等
        List<String> excludeAwardIds = this.queryExcludeAwardIds(req.getStrategyId());

        // 4. 执行抽奖算法
        String awardId = this.drawAlgorithm(req.getStrategyId(), drawAlgorithmMap.get(strategy.getStrategyMode()), excludeAwardIds);

        // 5. 包装中奖结果
        return buildDrawResult(req.getuId(), req.getStrategyId(), awardId,strategy);
    }

    private DrawRes buildDrawResult(String uId, Long strategyId, String awardId,StrategyBriefVO strategy) {
        if (null == awardId) {
            logger.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawRes(uId, strategyId, DrawAlgorithmConstants.DrawState.FAIL.getCode());
        }

        AwardBriefVO award = super.queryAwardInfoById(awardId);
        DrawAwardInfo drawAwardInfo = new DrawAwardInfo(award.getAwardId(), award.getAwardType(), award.getAwardName(), award.getAwardContent());
        drawAwardInfo.setStrategyMode(strategy.getStrategyMode());
        drawAwardInfo.setGrantType(strategy.getGrantType());
        drawAwardInfo.setGrantDate(strategy.getGrantDate());
        logger.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());

        return new DrawRes(uId, strategyId, DrawAlgorithmConstants.DrawState.SUCCESS.getCode(), drawAwardInfo);
    }

    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm iDrawAlgorithm, List<String> excludeAwardIds);

    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    private void checkAndInitAwardTuple(Long strategyId, Integer strategyMode, List<StrategyDetailBriefVO> strategyDetailList) {
        // TODO: 2023/12/28 这样设置的话如果activity的strategyId为2，则awardTuple未初始化，因此会产生错误.
        //本质原因还是在于initRateTuple职责划分不清楚！ 单项概率(需要使用hash加速)和整体概率(无法使用hash加速)需要初始化的内容不同。
//        if (!DrawAlgorithmConstants.StrategyMode.SINGLE.getCode().equals(strategyMode)) {
//            return;
//        }
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);
        if (drawAlgorithm.isExistRateTuple(strategyId)){
            return;
        }
        List<AwardRateInfo> awardRateInfoList = new ArrayList<>();
        for (StrategyDetailBriefVO strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(),strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId,awardRateInfoList);
    }
}
