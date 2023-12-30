package edu.nuaa.lottery.domain.suppot.ids;

import edu.nuaa.lottery.common.SupportConstants;
import edu.nuaa.lottery.domain.suppot.ids.IIdGenerator;
import edu.nuaa.lottery.domain.suppot.ids.police.RandomNumeric;
import edu.nuaa.lottery.domain.suppot.ids.police.ShortCode;
import edu.nuaa.lottery.domain.suppot.ids.police.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/30 11:20
 */
@Configuration
public class IdContext {
    /**
     * 创建 ID 生成策略对象，属于策略设计模式的使用方式
     *
     * @param snowFlake 雪花算法，长码，大量
     * @param shortCode 日期算法，短码，少量，全局唯一需要自己保证
     * @param randomNumeric 随机算法，短码，大量，全局唯一需要自己保证
     * @return IIdGenerator 实现类
     */
    @Bean
    public Map<SupportConstants.IDS, IIdGenerator> idGenerator(SnowFlake snowFlake, ShortCode shortCode, RandomNumeric randomNumeric){
        Map<SupportConstants.IDS,IIdGenerator> idGeneratorMap = new HashMap<>(8);
        idGeneratorMap.put(SupportConstants.IDS.SnowFlake,snowFlake);
        idGeneratorMap.put(SupportConstants.IDS.ShortCode,shortCode);
        idGeneratorMap.put(SupportConstants.IDS.RandomNumeric,randomNumeric);
        return idGeneratorMap;
    }
}
