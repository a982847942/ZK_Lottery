package edu.nuaa.rule;

import com.alibaba.fastjson.JSON;
import edu.nuaa.lottery.LotteryApplication;
import edu.nuaa.lottery.domain.rule.model.req.DecisionMatterReq;
import edu.nuaa.lottery.domain.rule.model.res.EngineResult;
import edu.nuaa.lottery.domain.rule.service.engine.EngineFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 19:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotteryApplication.class)
public class RuleTest {

    private Logger logger = LoggerFactory.getLogger(RuleTest.class);

    @Resource
    private EngineFilter engineFilter;

    @Test
    public void test_process() {
        DecisionMatterReq req = new DecisionMatterReq();
        req.setTreeId(2110081902L);
        req.setUserId("fustack");
        req.setValMap(new HashMap<String, Object>() {{
            put("gender", "man");
            put("age", "25");
        }});

        EngineResult res = engineFilter.process(req);

        logger.info("请求参数：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }

    @Test
    public void test(){
        Map<Integer,String> map  = new HashMap<>();
        map.put(1,"xixi");
        System.out.println(map.get(0));
    }

}