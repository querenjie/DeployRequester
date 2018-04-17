package com.myself.deployrequester.biz.config.impl;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.Module;
import com.myself.deployrequester.bo.ModuleType;
import com.myself.deployrequester.bo.Project;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by QueRenJie on ${date}
 */
public abstract class AbstractModulesConfig extends AbstractConfig {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(AbstractModulesConfig.class);

    /**
     * 添加项目
     * @param config
     */
    protected void addProject(Object[] config) {
        String projectName = (String)config[1];

        Project project = new Project();
        project.setId((Integer)config[0]);
        project.setProjectName(projectName);
        ConfigData.PROJECT_CONFIG.add(project);
    }

    /**
     * 添加模块
     * @param config
     */
    protected void addModule(Object[] config) {
        String moduleName = (String)config[2];

        Module module = new Module();
        module.setId((Short)config[0]);
        module.setCode((String)config[1]);
        module.setName(moduleName);
        ConfigData.MODULE_CONFIG.add(module);
    }

    /**
     * 添加模块类型
     * @param config
     */
    protected void addModuleType(Object[] config) {
        String moduleTypeName = (String)config[1];

        ModuleType moduleType = new ModuleType();
        moduleType.setId((Short)config[0]);
        moduleType.setTypeName(moduleTypeName);
        ConfigData.MODULE_TYPE_CONFIG.add(moduleType);
    }




    /**
     * 应用发布的配置
     */
    @Override
    public void buildApplicationDeployConfig() {

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
