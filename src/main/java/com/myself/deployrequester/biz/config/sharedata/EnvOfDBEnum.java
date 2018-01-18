package com.myself.deployrequester.biz.config.sharedata;

/**
 * 脚本发布的环境
 * Created by QueRenJie on ${date}
 */
public enum EnvOfDBEnum {
    PRE_PROD_ENV(1, "预发环境"), PROD_ENV(2, "生产环境");

    private int code;
    private String desc;

    private EnvOfDBEnum(int code, String desc) {
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
        for (EnvOfDBEnum envOfDBEnum : EnvOfDBEnum.values()) {
            if (envOfDBEnum.getCode() == code) {
                return envOfDBEnum.getDesc();
            }
        }
        return null;
    }

}
