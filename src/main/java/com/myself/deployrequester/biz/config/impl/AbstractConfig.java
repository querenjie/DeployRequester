package com.myself.deployrequester.biz.config.impl;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.LockElement;
import com.myself.deployrequester.biz.config.sharedata.RoleEnum;
import com.myself.deployrequester.biz.config.spi.Config;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.controller.ConfigdataController;
import com.myself.deployrequester.util.Log4jUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by QueRenJie on ${date}
 */
public abstract class AbstractConfig implements Config {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(AbstractConfig.class);

    /**
     * 项目的配置
     */
    protected List<Project> projectList;
    /**
     * 模块的配置
     */
    protected List<Module> moduleList;
    /**
     * 模块类型的配置
     */
    protected List<ModuleType> moduleTypeList;
    /**
     * 修改类型的配置
     */
    protected List<ModifyType> modifyTypeList;
    /**
     * 模块的部署URL配置
     */
    protected Map<String, DeployURL> moduleDeployURLConfig;

    /**
     * 测试环境上的日志URL
     */
    protected Map<String, LogURL> testLogURLMap;

    /**
     * 生产环境上的日志URL
     */
    protected Map<String, LogURL> productLogURLMap;

    /**
     * 预生产环境上的日志URL
     */
    protected Map<String, LogURL> preLogURLMap;

    /**
     * 部署结果配置
     */
    protected List<OutcomeConfig> outcomeConfigList;

    /**
     * 允许发布模块的ip地址
     */
    protected Set<String> allowedIpForUseDeployUrl;

    /**
     * 允许看到发布url的ip地址
     */
    protected Set<String> allowedIpForViewDeployUrl;

    /**
     * 允许标识生产发布的ip地址
     */
    protected Set<String> allowedIpForMarkProductDeploy;

    /**
     * 允许锁定发布申请的操作人员的ip地址
     */
    protected Set<String> allowedIpForLockDeployRequest;

    /**
     * 允许发布数据库脚本的操作人员的ip地址
     */
    protected Set<String> allowedIpForDeployDbscript;

    /**
     * 客户端ip地址和对应的开发人员姓名
     */
    protected Map<String, String> ipAndCrewNameMap;

    /**
     * 客户端ip地址和角色的对应关系
     */
    protected Map<String, RoleEnum> ipAndRoleMap;

    /**
     * 存放所有项目、模块、类型的锁定情况
     */
    protected List<LockElement> lockElementList;

    /**
     * 构建配置
     */
    public final void build() {
        moduleList = new ArrayList<Module>();
        moduleDeployURLConfig = new HashMap<String, DeployURL>();
        testLogURLMap = new HashMap<String, LogURL>();
        productLogURLMap = new HashMap<String, LogURL>();
        preLogURLMap = new HashMap<String, LogURL>();
        outcomeConfigList = new ArrayList<OutcomeConfig>();
        projectList = new ArrayList<Project>();
        moduleTypeList = new ArrayList<ModuleType>();
        modifyTypeList = new ArrayList<ModifyType>();
        allowedIpForUseDeployUrl = new HashSet<String>();
        allowedIpForViewDeployUrl = new HashSet<String>();
        allowedIpForMarkProductDeploy = new HashSet<String>();
        allowedIpForLockDeployRequest = new HashSet<String>();
        allowedIpForDeployDbscript = new HashSet<String>();
        ipAndCrewNameMap = new HashMap<String, String>();
        ipAndRoleMap = new HashMap<String, RoleEnum>();
        lockElementList = new ArrayList<LockElement>();
        this.buildConfig();
        ConfigData.MODULE_CONFIG = moduleList;
        ConfigData.MODULE_DEPLOY_URL_CONFIG = moduleDeployURLConfig;
        ConfigData.TEST_LOG_URL_CONFIG = testLogURLMap;
        ConfigData.PRODUCT_LOG_URL_CONFIG = productLogURLMap;
        ConfigData.PRE_LOG_URL_CONFIG = preLogURLMap;
        ConfigData.OUTCOME_CONFIG = outcomeConfigList;
        ConfigData.PROJECT_CONFIG = projectList;
        ConfigData.MODULE_TYPE_CONFIG = moduleTypeList;
        ConfigData.MODIFY_TYPE_CONFIG = modifyTypeList;
        ConfigData.ALLOWED_IP_CONFIG_USE_DEPLOY_URL = allowedIpForUseDeployUrl;
        ConfigData.ALLOWED_IP_CONFIG_VIEW_DEPLOY_URL = allowedIpForViewDeployUrl;
        ConfigData.ALLOWED_IP_CONFIG_MARK_PRODUCT_DEPLOY = allowedIpForMarkProductDeploy;
        ConfigData.ALLOWED_IP_CONFIG_LOCK_DEPLOY_REQUEST = allowedIpForLockDeployRequest;
        ConfigData.ALLOWED_IP_CONFIG_DEPLOY_DBSCRIPT = allowedIpForDeployDbscript;
        ConfigData.IP_CREWNAME_MAPPING = ipAndCrewNameMap;
        ConfigData.IP_ROLE_MAPPING = ipAndRoleMap;
        ConfigData.LOCK_ELEMENT_LIST = lockElementList;
    }

