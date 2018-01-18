package com.myself.deployrequester.model;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployRequesterStatistics1DO {
    private Integer rankno;                 //名次
    private Short projectcode;
    private Short modulecode;
    private Short moduletypecode;
    private String developer;
    private Integer nottested;
    private Integer testsuccess;
    private Integer testfailed;
    private Integer donotneedtest;
    private Integer testsuccessreally;
    private Double passrate;

    public Integer getRankno() {
        return rankno;
    }

    public void setRankno(Integer rankno) {
        this.rankno = rankno;
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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getNottested() {
        return nottested;
    }

    public void setNottested(Integer nottested) {
        this.nottested = nottested;
    }

    public Integer getTestsuccess() {
        return testsuccess;
    }

    public void setTestsuccess(Integer testsuccess) {
        this.testsuccess = testsuccess;
    }

    public Integer getTestfailed() {
        return testfailed;
    }

    public void setTestfailed(Integer testfailed) {
        this.testfailed = testfailed;
    }

    public Integer getDonotneedtest() {
        return donotneedtest;
    }

    public void setDonotneedtest(Integer donotneedtest) {
        this.donotneedtest = donotneedtest;
    }

    public Integer getTestsuccessreally() {
        return testsuccessreally;
    }

    public void setTestsuccessreally(Integer testsuccessreally) {
        this.testsuccessreally = testsuccessreally;
    }

    public Double getPassrate() {
        return passrate;
    }

    public void setPassrate(Double passrate) {
        this.passrate = passrate;
    }
}
