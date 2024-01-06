package edu.nuaa.lottery.application.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import edu.nuaa.lottery.common.AwardConstants;
import edu.nuaa.lottery.domain.activity.model.vo.InvoiceVO;
import edu.nuaa.lottery.domain.award.model.req.GoodsReq;
import edu.nuaa.lottery.domain.award.model.res.DistributionRes;
import edu.nuaa.lottery.domain.award.service.factory.DistributionGoodsFactory;
import edu.nuaa.lottery.domain.award.service.goods.IDistributionGoods;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/4 20:33
 */
@Component
public class LotteryInvoiceListener {
    private Logger logger = LoggerFactory.getLogger(LotteryInvoiceListener.class);
    @Resource
    DistributionGoodsFactory distributionGoodsFactory;

    @KafkaListener(topics = "lottery_invoice", groupId = "lottery")
    public void topicTest(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (!message.isPresent()){
            return;
        }

        try {
            InvoiceVO invoiceVO = JSON.parseObject((String) message.get(), InvoiceVO.class);
            IDistributionGoods distributionGoodsService = distributionGoodsFactory.getDistributionGoodsService(invoiceVO.getAwardType());
            DistributionRes distributionRes = distributionGoodsService.doDistribution(new GoodsReq(invoiceVO.getuId(), invoiceVO.getOrderId(), invoiceVO.getAwardId(), invoiceVO.getAwardName(), invoiceVO.getAwardContent()));
            Assert.isTrue(AwardConstants.AwardState.SUCCESS.getCode().equals(distributionRes.getCode()),distributionRes.getInfo());

            // 3. 打印日志
            logger.info("消费MQ消息，完成 topic：{} bizId：{} 发奖结果：{}", topic, invoiceVO.getuId(), JSON.toJSONString(distributionRes));
            ack.acknowledge();

        }catch (Exception e){
            // 发奖环节失败，消息重试。所有到环节，发货、更新库，都需要保证幂等。
            logger.error("消费MQ消息，失败 topic：{} message：{}", topic, message.get());
            throw e;
        }

    }


//    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP)
//    public void topicTest(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//        Optional<?> message = Optional.ofNullable(record.value());
//        if (message.isPresent()) {
//            Object msg = message.get();
//            logger.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
//            ack.acknowledge();
//        }
//    }
}
