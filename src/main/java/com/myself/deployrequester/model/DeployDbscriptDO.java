package com.myself.deployrequester.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployDbscriptDO {
    private String deploydbscriptid;
    private String deploydbserversid;
    private Short projectid;
    private Short moduleid;
    private String dbscript;
    private String description;
    private String applier;
    private String applierip;
    private Date createtime;
    private String executor;
    private String executorip;
    private Date executetime;
    private Short executestatus;
    private String failuremsg;
    private Short belong;
    private Short isabandoned;
    private String sqlmd5;
    private String executorforsync;
    private String executoripforsync;
    private Date executetimeforsync;
    private Short executestatusforsync;
    private String failuremsgforsync;
    private Short isabandonedforsync;

    public String getDeploydbscriptid() {
        return deploydbscriptid;
    }

    public void setDeploydbscriptid(String deploydbscriptid) {
        this.deploydbscriptid = deploydbscriptid;
    }

    public String getDeploydbserversid() {
        return deploydbserversid;
    }

    public void setDeploydbserversid(String deploydbserversid) {
        this.deploydbserversid = deploydbserversid;
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
        this.dbscript = dbscript;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier;
    }

    public String getApplierip() {
        return applierip;
    }

    public void setApplierip(String applierip) {
        this.applierip = applierip;
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
        this.executor = executor;
    }

    public String getExecutorip() {
        return executorip;
    }

    public void setExecutorip(String executorip) {
        this.executorip = executorip;
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
        this.failuremsg = failuremsg;
    }

    public Short getBelong() {
        return belong;
    }

    public void setBelong(Short belong) {
        this.belong = belong;
    }

    public Short getIsabandoned() {
        return isabandoned;
    }

    public void setIsabandoned(Short isabandoned) {
        this.isabandoned = isabandoned;
    }

    public String getSqlmd5() {
        return sqlmd5;
    }

    public void setSqlmd5(String sqlmd5) {
        this.sqlmd5 = sqlmd5;
    }

    public String getExecutorforsync() {
        return executorforsync;
    }

    public void setExecutorforsync(String executorforsync) {
        this.executorforsync = executorforsync;
    }

    public String getExecutoripforsync() {
        return executoripforsync;
    }

    public void setExecutoripforsync(String executoripforsync) {
        this.executoripforsync = executoripforsync;
    }

    public Date getExecutetimeforsync() {
        return executetimeforsync;
    }

    public void setExecutetimeforsync(Date executetimeforsync) {
        this.executetimeforsync = executetimeforsync;
    }

    public Short getExecutestatusforsync() {
        return executestatusforsync;
    }

    public void setExecutestatusforsync(Short executestatusforsync) {
        this.executestatusforsync = executestatusforsync;
    }

    public String getFailuremsgforsync() {
        return failuremsgforsync;
    }

    public void setFailuremsgforsync(String failuremsgforsync) {
        this.failuremsgforsync = failuremsgforsync;
    }

    public Short getIsabandonedforsync() {
        return isabandonedforsync;
    }

    public void setIsabandonedforsync(Short isabandonedforsync) {
        this.isabandonedforsync = isabandonedforsync;
    }
}
