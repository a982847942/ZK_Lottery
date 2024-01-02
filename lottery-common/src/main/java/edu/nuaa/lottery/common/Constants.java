package edu.nuaa.lottery.common;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 19:07
 */
public class Constants {
    public enum ResponseCode{
        SUCCESS("0000","成功"),
        UNKNOWN_ERROR("0001","未知失败"),
        ILLEGAL_PARAMETER("0002","非法参数"),
        INDEX_DUP("0003","主键冲突"),
        NO_UPDATE("0004","SQL操作无更新"),
        LOSING_DRAW("D001", "未中奖");
        private String code;
        private String info;

        ResponseCode(String code, String info) {
            this.code = code;
            this.info = info;
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
}
