package com.myself.deployrequester.dto;

/**
 * Created by QueRenJie on ${date}
 */
public class QueryCriteriaDTO {
    private Short projectcode;                 //项目编号
    private Short modulecode;                  //模块编号
    private Short moduletypecode;              //模块类型编号
    private String begindate;                   //发布申请的开始日期
    private String enddate;                     //发布申请的结束日期
    private Short istestok;                    //测试环境上测试是否通过(0：未测；1：测试环境测试通过；-1：测试环境测试不通过。)
    private Short isdeployedtoproduct;         //生产环境发布状态(0：未发布；1：已发布。)
    private String developer;                   //开发人员
    private String deployrequestid;             //发布申请记录的主键

    private String deployToProdEnvTime;         //发布生产的时间
    private String testflagmodifier;        //修改测试环境是否通过的标志的人员姓名
    private String testflagmodifierip;      //修改测试环境是否通过的标志的人员的IP地址

    private Short deploystatusforprodenv;      //2：发布生产环境；3：发布预生产环境


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

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Short getIstestok() {
        return istestok;
    }

    public void setIstestok(Short istestok) {
        this.istestok = istestok;
    }

    public Short getIsdeployedtoproduct() {
        return isdeployedtoproduct;
    }

    public void setIsdeployedtoproduct(Short isdeployedtoproduct) {
        this.isdeployedtoproduct = isdeployedtoproduct;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDeployrequestid() {
        return deployrequestid;
    }

    public void setDeployrequestid(String deployrequestid) {
        this.deployrequestid = deployrequestid;
    }

    public String getDeployToProdEnvTime() {
        return deployToProdEnvTime;
    }

    public void setDeployToProdEnvTime(String deployToProdEnvTime) {
        this.deployToProdEnvTime = deployToProdEnvTime;
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

    public Short getDeploystatusforprodenv() {
        return deploystatusforprodenv;
    }

    public void setDeploystatusforprodenv(Short deploystatusforprodenv) {
        this.deploystatusforprodenv = deploystatusforprodenv;
    }

    @Override
    public String toString() {
        return "QueryCriteriaDTO{" +
                "projectcode=" + projectcode +
                ", modulecode=" + modulecode +
                ", moduletypecode=" + moduletypecode +
                ", begindate='" + begindate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", istestok=" + istestok +
                ", isdeployedtoproduct=" + isdeployedtoproduct +
                ", developer='" + developer + '\'' +
                ", deployrequestid='" + deployrequestid + '\'' +
                ", deployToProdEnvTime='" + deployToProdEnvTime + '\'' +
                ", testflagmodifier='" + testflagmodifier + '\'' +
                ", testflagmodifierip='" + testflagmodifierip + '\'' +
                ", deploystatusforprodenv='" + deploystatusforprodenv + '\'' +
                '}';
    }
}
