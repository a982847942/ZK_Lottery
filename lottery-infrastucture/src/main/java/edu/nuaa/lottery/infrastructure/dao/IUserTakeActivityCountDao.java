package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.UserTakeActivityCount;
import edu.nuaa.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/31 16:42
 */
@Mapper
public interface IUserTakeActivityCountDao {
    /**
     * 查询用户领取次数信息
     * @param userTakeActivityCountReq 请求入参【活动号、用户ID】
     * @return 领取结果
     */
    @DBRouter
    UserTakeActivityCount queryUserTakeActivityCount(UserTakeActivityCount userTakeActivityCountReq);

    /**
     * 插入领取次数信息
     * @param userTakeActivityCount 请求入参
     */
//    @DBRouter
    void insert(UserTakeActivityCount userTakeActivityCount);

    /**
     * 更新领取次数信息
     * @param userTakeActivityCount 请求入参
     * @return 更新数量
     */
//    @DBRouter
    int updateLeftCount(UserTakeActivityCount userTakeActivityCount);
}
