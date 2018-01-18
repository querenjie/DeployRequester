package com.myself.deployrequester.biz.config.sharedata;

/**
 * Created by QueRenJie on ${date}
 */
public enum RoleEnum {
    DEVELOPER(1, "开发人员"), TESTER(2, "测试人员");

    private int code;
    private String desc;

    private RoleEnum(int code, String desc) {
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
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getCode() == code) {
                return roleEnum.getDesc();
            }
        }
        return null;
    }
}
