package edu.nuaa.lottery.infrastructure.repository;

import edu.nuaa.lottery.domain.award.repository.IOrderRepository;
import edu.nuaa.lottery.infrastructure.dao.IUserStrategyExportDao;
import edu.nuaa.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 10:50
 */
@Repository
public class AwardRepository implements IOrderRepository {
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);
    }
}
