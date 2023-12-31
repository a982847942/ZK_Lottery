package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.UserTakeActivity;
import edu.nuaa.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 12:40
 */
@Mapper
public interface IUserTakeActivityDao {

    /**
     * 插入用户领取活动信息
     *
     * @param userTakeActivity 入参
     */
    @DBRouter(key = "uId")
    void insert(UserTakeActivity userTakeActivity);

}
