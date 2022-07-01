package com.zx.lib.share;

/**
 * Created by colin on 17/3/15.
 */

public class ShearEnum {

    /**
     * 分享类型
     */
    public enum ShareContentTypeEnum {
        COURSE("course"),

        OTHER("other");

        String value;

        ShareContentTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static ShareContentTypeEnum getTypeEnum(String value) {
            for (ShareContentTypeEnum temp : values()) {
                if (temp.getValue().equals(value)) {
                    return temp;
                }
            }
            return OTHER;
        }
    }

    public enum SharePlatType {

        SINA(1),

        QQ(2),

        QZONE(3),

        WEICHAT(4),

        WEIMENT(5),

        RENREN(6);

        int value;

        SharePlatType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static SharePlatType getPlatType(int type) {
            for (SharePlatType temp : values()) {
                if (temp.getValue() == type) {
                    return temp;
                }
            }

            return null;
        }
    }

}
