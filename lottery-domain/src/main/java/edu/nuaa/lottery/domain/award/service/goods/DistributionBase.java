package edu.nuaa.lottery.domain.award.service.goods;

import edu.nuaa.lottery.domain.award.repository.IOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 20:02
 */
public class DistributionBase {
    protected Logger logger = LoggerFactory.getLogger(DistributionBase.class);

    @Resource
    private IOrderRepository awardRepository;
    protected void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        //后期添加更新分库分表中，用户个人的抽奖记录表中奖品发奖状态
        awardRepository.updateUserAwardState(uId, orderId, awardId, grantState);
    }
}
