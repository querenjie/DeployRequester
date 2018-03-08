package com.myself.deployrequester.po;

/**
 * Created by QueRenJie on ${date}
 */
public class QueryDeployAuditPO {
    private Short projectCode;
    private Short moduleCode;
    private Short moduleTypeCode;
    private String developer;           //开发人员，脚本的申请者

    public Short getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Short projectCode) {
        this.projectCode = projectCode;
    }

    public Short getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(Short moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Short getModuleTypeCode() {
        return moduleTypeCode;
    }

    public void setModuleTypeCode(Short moduleTypeCode) {
        this.moduleTypeCode = moduleTypeCode;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
