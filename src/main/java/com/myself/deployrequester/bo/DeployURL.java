package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployURL {
    //项目标识
    private String projectId;
    //模块标识
    private String moduleCode;
    //模块名称
    private String moduleName;
    //类型：rest或provider
    private String type;
    //测试环境的发布url
    private String deployUrlForTestEnv;
    //生产环境的发布url
    private String deployUrlForProductEnv;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeployUrlForTestEnv() {
        return deployUrlForTestEnv;
    }

    public void setDeployUrlForTestEnv(String deployUrlForTestEnv) {
        this.deployUrlForTestEnv = deployUrlForTestEnv;
    }

    public String getDeployUrlForProductEnv() {
        return deployUrlForProductEnv;
    }

    public void setDeployUrlForProductEnv(String deployUrlForProductEnv) {
        this.deployUrlForProductEnv = deployUrlForProductEnv;
    }
}
