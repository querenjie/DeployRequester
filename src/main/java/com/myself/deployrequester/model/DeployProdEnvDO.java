package com.myself.deployrequester.model;

import java.util.Date;

public class DeployProdEnvDO {
    private String deployprodenvid;

    private Short projectcode;

    private Short modulecode;

    private Short moduletypecode;

    private Date deploybegintime;

    private Date deployendtime;

    private String feedbackinfo;

    private Short issuccess;

    public String getDeployprodenvid() {
        return deployprodenvid;
    }

    public void setDeployprodenvid(String deployprodenvid) {
        this.deployprodenvid = deployprodenvid == null ? null : deployprodenvid.trim();
    }

    public Short getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(Short projectcode) {
        this.projectcode = projectcode;
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

    public Date getDeploybegintime() {
        return deploybegintime;
    }

    public void setDeploybegintime(Date deploybegintime) {
        this.deploybegintime = deploybegintime;
    }

    public Date getDeployendtime() {
        return deployendtime;
    }

    public void setDeployendtime(Date deployendtime) {
        this.deployendtime = deployendtime;
    }

    public String getFeedbackinfo() {
        return feedbackinfo;
    }

    public void setFeedbackinfo(String feedbackinfo) {
        this.feedbackinfo = feedbackinfo == null ? null : feedbackinfo.trim();
    }

    public Short getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(Short issuccess) {
        this.issuccess = issuccess;
    }
}