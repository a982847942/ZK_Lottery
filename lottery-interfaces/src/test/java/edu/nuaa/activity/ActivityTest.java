package edu.nuaa.activity;

import com.alibaba.fastjson.JSON;
import edu.nuaa.lottery.LotteryApplication;
import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.common.AwardConstants;
import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import edu.nuaa.lottery.domain.activity.model.req.ActivityConfigReq;
import edu.nuaa.lottery.domain.activity.model.req.ParTakeReq;
import edu.nuaa.lottery.domain.activity.model.res.ParTakeRes;
import edu.nuaa.lottery.domain.activity.model.vo.ActivityVO;
import edu.nuaa.lottery.domain.activity.model.vo.AwardVO;
import edu.nuaa.lottery.domain.activity.model.vo.StrategyDetailVO;
import edu.nuaa.lottery.domain.activity.model.vo.StrategyVO;
import edu.nuaa.lottery.domain.activity.service.deploy.IActivityDeploy;
import edu.nuaa.lottery.domain.activity.service.partake.IActivityPartake;
import edu.nuaa.lottery.domain.activity.service.stateflow.IStateHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 14:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotteryApplication.class)
public class ActivityTest {

    private Logger logger = LoggerFactory.getLogger(ActivityTest.class);

    @Resource
    private IActivityDeploy activityDeploy;

    @Resource
    private IStateHandler stateHandler;

    private ActivityConfigRich activityConfigRich;
    @Resource
    private IActivityPartake activityPartake;

    /**
     * TODO：后面编写ID生成策略
     */
    private Long activityId = 120981321L;

//    @Before
    public void init() {

        ActivityVO activity = new ActivityVO();
        activity.setActivityId(activityId);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("测试活动描述");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(ActivityConstants.ActivityState.EDIT.getCode());
        activity.setCreator("brain");

        StrategyVO strategy = new StrategyVO();
        strategy.setStrategyId(10002L);
        strategy.setStrategyDesc("抽奖策略");
        strategy.setStrategyMode(DrawAlgorithmConstants.StrategyMode.SINGLE.getCode());
        strategy.setGrantType(1);
        strategy.setGrantDate(new Date());
        strategy.setExtInfo("");

        StrategyDetailVO strategyDetail_01 = new StrategyDetailVO();
        strategyDetail_01.setStrategyId(strategy.getStrategyId());
        strategyDetail_01.setAwardId("101");
        strategyDetail_01.setAwardName("一等奖");
        strategyDetail_01.setAwardCount(10);
        strategyDetail_01.setAwardSurplusCount(10);
        strategyDetail_01.setAwardRate(new BigDecimal("0.05"));

        StrategyDetailVO strategyDetail_02 = new StrategyDetailVO();
        strategyDetail_02.setStrategyId(strategy.getStrategyId());
        strategyDetail_02.setAwardId("102");
        strategyDetail_02.setAwardName("二等奖");
        strategyDetail_02.setAwardCount(20);
        strategyDetail_02.setAwardSurplusCount(20);
        strategyDetail_02.setAwardRate(new BigDecimal("0.15"));

        StrategyDetailVO strategyDetail_03 = new StrategyDetailVO();
        strategyDetail_03.setStrategyId(strategy.getStrategyId());
        strategyDetail_03.setAwardId("103");
        strategyDetail_03.setAwardName("三等奖");
        strategyDetail_03.setAwardCount(50);
        strategyDetail_03.setAwardSurplusCount(50);
        strategyDetail_03.setAwardRate(new BigDecimal("0.20"));

        StrategyDetailVO strategyDetail_04 = new StrategyDetailVO();
        strategyDetail_04.setStrategyId(strategy.getStrategyId());
        strategyDetail_04.setAwardId("104");
        strategyDetail_04.setAwardName("四等奖");
        strategyDetail_04.setAwardCount(100);
        strategyDetail_04.setAwardSurplusCount(100);
        strategyDetail_04.setAwardRate(new BigDecimal("0.25"));

        StrategyDetailVO strategyDetail_05 = new StrategyDetailVO();
        strategyDetail_05.setStrategyId(strategy.getStrategyId());
        strategyDetail_05.setAwardId("104");
        strategyDetail_05.setAwardName("五等奖");
        strategyDetail_05.setAwardCount(500);
        strategyDetail_05.setAwardSurplusCount(500);
        strategyDetail_05.setAwardRate(new BigDecimal("0.35"));

        List<StrategyDetailVO> strategyDetailList = new ArrayList<>();
        strategyDetailList.add(strategyDetail_01);
        strategyDetailList.add(strategyDetail_02);
        strategyDetailList.add(strategyDetail_03);
        strategyDetailList.add(strategyDetail_04);
        strategyDetailList.add(strategyDetail_05);

        strategy.setStrategyDetailList(strategyDetailList);

        AwardVO award_01 = new AwardVO();
        award_01.setAwardId("101");
        award_01.setAwardType(AwardConstants.AwardType.DESC.getCode());
        award_01.setAwardName("电脑");
        award_01.setAwardContent("请联系活动组织者 brain");

        AwardVO award_02 = new AwardVO();
        award_02.setAwardId("102");
        award_02.setAwardType(AwardConstants.AwardType.DESC.getCode());
        award_02.setAwardName("手机");
        award_02.setAwardContent("请联系活动组织者 brain");

        AwardVO award_03 = new AwardVO();
        award_03.setAwardId("103");
        award_03.setAwardType(AwardConstants.AwardType.DESC.getCode());
        award_03.setAwardName("平板");
        award_03.setAwardContent("请联系活动组织者 brain");

        AwardVO award_04 = new AwardVO();
        award_04.setAwardId("104");
        award_04.setAwardType(AwardConstants.AwardType.DESC.getCode());
        award_04.setAwardName("耳机");
        award_04.setAwardContent("请联系活动组织者 brain");

        AwardVO award_05 = new AwardVO();
        award_05.setAwardId("105");
        award_05.setAwardType(AwardConstants.AwardType.DESC.getCode());
        award_05.setAwardName("数据线");
        award_05.setAwardContent("请联系活动组织者 brain");

        List<AwardVO> awardList = new ArrayList<>();
        awardList.add(award_01);
        awardList.add(award_02);
        awardList.add(award_03);
        awardList.add(award_04);
        awardList.add(award_05);

        activityConfigRich = new ActivityConfigRich(activity, strategy, awardList);
    }

    @Test
    public void test_createActivity() {
        activityDeploy.createActivity(new ActivityConfigReq(activityId, activityConfigRich));
    }

    @Test
    public void test_alterState() {
        logger.info("提交审核，测试：{}", JSON.toJSONString(stateHandler.arraignment(100001L, ActivityConstants.ActivityState.EDIT)));
        logger.info("审核通过，测试：{}", JSON.toJSONString(stateHandler.checkPass(100001L, ActivityConstants.ActivityState.ARRAIGNMENT)));
        logger.info("运行活动，测试：{}", JSON.toJSONString(stateHandler.doing(100001L, ActivityConstants.ActivityState.PASS)));
        logger.info("二次提审，测试：{}", JSON.toJSONString(stateHandler.checkPass(100001L, ActivityConstants.ActivityState.EDIT)));
    }

    @Test
    public void test_activityPartake() {
        ParTakeReq req = new ParTakeReq("Uhdgkw766120c", 100001L,new Date());
        ParTakeRes res = activityPartake.doParTake(req);
        logger.info("请求参数：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }

}
