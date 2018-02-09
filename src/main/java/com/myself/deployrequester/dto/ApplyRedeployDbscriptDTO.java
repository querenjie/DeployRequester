package com.myself.deployrequester.dto;

/**
 * Created by QueRenJie on ${date}
 */
public class ApplyRedeployDbscriptDTO {
    private String deploydbscriptid;        //脚本申请的记录id
    private String unexecutedSql;           //未执行的sql

    private String forcetodoit;             //yes表示强制提交这个申请，尽管里面有危险的sql

    public String getDeploydbscriptid() {
        return deploydbscriptid;
    }

    public void setDeploydbscriptid(String deploydbscriptid) {
        this.deploydbscriptid = deploydbscriptid;
    }

    public String getUnexecutedSql() {
        return unexecutedSql;
    }

    public void setUnexecutedSql(String unexecutedSql) {
        this.unexecutedSql = unexecutedSql;
    }

    public String getForcetodoit() {
        return forcetodoit;
    }

    public void setForcetodoit(String forcetodoit) {
        this.forcetodoit = forcetodoit;
    }
}
