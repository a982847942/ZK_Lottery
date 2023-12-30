package edu.nuaa.activity;

import edu.nuaa.lottery.LotteryApplication;
import edu.nuaa.lottery.common.SupportConstants;
import edu.nuaa.lottery.domain.suppot.ids.IIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotteryApplication.class)
public class SupportTest {

    private Logger logger = LoggerFactory.getLogger(SupportTest.class);

    @Resource
    private Map<SupportConstants.IDS, IIdGenerator> idGeneratorMap;

    @Test
    public void test_ids() {
        logger.info("雪花算法策略，生成ID：{}", idGeneratorMap.get(SupportConstants.IDS.SnowFlake).nextId());
        logger.info("日期算法策略，生成ID：{}", idGeneratorMap.get(SupportConstants.IDS.ShortCode).nextId());
        logger.info("随机算法策略，生成ID：{}", idGeneratorMap.get(SupportConstants.IDS.RandomNumeric).nextId());
    }

}
