package com.myself.deployrequester.bo;

import com.myself.deployrequester.model.DeployDbscriptDO;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployDbscript extends DeployDbscriptDO {
    private String projectName;         //项目名称
    private String moduleName;          //模块名称
    private String dblinkDesc;          //数据库连接的描述
    private String formatedCreateTime;  //格式化后的记录创建时间
    private String formatedExecutetime; //格式化后的脚本执行时间
    private String executeStatusDesc;   //脚本执行的状态描述

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDblinkDesc() {
        return dblinkDesc;
    }

    public void setDblinkDesc(String dblinkDesc) {
        this.dblinkDesc = dblinkDesc;
    }

    public String getFormatedCreateTime() {
        return formatedCreateTime;
    }

    public void setFormatedCreateTime(String formatedCreateTime) {
        this.formatedCreateTime = formatedCreateTime;
    }

    public String getFormatedExecutetime() {
        return formatedExecutetime;
    }

    public void setFormatedExecutetime(String formatedExecutetime) {
        this.formatedExecutetime = formatedExecutetime;
    }

    public String getExecuteStatusDesc() {
        return executeStatusDesc;
    }

    public void setExecuteStatusDesc(String executeStatusDesc) {
        this.executeStatusDesc = executeStatusDesc;
    }
}
