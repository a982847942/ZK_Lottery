package edu.nuaa.lottery.domain.strategy.service.draw;

import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.domain.strategy.model.aggregates.StrategyRich;
import edu.nuaa.lottery.domain.strategy.model.req.DrawReq;
import edu.nuaa.lottery.domain.strategy.model.res.DrawRes;
import edu.nuaa.lottery.domain.strategy.model.vo.AwardRateInfo;
import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardInfo;
import edu.nuaa.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import edu.nuaa.lottery.infrastructure.po.Award;
import edu.nuaa.lottery.infrastructure.po.Strategy;
import edu.nuaa.lottery.infrastructure.po.StrategyDetail;
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
        //1.查询抽奖策略
        StrategyRich strategyRich = queryStrategyRich(req.getStrategyId());
        Strategy strategy = strategyRich.getStrategy();
        List<StrategyDetail> strategyDetailList = strategyRich.getStrategyDetails();

        //2.检查并初始化奖品数组awardTuple
        checkAndInitAwardTuple(req.getStrategyId(),strategy.getStrategyMode(),strategyDetailList);

        //3.获取不在抽奖范围内的奖品列表，包括风控、库存不足、临时调整等
        List<String> excludeAwardIds = this.queryExcludeAwardIds(strategy.getStrategyId());
        //4.执行抽奖算法
        String awardId = this.drawAlgorithm(strategy.getStrategyId(),drawAlgorithmMap.get(strategy.getStrategyMode()),excludeAwardIds);
        //5.封装返回结果
        return buildDrawResult(req.getuId(),strategy.getStrategyId(),awardId);
    }

    private DrawRes buildDrawResult(String uId, Long strategyId, String awardId) {
        if (null == awardId) {
            logger.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawRes(uId, strategyId, DrawAlgorithmConstants.DrawState.FAIL.getCode());
        }
        Award award = queryAwardInfoById(awardId);
        logger.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());
        return new DrawRes(uId,strategyId,DrawAlgorithmConstants.DrawState.SUCCESS.getCode(),new DrawAwardInfo(award.getAwardId(),award.getAwardName()));
    }

    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm iDrawAlgorithm, List<String> excludeAwardIds);

    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    private void checkAndInitAwardTuple(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        if (!DrawAlgorithmConstants.StrategyMode.ENTIRETY.getCode().equals(strategyMode)) {
            return;
        }
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);
        if (drawAlgorithm.isExistRateTuple(strategyId)){
            return;
        }
        List<AwardRateInfo> awardRateInfoList = new ArrayList<>();
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(),strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId,awardRateInfoList);
    }
}
