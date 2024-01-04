package edu.nuaa.lottery.rpc.req;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description 量化人群抽奖请求
 * @author brain
 * @version 1.0
 * @date 2024/1/3 14:29
 */
public class QuantificationDrawReq implements Serializable {
    /** 用户ID */
    private String uId;
    /** 规则树ID */
    private Long treeId;
    /** 决策key,value  组合树有多种决策key */
    private Map<String, Object> valMap;

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Map<String, Object> getValMap() {
        return valMap;
    }

    public void setValMap(Map<String, Object> valMap) {
        this.valMap = valMap;
    }
}
