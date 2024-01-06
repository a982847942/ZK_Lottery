package edu.nuaa.application;

import edu.nuaa.lottery.LotteryApplication;
import edu.nuaa.lottery.application.mq.producer.KafkaProducer;
import edu.nuaa.lottery.common.AwardConstants;
import edu.nuaa.lottery.domain.activity.model.vo.InvoiceVO;
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

        InvoiceVO invoice = new InvoiceVO();
        invoice.setuId("fustack");
        invoice.setOrderId(1444540456057864192L);
        invoice.setAwardId("3");
        invoice.setAwardType(AwardConstants.AwardType.DESC.getCode());
        invoice.setAwardName("Code");
        invoice.setAwardContent("苹果电脑");
        invoice.setShippingAddress(null);
        invoice.setExtInfo(null);

        kafkaProducer.sendLotteryInvoice(invoice);

        while (true){
            Thread.sleep(10000);
        }
    }
}
