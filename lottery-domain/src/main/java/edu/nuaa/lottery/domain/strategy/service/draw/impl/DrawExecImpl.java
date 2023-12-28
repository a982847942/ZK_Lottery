package edu.nuaa.lottery.domain.strategy.service.draw.impl;

import edu.nuaa.lottery.domain.strategy.model.aggregates.StrategyRich;
import edu.nuaa.lottery.domain.strategy.model.req.DrawReq;
import edu.nuaa.lottery.domain.strategy.model.res.DrawRes;
import edu.nuaa.lottery.domain.strategy.repository.IStrategyRepository;
import edu.nuaa.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import edu.nuaa.lottery.domain.strategy.service.draw.DrawBase;
import edu.nuaa.lottery.domain.strategy.service.draw.IDrawExec;
import edu.nuaa.lottery.infrastructure.po.Award;
import edu.nuaa.lottery.infrastructure.po.Strategy;
import edu.nuaa.lottery.infrastructure.po.StrategyDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 10:57
 */
@Service("drawExec")
public class DrawExecImpl extends DrawBase implements IDrawExec {
    private Logger logger = LoggerFactory.getLogger(DrawExecImpl.class);

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DrawRes doDrawExec(DrawReq req) {
        logger.info("执行策略抽奖开始，strategyId：{}", req.getStrategyId());

        // 获取抽奖策略配置数据
        StrategyRich strategyRich = strategyRepository.queryStrategyRich(req.getStrategyId());
        Strategy strategy = strategyRich.getStrategy();
        List<StrategyDetail> strategyDetailList = strategyRich.getStrategyDetails();

        // 校验和初始化数据
        checkAndInitRateTuple(req.getStrategyId(), strategy.getStrategyMode(), strategyDetailList);

        // 根据策略方式抽奖
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategy.getStrategyMode());
        String awardId = drawAlgorithm.randomDraw(req.getStrategyId(), new ArrayList<>());

        // 获取奖品信息
        Award award = strategyRepository.queryAwardInfo(awardId);

        logger.info("执行策略抽奖完成，中奖用户：{} 奖品ID：{} 奖品名称：{}", req.getuId(), awardId, award.getAwardName());

        // 封装结果
        return new DrawRes(req.getuId(), req.getStrategyId(), awardId, award.getAwardName());
    }
}
