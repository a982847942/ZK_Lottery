package edu.nuaa.application;

import edu.nuaa.lottery.LotteryApplication;
import edu.nuaa.lottery.application.mq.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/4 20:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotteryApplication.class)
public class KafkaProducerTest {

    private Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Resource
    private KafkaProducer kafkaProducer;

    @Test
    public void test_send() throws InterruptedException {
        // 循环发送消息
        while (true) {
            kafkaProducer.send("你好，我是Lottery 001");
            Thread.sleep(3500);
        }
    }
}
