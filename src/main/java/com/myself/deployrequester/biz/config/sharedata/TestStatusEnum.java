package com.myself.deployrequester.biz.config.sharedata;

/**
 *  是否测试通过的枚举类
 * Created by QueRenJie on ${date}
 */
public enum TestStatusEnum {
    DID_NOT_TEST(0, "未测试"), TEST_PASSED(1, "测试通过"), TEST_FAILED(-1, "测试不通过"), DO_NOT_NEED_TEST(-2, "无需测试");

    private int code;
    private String desc;

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

    private TestStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (TestStatusEnum testStatusEnum : TestStatusEnum.values()) {
            if (testStatusEnum.getCode() == code) {
                return testStatusEnum.getDesc();
            }
        }
        return null;
    }
}
