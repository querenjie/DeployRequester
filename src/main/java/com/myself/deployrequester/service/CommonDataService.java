package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.spi.Config;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.controller.ConfigdataController;
import com.myself.deployrequester.util.ListSorter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class CommonDataService {
    public List<Module> getAllModules() {
        return ConfigData.MODULE_CONFIG;
    }

    public List<Project> getAllProjects() {
        return ConfigData.PROJECT_CONFIG;
    }

    public List<ModuleType> getAllModuleTypes() {
        return ConfigData.MODULE_TYPE_CONFIG;
    }

    public List<ModifyType> getAllModifyTypes() {
        return ConfigData.MODIFY_TYPE_CONFIG;
    }

    public List<Module> getModulesByProjectId(String projectId) {
        List<Module> moduleList = new ArrayList<Module>();
        if (ConfigData.MODULE_DEPLOY_URL_CONFIG != null) {
            Iterator<Map.Entry<String, DeployURL>> iterator = ConfigData.MODULE_DEPLOY_URL_CONFIG.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, DeployURL> entry = iterator.next();
                String key = entry.getKey();
                String[] arrKey = key.split("_");
                String projectIdInKey = arrKey[0];
                String moduleCodeInKey = arrKey[1];
                if (projectId.equals(projectIdInKey)) {
                    if (ConfigData.MODULE_CONFIG != null) {
                        for (Module module : ConfigData.MODULE_CONFIG) {
                            if (module.getCode().equals(moduleCodeInKey)) {
                                if (!isExistedInList(moduleList, module)) {
                                    moduleList.add(module);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        ListSorter<Module> listSorter = new ListSorter<Module>();
        listSorter.Sort(moduleList, "getId", ListSorter.ASCENDING);

        return moduleList;
    }

    /**
     * 根据模块编号和模块类型获取对应的发布url和日志url
     * @param moduleCode
     * @param moduleType
     * @return
     */
    public UrlSummary getUrlSummary(String projectId, String moduleCode, String moduleType) {
        UrlSummary urlSummary = new UrlSummary();
        if (ConfigData.MODULE_DEPLOY_URL_CONFIG != null) {
            DeployURL deployURL = ConfigData.MODULE_DEPLOY_URL_CONFIG.get(projectId + "_" + moduleCode + "_" + moduleType);
            if (deployURL != null) {
                urlSummary.setTestDeployUrl(deployURL.getDeployUrlForTestEnv());
                urlSummary.setProductDeployUrl(deployURL.getDeployUrlForProductEnv());
            }
        }
        if (ConfigData.TEST_LOG_URL_CONFIG != null) {
            LogURL logURL = ConfigData.TEST_LOG_URL_CONFIG.get(projectId + "_" + moduleCode + "_" + moduleType);
            if (logURL != null) {
                urlSummary.setTlogs(logURL.getLogUrl());
                urlSummary.setTslogs(logURL.getStartLogUrl());
                urlSummary.setTstatus(logURL.getStartStatusLogUrl());
            }
        }
        if (ConfigData.PRODUCT_LOG_URL_CONFIG != null) {
            LogURL logURL = ConfigData.PRODUCT_LOG_URL_CONFIG.get(projectId + "_" + moduleCode + "_" + moduleType);
            if (logURL != null) {
                urlSummary.setPlogs(logURL.getLogUrl());
                urlSummary.setPslogs(logURL.getStartLogUrl());
                urlSummary.setPstatus(logURL.getStartStatusLogUrl());
            }
        }
        if (ConfigData.PRE_LOG_URL_CONFIG != null) {
            LogURL logURL = ConfigData.PRE_LOG_URL_CONFIG.get(projectId + "_" + moduleCode + "_" + moduleType);
            if (logURL != null) {
                urlSummary.setPrelogs(logURL.getLogUrl());
                urlSummary.setPreslogs(logURL.getStartLogUrl());
                urlSummary.setPrestatus(logURL.getStartStatusLogUrl());
            }
        }
        return urlSummary;
    }

    public Project getProjectById(int projectId) {
        if (ConfigData.PROJECT_CONFIG != null) {
            for (Project project : ConfigData.PROJECT_CONFIG) {
                if (project.getId() == projectId) {
                    return project;
                }
            }
        }
        return null;
    }

    public String getModuleCodeById(Short moduleId) {
        if (ConfigData.MODULE_CONFIG != null) {
            for (Module module : ConfigData.MODULE_CONFIG) {
                if (module.getId().equals(moduleId)) {
                    return module.getCode();
                }
            }
        }
        return null;
    }

    public Module getModuleById(Short moduleId) {
        if (ConfigData.MODULE_CONFIG != null) {
            for (Module module : ConfigData.MODULE_CONFIG) {
                if (module.getId().equals(moduleId)) {
                    return module;
                }
            }
        }
        return null;
    }

    public String getModuleTypeNameById(Short moduleTypeId) {
        if (ConfigData.MODULE_TYPE_CONFIG != null) {
            for (ModuleType moduleType : ConfigData.MODULE_TYPE_CONFIG) {
                if (moduleType.getId().equals(moduleTypeId)) {
                    return moduleType.getTypeName();
                }
            }
        }
        return null;
    }

    public String getModifyTypeNameById(int modifyTypeId) {
        if (ConfigData.MODIFY_TYPE_CONFIG != null) {
            for (ModifyType modifyType : ConfigData.MODIFY_TYPE_CONFIG) {
                if (modifyType.getId() == modifyTypeId) {
                    return modifyType.getModifyType();
                }
            }
        }
        return null;
    }
    /**
     * 判断此ip地址是否可以使用发布url
     * @param ipAddr
     * @return
     */
    public boolean canUseDeployUrl(String ipAddr) {
        if (ConfigData.ALLOWED_IP_CONFIG_USE_DEPLOY_URL == null) {
            return false;
        }
        if (ConfigData.ALLOWED_IP_CONFIG_USE_DEPLOY_URL.contains(ipAddr)) {
            return true;
        }
        return false;
    }

    /**
     * 判断此ip地址是否可以查看发布url
     * @param ipAddr
     * @return
     */
    public boolean canViewDeployUrl(String ipAddr) {
        if (ConfigData.ALLOWED_IP_CONFIG_VIEW_DEPLOY_URL == null) {
            return false;
        }
        if (ConfigData.ALLOWED_IP_CONFIG_VIEW_DEPLOY_URL.contains(ipAddr)) {
            return true;
        }
        return false;
    }

    /**
     * 判断此ip地址是否可以标识以发布生产的记录
     * @param ipAddr
     * @return
     */
    public boolean canMarkProductDeploy(String ipAddr) {
        if (ConfigData.ALLOWED_IP_CONFIG_MARK_PRODUCT_DEPLOY == null) {
            return false;
        }
        if (ConfigData.ALLOWED_IP_CONFIG_MARK_PRODUCT_DEPLOY.contains(ipAddr)) {
            return true;
        }
        return false;
    }

    /**
     * 根据ip获取员工姓名
     * @param ipAddr
     * @return
     */
    public String obtainCrewName(String ipAddr) {
        if (ConfigData.IP_CREWNAME_MAPPING == null) {
            return "";
        }
        return ConfigData.IP_CREWNAME_MAPPING.get(ipAddr);
    }

    /**
     * 判断module是否存在于moduleList中
     * @param moduleList
     * @param module
     * @return
     */
    private boolean isExistedInList(List<Module> moduleList, Module module) {
        for (Module module1 : moduleList) {
            if (module1.getCode().equals(module.getCode())) {
                return true;
            }
        }
        return false;
    }

}
