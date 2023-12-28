package edu.nuaa.lottery.infrastructure.dao;

import edu.nuaa.lottery.infrastructure.po.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 19:31
 */
@Mapper
public interface IActivityDao {
    void insert(Activity req);
    Activity queryActivityById(Long activityId);
}
