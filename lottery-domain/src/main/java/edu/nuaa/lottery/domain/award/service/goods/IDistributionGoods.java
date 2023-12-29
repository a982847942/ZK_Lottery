package edu.nuaa.lottery.domain.award.service.goods;

import edu.nuaa.lottery.domain.award.model.req.GoodsReq;
import edu.nuaa.lottery.domain.award.model.res.DistributionRes;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 20:02
 */
public interface IDistributionGoods {
    /**
     * 奖品配送接口，奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     *
     * @param req   物品信息
     * @return      配送结果
     */
    DistributionRes doDistribution(GoodsReq req);

    Integer getDistributionGoodsName();
}
