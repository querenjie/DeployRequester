package com.myself.deployrequester.po;

public class PrjmodDblinkRelPO {
    private String prjmoddblinkrelid;

    private Short projectid;

    private Short moduleid;

    private String deploydbserversid;

    private String dbname;

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

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname == null ? null : dbname.trim();
    }
}