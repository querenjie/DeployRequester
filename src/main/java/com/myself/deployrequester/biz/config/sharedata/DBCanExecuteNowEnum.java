package com.myself.deployrequester.biz.config.sharedata;

/**
 * Created by QueRenJie on ${date}
 */
public enum DBCanExecuteNowEnum {
    EXEC_FORBIDED(0, "暂缓执行"), EXECUTE_ALLOWED(1, "随时都可执行");

    private int code;
    private String desc;

    private DBCanExecuteNowEnum(int code, String desc) {
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
        for (DBCanExecuteNowEnum dbCanExecuteNowEnum : DBCanExecuteNowEnum.values()) {
            if (code == dbCanExecuteNowEnum.getCode()) {
                return dbCanExecuteNowEnum.getDesc();
            }
        }
        return null;
    }

}