    public abstract void buildConfig();

    /**
     * 添加项目
     * @param config
     */
    protected void addProject(Object[] config) {
        String projectName = (String)config[1];
//        Log4jUtil.info(logger, "projectName=" + projectName);
//        try {
//            projectName = new String(((String)config[1]).getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//
//        }

        Project project = new Project();
        project.setId((Integer)config[0]);
        project.setProjectName(projectName);
        projectList.add(project);
    }

    /**
     * 添加模块
     * @param config
     */
    protected void addModule(Object[] config) {
        String moduleName = (String)config[2];
//        Log4jUtil.info(logger, "moduleName=" + moduleName);
//        try {
//            moduleName = new String(((String)config[2]).getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        Module module = new Module();
        module.setId((Short)config[0]);
        module.setCode((String)config[1]);
        module.setName(moduleName);
        moduleList.add(module);
    }

    /**
     * 添加模块类型
     * @param config
     */
    protected void addModuleType(Object[] config) {
        String moduleTypeName = (String)config[1];
//        Log4jUtil.info(logger, "moduleTypeName=" + moduleTypeName);
//        try {
//            moduleTypeName = new String(((String)config[1]).getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        ModuleType moduleType = new ModuleType();
        moduleType.setId((Short)config[0]);
        moduleType.setTypeName(moduleTypeName);
        moduleTypeList.add(moduleType);
    }

    /**
     * 添加修改类型
     * @param config
     */
    protected void addModifyType(Object[] config) {
        String modifyTypeName = (String)config[1];
//        Log4jUtil.info(logger, "modifyTypeName=" + modifyTypeName);
//        try {
//            modifyTypeName = new String(((String)config[1]).getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        ModifyType modifyType = new ModifyType();
        modifyType.setId((Integer)config[0]);
        modifyType.setModifyType(modifyTypeName);
        modifyTypeList.add(modifyType);
    }

    /**
     * 添加模块发布url
     * @param config
     */
    protected void addModuleDeployURL(Object[] config) {
        String moduleName = (String)config[2];
//        Log4jUtil.info(logger, "moduleName=" + moduleName);
//        try {
//            moduleName = new String(((String)config[2]).getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String projectId = String.valueOf(config[0]);
        DeployURL deployURL = new DeployURL();
        deployURL.setProjectId(projectId);
        deployURL.setModuleCode((String)config[1]);     //这里的moduleCode是英文字符,不是数字
        deployURL.setModuleName(moduleName);
        deployURL.setType((String)config[3]);           //这里的type是英文字符,不是数字
        deployURL.setDeployUrlForTestEnv((String)config[4]);
        deployURL.setDeployUrlForProductEnv((String)config[5]);

        moduleDeployURLConfig.put(deployURL.getProjectId()+"_"+deployURL.getModuleCode()+"_"+deployURL.getType(), deployURL);

        //初始项目、模块、类型的锁定标识为不锁定
        LockElement lockElement = new LockElement();
        lockElement.setProjectId(Short.parseShort(deployURL.getProjectId()));
        lockElement.setModuleId(getModuleIdByModuleCode(deployURL.getModuleCode()));
        lockElement.setModuleTypeId(getModuleTypeIdByModuleTypeName(deployURL.getType()));
        lockElement.setLocked(false);
        lockElementList.add(lockElement);
    }

