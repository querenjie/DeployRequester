package com.myself.deployrequester.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
public class DBScriptInfoForFileGenerate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String deploydbscriptid;            //脚本申请记录的主键
    private String applier;                     //申请者姓名
    private String description;                 //描述
    private String dbname;                      //数据库名称
    private List<String> subsqlList;            //尚未执行的脚本语句
    private String deploydbserversid;           //执行脚本时采用的链接配置标识
    private String projectCode;                 //项目编号。1：翌能；2：宁家；3：大宗；4：翌捷

    public String getDeploydbscriptid() {
        return deploydbscriptid;
    }

    public void setDeploydbscriptid(String deploydbscriptid) {
        this.deploydbscriptid = deploydbscriptid;
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public List<String> getSubsqlList() {
        return subsqlList;
    }

    public void setSubsqlList(List<String> subsqlList) {
        this.subsqlList = subsqlList;
    }

    public String getDeploydbserversid() {
        return deploydbserversid;
    }

    public void setDeploydbserversid(String deploydbserversid) {
        this.deploydbserversid = deploydbserversid;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @Override
    public String toString() {
        return "DBScriptInfoForFileGenerate{" +
                "deploydbscriptid='" + deploydbscriptid + '\'' +
                ", applier='" + applier + '\'' +
                ", description='" + description + '\'' +
                ", dbname='" + dbname + '\'' +
                ", subsqlList=" + subsqlList +
                ", deploydbserversid='" + deploydbserversid + '\'' +
                ", projectCode='" + projectCode + '\'' +
                '}';
    }
}
