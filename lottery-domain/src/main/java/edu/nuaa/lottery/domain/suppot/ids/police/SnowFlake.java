package edu.nuaa.lottery.domain.suppot.ids.police;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import edu.nuaa.lottery.domain.suppot.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/30 11:20
 */
@Component
public class SnowFlake implements IIdGenerator {
    private Snowflake snowflake;
    @Override
    public long nextId() {
        return snowflake.nextId();
    }
    @PostConstruct
    public void init(){
        // 0 ~ 31 位，可以采用配置的方式使用
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;
        snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
    }
}
