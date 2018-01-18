package com.myself.deployrequester.model;

/**
 * Created by QueRenJie on ${date}
 */
public class QueryCriteriaStatistics1DO {
    private Short projectcode;                  //项目编号
    private Short modulecode;                   //模块编号
    private Short moduletypecode;               //模块类型编号
    private String begindate;                   //发布申请的开始日期
    private String enddate;                     //发布申请的结束日期

    /**
     * 根据标记就能知道要对哪些字段进行分组查询
     * 1:projectcode
     * 2:projectcode, modulecode
     * 3:projectcode, modulecode, moduletypecode
     */
    private String groupColumnsFlag;            //分组字段标记。

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

    public String getGroupColumnsFlag() {
        return groupColumnsFlag;
    }

    public void setGroupColumnsFlag(String groupColumnsFlag) {
        this.groupColumnsFlag = groupColumnsFlag;
    }
}
