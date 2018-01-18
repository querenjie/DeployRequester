package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class UrlSummary {
    private String testDeployUrl;       //测试环境的发布url
    private String productDeployUrl;    //生产环境的发布url
    private String tlogs;               //测试环境日志url
    private String tslogs;              //测试环境启动日志url
    private String tstatus;             //测试环境启动状态url
    private String plogs;               //生产环境日志url
    private String pslogs;              //生产环境启动日志url
    private String pstatus;             //生产环境启动状态url
    private String prelogs;             //预生产环境日志url
    private String preslogs;            //预生产环境启动日志url
    private String prestatus;           //预生产环境启动状态url

    public String getTestDeployUrl() {
        return testDeployUrl;
    }

    public void setTestDeployUrl(String testDeployUrl) {
        this.testDeployUrl = testDeployUrl;
    }

    public String getProductDeployUrl() {
        return productDeployUrl;
    }

    public void setProductDeployUrl(String productDeployUrl) {
        this.productDeployUrl = productDeployUrl;
    }

    public String getTlogs() {
        return tlogs;
    }

    public void setTlogs(String tlogs) {
        this.tlogs = tlogs;
    }

    public String getTslogs() {
        return tslogs;
    }

    public void setTslogs(String tslogs) {
        this.tslogs = tslogs;
    }

    public String getTstatus() {
        return tstatus;
    }

    public void setTstatus(String tstatus) {
        this.tstatus = tstatus;
    }

    public String getPlogs() {
        return plogs;
    }

    public void setPlogs(String plogs) {
        this.plogs = plogs;
    }

    public String getPslogs() {
        return pslogs;
    }

    public void setPslogs(String pslogs) {
        this.pslogs = pslogs;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getPrelogs() {
        return prelogs;
    }

    public void setPrelogs(String prelogs) {
        this.prelogs = prelogs;
    }

    public String getPreslogs() {
        return preslogs;
    }

    public void setPreslogs(String preslogs) {
        this.preslogs = preslogs;
    }

    public String getPrestatus() {
        return prestatus;
    }

    public void setPrestatus(String prestatus) {
        this.prestatus = prestatus;
    }
}
