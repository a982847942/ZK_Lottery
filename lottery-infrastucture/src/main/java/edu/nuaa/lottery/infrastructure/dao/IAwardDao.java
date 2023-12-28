package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 22:49
 */
@Mapper
public interface IAwardDao {
    Award queryAwardInfo(String awardId);
}
