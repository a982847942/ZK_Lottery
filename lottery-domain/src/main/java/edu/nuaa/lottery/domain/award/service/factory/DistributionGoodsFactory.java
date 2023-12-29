package edu.nuaa.lottery.domain.award.service.factory;

import edu.nuaa.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/28 20:18
 */
@Component
public class DistributionGoodsFactory extends GoodsConfig{
    public IDistributionGoods getDistributionGoodsService(Integer awardType){
        return distributionGoodsMap.get(awardType);
    }
}
