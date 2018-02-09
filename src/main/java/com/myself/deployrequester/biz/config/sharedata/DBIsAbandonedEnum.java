package com.myself.deployrequester.biz.config.sharedata;

/**
 * Created by QueRenJie on ${date}
 */
public enum DBIsAbandonedEnum {
    NOT_ABANDONED(0, "可以继续执行剩余的sql"), ABANDONED(1, "已经放弃剩余sql的执行");

    private int code;
    private String desc;

    private DBIsAbandonedEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (DBIsAbandonedEnum obj : DBIsAbandonedEnum.values()) {
            if (code == obj.getCode()) {
                return obj.getDesc();
            }
        }
        return null;
    }

}
