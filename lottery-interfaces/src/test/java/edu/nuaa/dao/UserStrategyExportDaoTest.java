package edu.nuaa.dao;

import com.alibaba.fastjson.JSON;
import edu.nuaa.lottery.LotteryApplication;
import edu.nuaa.lottery.common.AwardConstants;
import edu.nuaa.lottery.common.DrawAlgorithmConstants;
import edu.nuaa.lottery.common.SupportConstants;
import edu.nuaa.lottery.domain.suppot.ids.IIdGenerator;
import edu.nuaa.lottery.infrastructure.dao.IUserStrategyExportDao;
import edu.nuaa.lottery.infrastructure.po.UserStrategyExport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 13:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotteryApplication.class)
public class UserStrategyExportDaoTest {

    private Logger logger = LoggerFactory.getLogger(UserStrategyExportDaoTest.class);

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Resource
    private Map<SupportConstants.IDS, IIdGenerator> idGeneratorMap;

    @Test
    public void test_insert() {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId("Uhdgkw766120d");
        userStrategyExport.setActivityId(idGeneratorMap.get(SupportConstants.IDS.ShortCode).nextId());
        userStrategyExport.setOrderId(idGeneratorMap.get(SupportConstants.IDS.SnowFlake).nextId());
        userStrategyExport.setStrategyId(idGeneratorMap.get(SupportConstants.IDS.RandomNumeric).nextId());
        userStrategyExport.setStrategyMode(DrawAlgorithmConstants.StrategyMode.SINGLE.getCode());
        userStrategyExport.setGrantType(1);
        userStrategyExport.setGrantDate(new Date());
        userStrategyExport.setGrantState(1);
        userStrategyExport.setAwardId("1");
        userStrategyExport.setAwardType(AwardConstants.AwardType.DESC.getCode());
        userStrategyExport.setAwardName("IMac");
        userStrategyExport.setAwardContent("奖品描述");
        userStrategyExport.setUuid(String.valueOf(userStrategyExport.getOrderId()));

        userStrategyExportDao.insert(userStrategyExport);
    }

    @Test
    public void test_select() {
        UserStrategyExport userStrategyExport = userStrategyExportDao.queryUserStrategyExportByUId("Uhdgkw766120d");
        logger.info("测试结果：{}", JSON.toJSONString(userStrategyExport));
    }

}
