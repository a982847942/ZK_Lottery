package edu.nuaa.lottery.common;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/30 11:25
 */
public class SupportConstants {
    public enum IDS{
        /** 雪花算法 */
        SnowFlake(0,"雪花算法"),
        /** 日期算法 */
        ShortCode(1,"日期算法"),
        /** 随机算法 */
        RandomNumeric(2,"随机算法");
        private Integer code;
        private String info;

        IDS(Integer code, String info) {
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
