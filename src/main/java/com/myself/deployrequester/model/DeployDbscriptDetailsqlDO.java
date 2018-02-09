package com.myself.deployrequester.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployDbscriptDetailsqlDO {
    private String deploydbscriptdetailsqlid;

    private String deploydbscriptid;

    private Short subsqlseqno;

    private String subsql;

    private Short executestatus;

    private Date executetime;

    private String executor;

    private String executorip;

    private Short willignore;

    private Date createtime;

    private String creater;

    private String createrip;

    public String getDeploydbscriptdetailsqlid() {
        return deploydbscriptdetailsqlid;
    }

    public void setDeploydbscriptdetailsqlid(String deploydbscriptdetailsqlid) {
        this.deploydbscriptdetailsqlid = deploydbscriptdetailsqlid;
    }

    public String getDeploydbscriptid() {
        return deploydbscriptid;
    }

    public void setDeploydbscriptid(String deploydbscriptid) {
        this.deploydbscriptid = deploydbscriptid;
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
        this.subsql = subsql;
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
        this.executor = executor;
    }

    public String getExecutorip() {
        return executorip;
    }

    public void setExecutorip(String executorip) {
        this.executorip = executorip;
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
        this.creater = creater;
    }

    public String getCreaterip() {
        return createrip;
    }

    public void setCreaterip(String createrip) {
        this.createrip = createrip;
    }
}
