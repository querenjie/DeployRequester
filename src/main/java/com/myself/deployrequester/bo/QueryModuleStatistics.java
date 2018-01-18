package com.myself.deployrequester.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * 某个模块中的内容
 * Created by QueRenJie on ${date}
 */
public class QueryModuleStatistics {
    private Short moduleId;
    private String moduleCode;
    private String moduleName;
    /**
     * 模块中的记录总数
     */
    private int moduleCount;
    /**
     * 模块中的rest的总数
     */
    private int moduleRestCount;
    /**
     * 模块中的provider的总数
     */
    private int moduleProviderCount;
    /**
     * 本模块中的记录
     */
    private List<DeployRequest> deployRequestList = new ArrayList<DeployRequest>();

    public Short getModuleId() {
        return moduleId;
    }

    public void setModuleId(Short moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getModuleCount() {
        return moduleCount;
    }

    public void setModuleCount(int moduleCount) {
        this.moduleCount = moduleCount;
    }

    public int getModuleRestCount() {
        return moduleRestCount;
    }

    public void setModuleRestCount(int moduleRestCount) {
        this.moduleRestCount = moduleRestCount;
    }

    public int getModuleProviderCount() {
        return moduleProviderCount;
    }

    public void setModuleProviderCount(int moduleProviderCount) {
        this.moduleProviderCount = moduleProviderCount;
    }

    public List<DeployRequest> getDeployRequestList() {
        return deployRequestList;
    }

    public void setDeployRequestList(List<DeployRequest> deployRequestList) {
        this.deployRequestList = deployRequestList;
    }
}
