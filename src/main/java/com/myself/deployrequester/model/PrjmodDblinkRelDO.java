package com.myself.deployrequester.model;

/**
 * Created by QueRenJie on ${date}
 */
public class PrjmodDblinkRelDO {
    private String prjmoddblinkrelid;

    private Short projectid;

    private Short moduleid;

    private String deploydbserversid;

    public String getPrjmoddblinkrelid() {
        return prjmoddblinkrelid;
    }

    public void setPrjmoddblinkrelid(String prjmoddblinkrelid) {
        this.prjmoddblinkrelid = prjmoddblinkrelid;
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
        this.deploydbserversid = deploydbserversid;
    }
}
