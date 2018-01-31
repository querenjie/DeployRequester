package com.myself.deployrequester.biz.config.sharedata;

/**
 * Created by QueRenJie on ${date}
 */
public enum DBExecuteStatusEnum {
    NOT_EXECUTE_YET(0, "未执行"), EXECUTE_SUCCESSFULLY(1, "发布成功"), EXECUTE_FAILED(-1, "发布失败");

    private int code;
    private String desc;

    private DBExecuteStatusEnum(int code, String desc) {
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
        for (DBExecuteStatusEnum dbExecuteStatusEnum : DBExecuteStatusEnum.values()) {
            if (code == dbExecuteStatusEnum.getCode()) {
                return dbExecuteStatusEnum.getDesc();
            }
        }
        return null;
    }
}