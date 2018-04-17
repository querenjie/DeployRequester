package com.myself.deployrequester.biz.config.impl;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.LockElement;
import com.myself.deployrequester.biz.config.sharedata.RoleEnum;
import com.myself.deployrequester.bo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by QueRenJie on ${date}
 */
public abstract class AbstractApplicationDeployConfig extends AbstractConfig {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(AbstractApplicationDeployConfig.class);

    /**
     * 添加修改类型
     * @param config
     */
    protected void addModifyType(Object[] config) {
        String modifyTypeName = (String)config[1];

        ModifyType modifyType = new ModifyType();
        modifyType.setId((Integer)config[0]);
        modifyType.setModifyType(modifyTypeName);
        ConfigData.MODIFY_TYPE_CONFIG.add(modifyType);
    }

    /**
     * 添加模块发布url
     * @param config
     */
    protected void addModuleDeployURL(Object[] config) {
        String moduleName = (String)config[2];

        String projectId = String.valueOf(config[0]);
        DeployURL deployURL = new DeployURL();
        deployURL.setProjectId(projectId);
        deployURL.setModuleCode((String)config[1]);     //这里的moduleCode是英文字符,不是数字
        deployURL.setModuleName(moduleName);
        deployURL.setType((String)config[3]);           //这里的type是英文字符,不是数字
        deployURL.setDeployUrlForTestEnv((String)config[4]);
        deployURL.setDeployUrlForProductEnv((String)config[5]);

        ConfigData.MODULE_DEPLOY_URL_CONFIG.put(deployURL.getProjectId()+"_"+deployURL.getModuleCode()+"_"+deployURL.getType(), deployURL);

        //初始项目、模块、类型的锁定标识为不锁定
        LockElement lockElement = new LockElement();
        lockElement.setProjectId(Short.parseShort(deployURL.getProjectId()));
        lockElement.setModuleId(getModuleIdByModuleCode(deployURL.getModuleCode()));
        lockElement.setModuleTypeId(getModuleTypeIdByModuleTypeName(deployURL.getType()));
        lockElement.setLocked(false);
        ConfigData.LOCK_ELEMENT_LIST.add(lockElement);
    }

    /**
     * 添加模块的测试环境的日志URL
     * @param config
     */
    protected void addTestLogURL(Object[] config) {
        LogURL logURL = createInstance(config);
        ConfigData.TEST_LOG_URL_CONFIG.put(logURL.getProjectId()+"_"+logURL.getModuleCode()+"_"+logURL.getType(), logURL);
    }

    /**
     * 添加模块的生产环境的日志URL
     * @param config
     */
    protected void addProductLogURL(Object[] config) {
        LogURL logURL = createInstance(config);
        ConfigData.PRODUCT_LOG_URL_CONFIG.put(logURL.getProjectId()+"_"+logURL.getModuleCode()+"_"+logURL.getType(), logURL);
    }

    protected void addPreLogURL(Object[] config) {
        LogURL logURL = createInstance(config);
        ConfigData.PRE_LOG_URL_CONFIG.put(logURL.getProjectId()+"_"+logURL.getModuleCode()+"_"+logURL.getType(), logURL);
    }

    /**
     * 添加发布结果配置
     * @param config
     */
    protected void addOutcomeConfig(Object[] config) {
        String suggestion = (String)config[2];

        OutcomeConfig outcomeConfig = new OutcomeConfig();
        outcomeConfig.setKeywordsInOutcomeMsg((String)config[0]);
        outcomeConfig.setDeploySuccess((Boolean)config[1]);
        outcomeConfig.setSuggestion(suggestion);
        ConfigData.OUTCOME_CONFIG.add(outcomeConfig);
    }

    /**
     * 创建客户端ip地址和对应的开发人员姓名的映射关系
     * @param config
     */
    protected void addIpAndCrewNameMapping(Object[] config) {
        String ip = (String) config[0];
        String name = (String) config[1];

        RoleEnum role = (RoleEnum) config[2];

        if (StringUtils.isBlank(ip) || StringUtils.isBlank(name)) {
            return;
        }

        ConfigData.IP_CREWNAME_MAPPING.put(ip, name);
        ConfigData.IP_ROLE_MAPPING.put(ip, role);
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


    /**
     * 根据模块的英文名称获取他的Id
     * @param moduleCode
     * @return
     */
    private Short getModuleIdByModuleCode(String moduleCode) {
        if (StringUtils.isBlank(moduleCode)) {
            return null;
        }
        if (ConfigData.MODULE_CONFIG != null) {
            for (Module module : ConfigData.MODULE_CONFIG) {
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
        if (ConfigData.MODULE_TYPE_CONFIG != null) {
            for (ModuleType moduleType : ConfigData.MODULE_TYPE_CONFIG) {
                if (moduleTypeName.equals(moduleType.getTypeName())) {
                    return moduleType.getId();
                }
            }
        }
        return null;
    }



    /**
     * 工程模块的配置
     */
    @Override
    public void buildModulesConfig() {

    }

    /**
     * 数据库脚本发布的配置
     */
    @Override
    public void buildDBScriptDeployConfig() {

    }

    /**
     * RabbitMQ的配置
     */
    @Override
    public void buildRabbitMQConfig() {

    }
}
