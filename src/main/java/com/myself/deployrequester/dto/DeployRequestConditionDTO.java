package com.myself.deployrequester.dto;

/**
 * This DTO encapsulates the conditions on the page which is responsible for showing
 * request list to users.
 * Created by QueRenJie on ${date}
 */
public class DeployRequestConditionDTO {
    //开发人员, it's also an applicant
    private String developer;
    //是否只显示最近一次申请的情况。
    private String onlyGetLatestRequest = "N";
    private String beginDateOfApplyDate;
    private String endDateOfApplyDate;
    //是否已上测试环境成功
    private String isDeployOkOnTestEnv = "N";
    //是否在测试环境测试通过
    private String isPassedOnTestEnv = "N";
    //是否已上生产环境成功
    private String getIsDeployOkOnProdEnv = "N";

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getOnlyGetLatestRequest() {
        return onlyGetLatestRequest;
    }

    public void setOnlyGetLatestRequest(String onlyGetLatestRequest) {
        this.onlyGetLatestRequest = onlyGetLatestRequest;
    }

    public String getBeginDateOfApplyDate() {
        return beginDateOfApplyDate;
    }

    public void setBeginDateOfApplyDate(String beginDateOfApplyDate) {
        this.beginDateOfApplyDate = beginDateOfApplyDate;
    }

    public String getEndDateOfApplyDate() {
        return endDateOfApplyDate;
    }

    public void setEndDateOfApplyDate(String endDateOfApplyDate) {
        this.endDateOfApplyDate = endDateOfApplyDate;
    }

    public String getIsDeployOkOnTestEnv() {
        return isDeployOkOnTestEnv;
    }

    public void setIsDeployOkOnTestEnv(String isDeployOkOnTestEnv) {
        this.isDeployOkOnTestEnv = isDeployOkOnTestEnv;
    }

    public String getIsPassedOnTestEnv() {
        return isPassedOnTestEnv;
    }

    public void setIsPassedOnTestEnv(String isPassedOnTestEnv) {
        this.isPassedOnTestEnv = isPassedOnTestEnv;
    }

    public String getGetIsDeployOkOnProdEnv() {
        return getIsDeployOkOnProdEnv;
    }

    public void setGetIsDeployOkOnProdEnv(String getIsDeployOkOnProdEnv) {
        this.getIsDeployOkOnProdEnv = getIsDeployOkOnProdEnv;
    }
}
