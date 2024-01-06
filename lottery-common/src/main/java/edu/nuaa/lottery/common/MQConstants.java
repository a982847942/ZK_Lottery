package edu.nuaa.lottery.common;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/5 9:41
 */
public class MQConstants {
    /**
     * MQ发送状态： 0 未发送 1 发送成功 2 发送失败
     */
    public enum MQState{
        INIT(0, "初始"),
        COMPLETE(1, "完成"),
        FAIL(2, "失败");
        private Integer code;
        private String info;

        MQState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
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
