package com.myself.deployrequester.po;

/**
 * 发布申请的查询条件
 * Created by QueRenJie on ${date}
 */
public class ProduceApplicationQueryCriteriaPO {
    private Short projectcode;                  //项目编号
    private Short modulecode;                   //模块编号
    private Short moduletypecode;               //模块类型编号
    private String deployToWhere;               //发布到什么环境上。product:生产环境; preproduct:预发布环境

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

    public String getDeployToWhere() {
        return deployToWhere;
    }

    public void setDeployToWhere(String deployToWhere) {
        this.deployToWhere = deployToWhere;
    }
}
