package com.myself.deployrequester.biz.config.sharedata;

/**
 * 发布情况的枚举类
 * Created by QueRenJie on ${date}
 */
public enum DeployEnum {
    DEPLOYING("1"), DEPLOY_SUCCESS("2"), DEPLOY_FAILED("-1");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private DeployEnum(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
