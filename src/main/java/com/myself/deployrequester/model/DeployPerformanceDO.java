package com.myself.deployrequester.model;

import java.util.UUID;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployPerformanceDO {
    private String deployperformanceid;

    private Short modulecode;

    private Short moduletypecode;

    private long deployduration;

    private Short projectcode;

    public String getDeployperformanceid() {
        return deployperformanceid;
    }

    public void setDeployperformanceid(String deployperformanceid) {
        this.deployperformanceid = deployperformanceid == null ? null : deployperformanceid.trim();
    }

    public Short getModulecode() {
        return modulecode;
    }

    public void setModulecode(Short modulecode) {
        this.modulecode = modulecode;
    }

    public Short getModuletypecode() {
        return moduletypecode;
    }

    public void setModuletypecode(Short moduletypecode) {
        this.moduletypecode = moduletypecode;
    }

    public long getDeployduration() {
        return deployduration;
    }

    public void setDeployduration(long deployduration) {
        this.deployduration = deployduration;
    }

    public Short getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(Short projectcode) {
        this.projectcode = projectcode;
    }
}
