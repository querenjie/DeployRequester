package com.myself.deployrequester.biz.config.sharedata;

/**
 * Created by QueRenJie on ${date}
 */
public enum DeployStatusForProdEnvEnum {
    DID_NOT_DEPLOYED(0, "未发布"), DEPLOYING(1, "正在发布"),
    DEPLOYED_SUCCESSFULLY(2, "发布生产成功"), DEPLOYED_FAILED(-1, "发布失败"),
    DEPLOYED_PREPROD_SUCCESSFULLY(3, "预发布成功");

    private int code;
    private String desc;

    private DeployStatusForProdEnvEnum(int code, String desc) {
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
        for (DeployStatusForProdEnvEnum deployStatusForProdEnvEnum : DeployStatusForProdEnvEnum.values()) {
            if (code == deployStatusForProdEnvEnum.getCode()) {
                return deployStatusForProdEnvEnum.getDesc();
            }
        }
        return null;
    }
}
