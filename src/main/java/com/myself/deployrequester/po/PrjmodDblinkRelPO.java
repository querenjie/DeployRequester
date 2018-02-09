package com.myself.deployrequester.po;

import java.util.UUID;

public class PrjmodDblinkRelPO {
    private String prjmoddblinkrelid = UUID.randomUUID().toString();

    private Short projectid;

    private Short moduleid;

    private String deploydbserversid;

    private Short belong;

    public String getPrjmoddblinkrelid() {
        return prjmoddblinkrelid;
    }

    public void setPrjmoddblinkrelid(String prjmoddblinkrelid) {
        this.prjmoddblinkrelid = prjmoddblinkrelid == null ? null : prjmoddblinkrelid.trim();
    }

    public Short getProjectid() {
        return projectid;
    }

    public void setProjectid(Short projectid) {
        this.projectid = projectid;
    }

    public Short getModuleid() {
        return moduleid;
    }

    public void setModuleid(Short moduleid) {
        this.moduleid = moduleid;
    }

    public String getDeploydbserversid() {
        return deploydbserversid;
    }

    public void setDeploydbserversid(String deploydbserversid) {
        this.deploydbserversid = deploydbserversid == null ? null : deploydbserversid.trim();
    }

    public Short getBelong() {
        return belong;
    }

    public void setBelong(Short belong) {
        this.belong = belong;
    }
}