package com.myself.deployrequester.po;

import java.util.Date;
import java.util.UUID;

public class DeployDbscriptDetailsqlPO {
    private String deploydbscriptdetailsqlid = UUID.randomUUID().toString();

    private String deploydbscriptid;

    private Short subsqlseqno;

    private String subsql;

    private Short executestatus;

    private Date executetime;

    private String executor;

    private String executorip;

    private Short willignore;

    private Date createtime = new Date();

    private String creater;

    private String createrip;

    public String getDeploydbscriptdetailsqlid() {
        return deploydbscriptdetailsqlid;
    }

    public void setDeploydbscriptdetailsqlid(String deploydbscriptdetailsqlid) {
        this.deploydbscriptdetailsqlid = deploydbscriptdetailsqlid == null ? null : deploydbscriptdetailsqlid.trim();
    }

    public String getDeploydbscriptid() {
        return deploydbscriptid;
    }

    public void setDeploydbscriptid(String deploydbscriptid) {
        this.deploydbscriptid = deploydbscriptid == null ? null : deploydbscriptid.trim();
    }

    public Short getSubsqlseqno() {
        return subsqlseqno;
    }

    public void setSubsqlseqno(Short subsqlseqno) {
        this.subsqlseqno = subsqlseqno;
    }

    public String getSubsql() {
        return subsql;
    }

    public void setSubsql(String subsql) {
        this.subsql = subsql == null ? null : subsql.trim();
    }

    public Short getExecutestatus() {
        return executestatus;
    }

    public void setExecutestatus(Short executestatus) {
        this.executestatus = executestatus;
    }

    public Date getExecutetime() {
        return executetime;
    }

    public void setExecutetime(Date executetime) {
        this.executetime = executetime;
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

    public Short getWillignore() {
        return willignore;
    }

    public void setWillignore(Short willignore) {
        this.willignore = willignore;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getCreaterip() {
        return createrip;
    }

    public void setCreaterip(String createrip) {
        this.createrip = createrip == null ? null : createrip.trim();
    }
}
