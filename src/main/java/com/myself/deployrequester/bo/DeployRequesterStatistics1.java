package com.myself.deployrequester.bo;

import com.myself.deployrequester.model.DeployRequesterStatistics1DO;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployRequesterStatistics1 extends DeployRequesterStatistics1DO {
    private String projectName;
    private String moduleCodeName;
    private String moduleTypeName;

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

    public String getModuleTypeName() {
        return moduleTypeName;
    }

    public void setModuleTypeName(String moduleTypeName) {
        this.moduleTypeName = moduleTypeName;
    }
}
