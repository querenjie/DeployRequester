package com.myself.deployrequester.dto;

public class DeployProdEnvDetailDTO {
    private String deployprodenvdetailid;

    private String deployprodenvid;

    private String deployrequestid;

    public String getDeployprodenvdetailid() {
        return deployprodenvdetailid;
    }

    public void setDeployprodenvdetailid(String deployprodenvdetailid) {
        this.deployprodenvdetailid = deployprodenvdetailid == null ? null : deployprodenvdetailid.trim();
    }

    public String getDeployprodenvid() {
        return deployprodenvid;
    }

    public void setDeployprodenvid(String deployprodenvid) {
        this.deployprodenvid = deployprodenvid == null ? null : deployprodenvid.trim();
    }

    public String getDeployrequestid() {
        return deployrequestid;
    }

    public void setDeployrequestid(String deployrequestid) {
        this.deployrequestid = deployrequestid == null ? null : deployrequestid.trim();
    }
}