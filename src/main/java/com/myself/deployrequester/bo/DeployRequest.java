package com.myself.deployrequester.bo;

import com.myself.deployrequester.model.DeployRequesterDO;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployRequest extends DeployRequesterDO {
    private String projectName;
    private String moduleCodeName;
    private String moduleDesc;
    private String moduleTypeName;
    private String modifyTypeName;
    private String isTestOkDesc;
    private String deployStatusForProdEnvDesc;
    private String formatedCreateDate;
    private String testflagmodifier;        //修改测试环境是否通过的标志的人员姓名
    private String testflagmodifierip;      //修改测试环境是否通过的标志的人员的IP地址
    private String formatedTestflagmodifytime;      //修改测试环境是否通过的标志的时间。用系统当前时间。


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleCodeName() {
        return moduleCodeName;
    }

    public void setModuleCodeName(String moduleCodeName) {
        this.moduleCodeName = moduleCodeName;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getModuleTypeName() {
        return moduleTypeName;
    }

    public void setModuleTypeName(String moduleTypeName) {
        this.moduleTypeName = moduleTypeName;
    }

    public String getModifyTypeName() {
        return modifyTypeName;
    }

    public void setModifyTypeName(String modifyTypeName) {
        this.modifyTypeName = modifyTypeName;
    }

    public String getIsTestOkDesc() {
        return isTestOkDesc;
    }

    public void setIsTestOkDesc(String isTestOkDesc) {
        this.isTestOkDesc = isTestOkDesc;
    }

    public String getDeployStatusForProdEnvDesc() {
        return deployStatusForProdEnvDesc;
    }

    public void setDeployStatusForProdEnvDesc(String deployStatusForProdEnvDesc) {
        this.deployStatusForProdEnvDesc = deployStatusForProdEnvDesc;
    }

    public String getFormatedCreateDate() {
        return formatedCreateDate;
    }

    public void setFormatedCreateDate(String formatedCreateDate) {
        this.formatedCreateDate = formatedCreateDate;
    }

    @Override
    public String getTestflagmodifier() {
        return testflagmodifier;
    }

    @Override
    public void setTestflagmodifier(String testflagmodifier) {
        this.testflagmodifier = testflagmodifier;
    }

    @Override
    public String getTestflagmodifierip() {
        return testflagmodifierip;
    }

    @Override
    public void setTestflagmodifierip(String testflagmodifierip) {
        this.testflagmodifierip = testflagmodifierip;
    }

    public String getFormatedTestflagmodifytime() {
        return formatedTestflagmodifytime;
    }

    public void setFormatedTestflagmodifytime(String formatedTestflagmodifytime) {
        this.formatedTestflagmodifytime = formatedTestflagmodifytime;
    }
}
