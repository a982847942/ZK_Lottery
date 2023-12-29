package edu.nuaa.lottery.common;

import java.io.Serializable;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 19:04
 */
public class Result implements Serializable {
    private static final long serialVersionUID = -3826891916021780628L;
    private String code;
    private String info;

    public Result(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static Result buildResult(Constants.ResponseCode code, String info){
        return new Result(code.getCode(),info);
    }

    public static Result buildSuccessResult(){
        return new Result(Constants.ResponseCode.SUCCESS.getCode(),Constants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildErrorResult(){
        return new Result(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), Constants.ResponseCode.UNKNOWN_ERROR.getInfo());
    }
    public static Result buildErrorResult(String info){
        return new Result(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), info);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
