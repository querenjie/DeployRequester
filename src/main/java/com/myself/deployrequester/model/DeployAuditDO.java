package com.myself.deployrequester.model;

import com.myself.deployrequester.biz.config.sharedata.DeploySwitchEnum;

import java.util.Date;
import java.util.UUID;

public class DeployAuditDO {
    private String deployauditid;

    private String deployrequestid;

    private Short deployswitchstatus;

    private String auditor;

    private String auditorip;

    private Date switchontime;

    public String getDeployauditid() {
        return deployauditid;
    }

    public void setDeployauditid(String deployauditid) {
        this.deployauditid = deployauditid == null ? null : deployauditid.trim();
    }

    public String getDeployrequestid() {
        return deployrequestid;
    }

    public void setDeployrequestid(String deployrequestid) {
        this.deployrequestid = deployrequestid == null ? null : deployrequestid.trim();
    }

    public Short getDeployswitchstatus() {
        return deployswitchstatus;
    }

    public void setDeployswitchstatus(Short deployswitchstatus) {
        this.deployswitchstatus = deployswitchstatus;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getAuditorip() {
        return auditorip;
    }

    public void setAuditorip(String auditorip) {
        this.auditorip = auditorip == null ? null : auditorip.trim();
    }

    public Date getSwitchontime() {
        return switchontime;
    }

    public void setSwitchontime(Date switchontime) {
        this.switchontime = switchontime;
    }
}