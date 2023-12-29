package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 22:49
 */
@Mapper
public interface IAwardDao {
    /**
     * 查询奖品信息
     *
     * @param awardId 奖品ID
     * @return        奖品信息
     */
    Award queryAwardInfo(String awardId);

    /**
     * 插入奖品信息
     * @param awardList 奖品信息列表
     * @return
     */
    void insertAwardList(List<Award> awardList);
}
