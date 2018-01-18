package com.myself.deployrequester.dto;

import java.util.Date;

public class DeployRequesterDTO {
    private String deployrequestid;

    private Short projectcode;

    private Short modulecode;

    private Short moduletypecode;

    private Short modifytype;

    private String description;

    private String developer;

    private Short isautodeploytotestenv;

    private Short deploystatusfortestenv;

    private Short istestok;

    private Short deploystatusforprodenv;

    private Date createtime;

    private String menuname;

    public String getDeployrequestid() {
        return deployrequestid;
    }

    public void setDeployrequestid(String deployrequestid) {
        this.deployrequestid = deployrequestid == null ? null : deployrequestid.trim();
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

    public Short getModifytype() {
        return modifytype;
    }

    public void setModifytype(Short modifytype) {
        this.modifytype = modifytype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer == null ? null : developer.trim();
    }

    public Short getIsautodeploytotestenv() {
        return isautodeploytotestenv;
    }

    public void setIsautodeploytotestenv(Short isautodeploytotestenv) {
        this.isautodeploytotestenv = isautodeploytotestenv;
    }

    public Short getDeploystatusfortestenv() {
        return deploystatusfortestenv;
    }

    public void setDeploystatusfortestenv(Short deploystatusfortestenv) {
        this.deploystatusfortestenv = deploystatusfortestenv;
    }

    public Short getIstestok() {
        return istestok;
    }

    public void setIstestok(Short istestok) {
        this.istestok = istestok;
    }

    public Short getDeploystatusforprodenv() {
        return deploystatusforprodenv;
    }

    public void setDeploystatusforprodenv(Short deploystatusforprodenv) {
        this.deploystatusforprodenv = deploystatusforprodenv;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

}