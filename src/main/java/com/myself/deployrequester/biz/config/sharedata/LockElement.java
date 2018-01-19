package com.myself.deployrequester.biz.config.sharedata;

/**
 * 用于判定哪些模块被锁定，锁定后不能发布测试环境。
 * Created by QueRenJie on ${date}
 */
public class LockElement {
    private Short projectId;
    private Short moduleId;
    private Short moduleTypeId;
    private boolean isLocked;       //是否已经被锁定

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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
