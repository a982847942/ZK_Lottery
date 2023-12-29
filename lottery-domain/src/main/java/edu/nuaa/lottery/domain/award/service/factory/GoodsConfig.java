package edu.nuaa.lottery.domain.award.service.factory;

import edu.nuaa.lottery.common.AwardConstants;
import edu.nuaa.lottery.domain.award.service.goods.IDistributionGoods;
import edu.nuaa.lottery.domain.award.service.goods.impl.CouponGoods;
import edu.nuaa.lottery.domain.award.service.goods.impl.DescGoods;
import edu.nuaa.lottery.domain.award.service.goods.impl.PhysicalGoods;
import edu.nuaa.lottery.domain.award.service.goods.impl.RedeemCodeGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 20:18
 */
public class GoodsConfig {
    protected static Map<Integer, IDistributionGoods> distributionGoodsMap = new ConcurrentHashMap<>();

    @Resource
    CouponGoods couponGoods;
    @Resource
    DescGoods descGoods;
    @Resource
    PhysicalGoods physicalGoods;
    @Resource
    RedeemCodeGoods redeemCodeGoods;

    @PostConstruct
    public void init(){
        distributionGoodsMap.put(AwardConstants.AwardType.CouponGoods.getCode(),couponGoods);
        distributionGoodsMap.put(AwardConstants.AwardType.DESC.getCode(), descGoods);
        distributionGoodsMap.put(AwardConstants.AwardType.PhysicalGoods.getCode(), physicalGoods);
        distributionGoodsMap.put(AwardConstants.AwardType.RedeemCodeGoods.getCode(), redeemCodeGoods);
    }
}
