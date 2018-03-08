package com.myself.deployrequester.biz.config.sharedata;

/**
 * Created by QueRenJie on ${date}
 * 发布开关的枚举类
 */
public enum DeploySwitchEnum {
    SWITCH_OFF(Short.valueOf("0"), "发布开关未开启"), SWITCH_ON(Short.valueOf("1"), "发布开关开启");

    private Short code;
    private String desc;

    private DeploySwitchEnum(Short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByCode(Short code) {
        for (DeploySwitchEnum deploySwitchEnum : DeploySwitchEnum.values()) {
            if (code.shortValue() == deploySwitchEnum.getCode().shortValue()) {
                return deploySwitchEnum.getDesc();
            }
        }
        return null;
    }

}
