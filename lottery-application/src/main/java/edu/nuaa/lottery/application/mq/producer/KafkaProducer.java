package edu.nuaa.lottery.application.mq.producer;

import com.alibaba.fastjson.JSON;
import edu.nuaa.lottery.domain.activity.model.vo.InvoiceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/4 20:31
 */
@Component
public class KafkaProducer {
    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC_INVOICE = "lottery_invoice";

    public ListenableFuture<SendResult<String, Object>> sendLotteryInvoice(InvoiceVO invoiceVO) {
        String message = JSON.toJSONString(invoiceVO);
        logger.info("发送奖品信息到MQ, topic: {} bizId: {} message: {}",TOPIC_INVOICE,invoiceVO.getuId(),message);
        return kafkaTemplate.send(TOPIC_INVOICE, message);
    }


//    public static final String TOPIC_GROUP = "test-consumer-group";

//    public void send(Object obj) {
//        String obj2String = JSON.toJSONString(obj);
//        logger.info("准备发送消息为：{}", obj2String);
//
//        // 发送消息
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_TEST, obj);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                //发送失败的处理
//                logger.info(TOPIC_TEST + " - 生产者 发送消息失败：" + throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
//                //成功的处理
//                logger.info(TOPIC_TEST + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
//            }
//        });
//    }
}