    /**
     * 根据模块的英文名称获取他的Id
     * @param moduleCode
     * @return
     */
    private Short getModuleIdByModuleCode(String moduleCode) {
        if (StringUtils.isBlank(moduleCode)) {
            return null;
        }
        if (moduleList != null) {
            for (Module module : moduleList) {
                if (moduleCode.equals(module.getCode())) {
                    return module.getId();
                }
            }
        }
        return null;
    }

    /**
     * 根据模块类型的名称获取他的Id
     * @param moduleTypeName
     * @return
     */
    private Short getModuleTypeIdByModuleTypeName(String moduleTypeName) {
        if (StringUtils.isBlank(moduleTypeName)) {
            return null;
        }
        if (modifyTypeList != null) {
            for (ModuleType moduleType : moduleTypeList) {
                if (moduleTypeName.equals(moduleType.getTypeName())) {
                    return moduleType.getId();
                }
            }
        }
        return null;
    }

    /**
     * 添加模块的测试环境的日志URL
     * @param config
     */
    protected void addTestLogURL(Object[] config) {
        LogURL logURL = createInstance(config);
        testLogURLMap.put(logURL.getProjectId()+"_"+logURL.getModuleCode()+"_"+logURL.getType(), logURL);
    }

    /**
     * 添加模块的生产环境的日志URL
     * @param config
     */
    protected void addProductLogURL(Object[] config) {
        LogURL logURL = createInstance(config);
        productLogURLMap.put(logURL.getProjectId()+"_"+logURL.getModuleCode()+"_"+logURL.getType(), logURL);
    }

    protected void addPreLogURL(Object[] config) {
        LogURL logURL = createInstance(config);
        preLogURLMap.put(logURL.getProjectId()+"_"+logURL.getModuleCode()+"_"+logURL.getType(), logURL);
    }

    /**
     * 添加发布结果配置
     * @param config
     */
    protected void addOutcomeConfig(Object[] config) {
        String suggestion = (String)config[2];
//        try {
//            suggestion = new String(((String)config[2]).getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        OutcomeConfig outcomeConfig = new OutcomeConfig();
        outcomeConfig.setKeywordsInOutcomeMsg((String)config[0]);
        outcomeConfig.setDeploySuccess((Boolean)config[1]);
        outcomeConfig.setSuggestion(suggestion);
        outcomeConfigList.add(outcomeConfig);
    }

    /**
     * 添加ip地址到允许访问此发布申请系统的ip集合中
     * @param ipAddr
     */
    protected void addAllowedIpConfig(String ipAddr, int whichPrivilege) {
        if (whichPrivilege == Config.USE_DEPLOY_URL) {
            allowedIpForUseDeployUrl.add(ipAddr);
        }
        if (whichPrivilege == Config.VIEW_DEPLOY_URL) {
            allowedIpForViewDeployUrl.add(ipAddr);
        }
        if (whichPrivilege == Config.MARK_PRODUCT_DEPLOY) {
            allowedIpForMarkProductDeploy.add(ipAddr);
        }
        if (whichPrivilege == Config.LOCK_DEPLOY_REQUEST) {
            allowedIpForLockDeployRequest.add(ipAddr);
        }
        if (whichPrivilege == Config.DEPLOY_DBSCRIPT) {
            allowedIpForDeployDbscript.add(ipAddr);
        }
    }


    /**
     * 创建客户端ip地址和对应的开发人员姓名的映射关系
     * @param config
     */
    protected void addIpAndCrewNameMapping(Object[] config) {
        String ip = (String) config[0];
        String developer = (String) config[1];
//        Log4jUtil.info(logger, "developer=" + developer);
//        try {
//            developer = new String(((String) config[1]).getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        RoleEnum role = (RoleEnum) config[2];

        if (StringUtils.isBlank(ip) || StringUtils.isBlank(developer)) {
            return;
        }

        ipAndCrewNameMap.put(ip, developer);
        ipAndRoleMap.put(ip, role);
    }

    private LogURL createInstance(Object[] logURLConfig) {
        LogURL logURL = new LogURL();
        logURL.setProjectId(String.valueOf(logURLConfig[0]));
        logURL.setModuleCode((String)logURLConfig[1]);
        logURL.setModuleName((String)logURLConfig[2]);
        logURL.setType((String)logURLConfig[3]);
        logURL.setLogUrl((String)logURLConfig[4]);
        logURL.setStartLogUrl((String)logURLConfig[5]);
        logURL.setStartStatusLogUrl((String)logURLConfig[6]);
        return logURL;
    }


}
