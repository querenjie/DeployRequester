package com.myself.deployrequester.dto;

/**
 * Created by QueRenJie on ${date}
 */
public class ModuleDTO {
    private Short projectId;
    private Short moduleId;
    private Short moduleTypeId;

    public Short getProjectId() {
        return projectId;
    }

    public void setProjectId(Short projectId) {
        this.projectId = projectId;
    }

    public Short getModuleId() {
        return moduleId;
    }

    public void setModuleId(Short moduleId) {
        this.moduleId = moduleId;
    }

    public Short getModuleTypeId() {
        return moduleTypeId;
    }

    public void setModuleTypeId(Short moduleTypeId) {
        this.moduleTypeId = moduleTypeId;
    }
}
