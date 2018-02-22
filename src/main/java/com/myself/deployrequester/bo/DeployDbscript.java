package com.myself.deployrequester.bo;

import com.myself.deployrequester.model.DeployDbscriptDO;

import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployDbscript extends DeployDbscriptDO {
    private String projectName;         //项目名称
    private String moduleName;          //模块名称
    private String dblinkDesc;          //数据库连接的描述
    private String formatedCreateTime;  //格式化后的记录创建时间
    private String formatedExecuteTime; //格式化后的脚本执行时间
    private String executeStatusDesc;   //脚本执行的状态描述
    private String belongDesc;          //目标数据库服务器环境的描述
    private String visitorIp;           //访问者的Ip地址
    private List<String> executedSqlList;   //已经执行过的sql
    private List<String> unexecutedSqlList; //尚未执行过的sql
    private String isabandonedDesc;         //是否已经放弃执行尚未执行的sql
    private String hasSyncSql = "no";               //是否有同步的脚本sql。yes或者no
    private String formatedExceuteTimeForSync;      //同步脚本的格式化后的脚本执行时间
    private String executeStatusDescForSync;        //同步脚本的执行的状态描述
    private List<String> executedSqlListForSync;    //同步脚本的已经执行过的sql
    private List<String> unexecutedSqlListForSync;  //同步脚本的尚未执行过的sql
    private String isabandonedDescForSync;          //同步脚本的是否已经放弃执行尚未执行的sql
    private String dblinkDescForSync;               //同步库连接的描述

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDblinkDesc() {
        return dblinkDesc;
    }

    public void setDblinkDesc(String dblinkDesc) {
        this.dblinkDesc = dblinkDesc;
    }

    public String getFormatedCreateTime() {
        return formatedCreateTime;
    }

    public void setFormatedCreateTime(String formatedCreateTime) {
        this.formatedCreateTime = formatedCreateTime;
    }

    public String getFormatedExecuteTime() {
        return formatedExecuteTime;
    }

    public void setFormatedExecuteTime(String formatedExecuteTime) {
        this.formatedExecuteTime = formatedExecuteTime;
    }

    public String getExecuteStatusDesc() {
        return executeStatusDesc;
    }

    public void setExecuteStatusDesc(String executeStatusDesc) {
        this.executeStatusDesc = executeStatusDesc;
    }

    public String getBelongDesc() {
        return belongDesc;
    }

    public void setBelongDesc(String belongDesc) {
        this.belongDesc = belongDesc;
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public List<String> getExecutedSqlList() {
        return executedSqlList;
    }

    public void setExecutedSqlList(List<String> executedSqlList) {
        this.executedSqlList = executedSqlList;
    }

    public List<String> getUnexecutedSqlList() {
        return unexecutedSqlList;
    }

    public void setUnexecutedSqlList(List<String> unexecutedSqlList) {
        this.unexecutedSqlList = unexecutedSqlList;
    }

    public String getIsabandonedDesc() {
        return isabandonedDesc;
    }

    public void setIsabandonedDesc(String isabandonedDesc) {
        this.isabandonedDesc = isabandonedDesc;
    }

    public String getHasSyncSql() {
        return hasSyncSql;
    }

    public void setHasSyncSql(String hasSyncSql) {
        this.hasSyncSql = hasSyncSql;
    }

    public String getFormatedExceuteTimeForSync() {
        return formatedExceuteTimeForSync;
    }

    public void setFormatedExceuteTimeForSync(String formatedExceuteTimeForSync) {
        this.formatedExceuteTimeForSync = formatedExceuteTimeForSync;
    }

    public String getExecuteStatusDescForSync() {
        return executeStatusDescForSync;
    }

    public void setExecuteStatusDescForSync(String executeStatusDescForSync) {
        this.executeStatusDescForSync = executeStatusDescForSync;
    }

    public List<String> getExecutedSqlListForSync() {
        return executedSqlListForSync;
    }

    public void setExecutedSqlListForSync(List<String> executedSqlListForSync) {
        this.executedSqlListForSync = executedSqlListForSync;
    }

    public List<String> getUnexecutedSqlListForSync() {
        return unexecutedSqlListForSync;
    }

    public void setUnexecutedSqlListForSync(List<String> unexecutedSqlListForSync) {
        this.unexecutedSqlListForSync = unexecutedSqlListForSync;
    }

    public String getIsabandonedDescForSync() {
        return isabandonedDescForSync;
    }

    public void setIsabandonedDescForSync(String isabandonedDescForSync) {
        this.isabandonedDescForSync = isabandonedDescForSync;
    }

    public String getDblinkDescForSync() {
        return dblinkDescForSync;
    }

    public void setDblinkDescForSync(String dblinkDescForSync) {
        this.dblinkDescForSync = dblinkDescForSync;
    }

}
