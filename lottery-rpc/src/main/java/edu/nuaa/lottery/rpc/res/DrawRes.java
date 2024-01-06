package edu.nuaa.lottery.rpc.res;

import edu.nuaa.lottery.common.Result;
import edu.nuaa.lottery.rpc.dto.AwardDTO;

import java.io.Serializable;

/**
 * @description: 抽奖结果
 * @author brain
 * @version 1.0
 * @date 2024/1/5 10:45
 */
public class DrawRes extends Result implements Serializable {

    private AwardDTO awardDTO;

    public DrawRes(String code, String info) {
        super(code, info);
    }

    public AwardDTO getAwardDTO() {
        return awardDTO;
    }

    public void setAwardDTO(AwardDTO awardDTO) {
        this.awardDTO = awardDTO;
    }

}