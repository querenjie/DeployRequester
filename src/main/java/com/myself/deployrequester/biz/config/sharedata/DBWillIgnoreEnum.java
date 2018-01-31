package com.myself.deployrequester.biz.config.sharedata;

/**
 * Created by QueRenJie on ${date}
 */
public enum DBWillIgnoreEnum {
    NOT_IGNORE(0, "需要执行的"), IGNORE(1, "需要忽略的");

    private int code;
    private String desc;

    private DBWillIgnoreEnum(int code, String desc) {
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
        for (DBWillIgnoreEnum dbWillIgnoreEnum : DBWillIgnoreEnum.values()) {
            if (code == dbWillIgnoreEnum.getCode()) {
                return dbWillIgnoreEnum.getDesc();
            }
        }
        return null;
    }

}
