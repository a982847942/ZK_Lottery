package edu.nuaa.lottery.domain.award.service.goods.impl;

import edu.nuaa.lottery.common.AwardConstants;
import edu.nuaa.lottery.domain.award.model.req.GoodsReq;
import edu.nuaa.lottery.domain.award.model.res.DistributionRes;
import edu.nuaa.lottery.domain.award.service.goods.DistributionBase;
import edu.nuaa.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 20:06
 */
@Component
public class PhysicalGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {

        // 模拟调用实物发奖
        logger.info("模拟调用实物发奖 uId：{} awardContent：{}", req.getuId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), AwardConstants.AwardState.SUCCESS.getCode(), AwardConstants.AwardState.SUCCESS.getInfo());

        return new DistributionRes(req.getuId(), AwardConstants.AwardState.SUCCESS.getCode(), AwardConstants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return AwardConstants.AwardType.PhysicalGoods.getCode();
    }
}
