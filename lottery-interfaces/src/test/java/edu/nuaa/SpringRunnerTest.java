package edu.nuaa;

import com.alibaba.fastjson.JSON;
import edu.nuaa.lottery.LotteryApplication;
import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.domain.award.model.req.GoodsReq;
import edu.nuaa.lottery.domain.award.model.res.DistributionRes;
import edu.nuaa.lottery.domain.award.service.factory.DistributionGoodsFactory;
import edu.nuaa.lottery.domain.award.service.goods.IDistributionGoods;
import edu.nuaa.lottery.domain.strategy.model.req.DrawReq;
import edu.nuaa.lottery.domain.strategy.model.res.DrawRes;
import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardVO;
import edu.nuaa.lottery.domain.strategy.service.draw.IDrawExec;
import edu.nuaa.lottery.infrastructure.dao.IActivityDao;
import edu.nuaa.lottery.infrastructure.po.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotteryApplication.class)
public class SpringRunnerTest {

    private Logger logger = LoggerFactory.getLogger(SpringRunnerTest.class);

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IDrawExec drawExec;

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @Test
    public void test_drawExec() {
        drawExec.doDrawExec(new DrawReq("陈士硕", 10001L));
        drawExec.doDrawExec(new DrawReq("于晓晴", 10001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 10001L));
    }


    @Test
    public void test_award() {
        // 执行抽奖
        DrawRes drawResult = drawExec.doDrawExec(new DrawReq("brain", 10001L));

        // 判断抽奖结果
        Integer drawState = drawResult.getDrawState();
        if (DrawAlgorithmConstants.DrawState.FAIL.getCode().equals(drawState)) {
            logger.info("未中奖 DrawAwardInfo is null");
            return;
        }

        // 封装发奖参数，orderId：2109313442431 为模拟ID，需要在用户参与领奖活动时生成
        DrawAwardVO drawAwardVO = drawResult.getDrawAwardInfo();
        GoodsReq goodsReq = new GoodsReq(drawResult.getuId(), "2109313442431", drawAwardVO.getAwardId(), drawAwardVO.getAwardName(), drawAwardVO.getAwardContent());

        // 根据 awardType 从抽奖工厂中获取对应的发奖服务
        IDistributionGoods distributionGoodsService = distributionGoodsFactory.getDistributionGoodsService(drawAwardVO.getAwardType());
        DistributionRes distributionRes = distributionGoodsService.doDistribution(goodsReq);

        logger.info("测试结果：{}", JSON.toJSONString(distributionRes));
    }
    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100001L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("brain");
        activityDao.insert(activity);
    }

    @Test
    public void test_select() {
        Activity activity = activityDao.queryActivityById(100001L);
        logger.info("测试结果：{}", JSON.toJSONString(activity));
    }

}
