package com.yiche.bdc.dataexport.constant;

/**
 * 常量
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author weiyx
     * @email weiyx@yiche.com
     * @date 2018年06月5日 上午11:47:22
     */
    public enum ScheduleAlarm {
        /**
         * 不报警
         */
        NOT_ALARM(0),
        /**
         * 报警
         */
        ALARM(1);

        private int value;

        private ScheduleAlarm(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
