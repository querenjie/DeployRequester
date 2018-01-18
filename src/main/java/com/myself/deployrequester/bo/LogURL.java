package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class LogURL {
    //项目id
    private String projectId;
    //模块标识
    private String moduleCode;
    //模块名称
    private String moduleName;
    //类型：rest或provider
    private String type;
    //测试/生产环境的日志url
    private String logUrl;
    //测试/生产环境的启动日志url
    private String startLogUrl;
    //测试/生产环境的启动状态日志url
    private String startStatusLogUrl;

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

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getStartLogUrl() {
        return startLogUrl;
    }

    public void setStartLogUrl(String startLogUrl) {
        this.startLogUrl = startLogUrl;
    }

    public String getStartStatusLogUrl() {
        return startStatusLogUrl;
    }

    public void setStartStatusLogUrl(String startStatusLogUrl) {
        this.startStatusLogUrl = startStatusLogUrl;
    }

}
