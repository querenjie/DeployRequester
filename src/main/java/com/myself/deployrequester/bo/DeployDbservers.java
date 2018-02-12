package com.myself.deployrequester.bo;

import com.myself.deployrequester.model.DeployDbserversDO;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployDbservers extends DeployDbserversDO {
    private String belongName = "";
    private String projectName = "";
    private String moduleName = "";          //模块名称

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

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
}
