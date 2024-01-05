package edu.nuaa.lottery.domain.rule.service.engine;

import edu.nuaa.lottery.domain.rule.service.logic.LogicFilter;
import edu.nuaa.lottery.domain.rule.service.logic.impl.UserAgeFilter;
import edu.nuaa.lottery.domain.rule.service.logic.impl.UserGenderFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/2 15:58
 */
public class EngineConfig {
    /**
     * 过滤器集合对象
     */
    protected static Map<String, LogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    @Resource
    UserAgeFilter userAgeFilter;
    @Resource
    UserGenderFilter userGenderFilter;

    @PostConstruct
    public void init() {
        logicFilterMap.put("userAge", userAgeFilter);
        logicFilterMap.put("userGender", userGenderFilter);
    }
}
