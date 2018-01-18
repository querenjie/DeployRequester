package com.myself.deployrequester.po;

import java.util.Date;
import java.util.UUID;

public class DeployDbscriptPO {
    private String deploydbscriptid = UUID.randomUUID().toString();

    private String deploydbserversid;

    private Short projectid;

    private Short moduleid;

    private String dbscript;

    private String description;

    private String applier;

    private String applierip;

    private Date createtime = new Date();

    private String executor;

    private String executorip;

    private Date executetime;

    private Short executestatus;

    private String failuremsg;

    public String getDeploydbscriptid() {
        return deploydbscriptid;
    }

    public void setDeploydbscriptid(String deploydbscriptid) {
        this.deploydbscriptid = deploydbscriptid == null ? null : deploydbscriptid.trim();
    }

    public String getDeploydbserversid() {
        return deploydbserversid;
    }

    public void setDeploydbserversid(String deploydbserversid) {
        this.deploydbserversid = deploydbserversid == null ? null : deploydbserversid.trim();
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

    public String getDbscript() {
        return dbscript;
    }

    public void setDbscript(String dbscript) {
        this.dbscript = dbscript == null ? null : dbscript.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier == null ? null : applier.trim();
    }

    public String getApplierip() {
        return applierip;
    }

    public void setApplierip(String applierip) {
        this.applierip = applierip == null ? null : applierip.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor == null ? null : executor.trim();
    }

    public String getExecutorip() {
        return executorip;
    }

    public void setExecutorip(String executorip) {
        this.executorip = executorip == null ? null : executorip.trim();
    }

    public Date getExecutetime() {
        return executetime;
    }

    public void setExecutetime(Date executetime) {
        this.executetime = executetime;
    }

    public Short getExecutestatus() {
        return executestatus;
    }

    public void setExecutestatus(Short executestatus) {
        this.executestatus = executestatus;
    }

    public String getFailuremsg() {
        return failuremsg;
    }

    public void setFailuremsg(String failuremsg) {
        this.failuremsg = failuremsg == null ? null : failuremsg.trim();
    }
}