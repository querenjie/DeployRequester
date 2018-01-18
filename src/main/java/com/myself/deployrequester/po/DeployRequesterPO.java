package com.myself.deployrequester.po;

import java.util.Date;
import java.util.UUID;

public class DeployRequesterPO {
    private String deployrequestid = UUID.randomUUID().toString();

    private Short projectcode;

    private Short modulecode;

    private Short moduletypecode;

    private Short modifytype;

    private String description;

    private String developer;

    private Short isautodeploytotestenv;

    private Short deploystatusfortestenv = 0;   //0：未发布（默认值）；1：正在发布；2：发布成功；-1：发布失败。

    private Short istestok = 0;     //0：未测（默认值）；1：测试环境测试通过；-1：测试环境测试不通过。这个是在页面上要有人打钩表示通过。

    private Short deploystatusforprodenv = 0;       //0：未发布（默认值）；1：正在发布；2：发布成功；-1：发布失败。能上生产的判断条件是比如这条记录是goods rest中的某条记录，首先取出所有的goods rest中未上过生产或上生产失败的记录，然后每条记录必须都是测试通过的，那么这个goods rest就可以上生产了。另外一种方式就是要强制上生产的话也就意味着把bug带上生产了。

    private Date createtime = new Date();

    private String menuname;        //菜单名

    private String testflagmodifier;        //修改测试环境是否通过的标志的人员姓名

    private String testflagmodifierip;      //修改测试环境是否通过的标志的人员的IP地址

    private Date testflagmodifytime;      //修改测试环境是否通过的标志的时间。用系统当前时间。

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

    public String getTestflagmodifier() {
        return testflagmodifier;
    }

    public void setTestflagmodifier(String testflagmodifier) {
        this.testflagmodifier = testflagmodifier;
    }

    public String getTestflagmodifierip() {
        return testflagmodifierip;
    }

    public void setTestflagmodifierip(String testflagmodifierip) {
        this.testflagmodifierip = testflagmodifierip;
    }

    public Date getTestflagmodifytime() {
        return testflagmodifytime;
    }

    public void setTestflagmodifytime(Date testflagmodifytime) {
        this.testflagmodifytime = testflagmodifytime;
    }
}