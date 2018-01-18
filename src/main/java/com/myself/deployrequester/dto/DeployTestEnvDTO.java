package com.myself.deployrequester.dto;

import java.util.Date;

public class DeployTestEnvDTO {
    private String deploytestenvid;

    private String deployrequestid;

    private Date deploybegintime;

    private Date deployendtime;

    private long duration;

    private String feedbackinfo;

    private Short issuccess;

    public String getDeploytestenvid() {
        return deploytestenvid;
    }

    public void setDeploytestenvid(String deploytestenvid) {
        this.deploytestenvid = deploytestenvid == null ? null : deploytestenvid.trim();
    }

    public String getDeployrequestid() {
        return deployrequestid;
    }

    public void setDeployrequestid(String deployrequestid) {
        this.deployrequestid = deployrequestid == null ? null : deployrequestid.trim();
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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