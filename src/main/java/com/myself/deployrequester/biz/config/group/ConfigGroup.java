package com.myself.deployrequester.biz.config.group;

import com.myself.deployrequester.biz.config.impl.AbstractApplicationDeployConfig;
import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.spi.Config;
import com.myself.deployrequester.biz.design.dynamiccomponent.spi.ComponentContainer;
import com.myself.deployrequester.util.Log4jUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
public class ConfigGroup implements ComponentContainer<Config> {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(ConfigGroup.class);

    /**
     * 重新加载组件
     *
     * @param components
     */
    public void onReload(List<Config> components) {
        Log4jUtil.info(logger,"进入加载配置的方法ConfigGroup.onReload(List<Config> components)");
        if (components != null && components.size() > 0) {
            //首先清除一下全局变量中的内容
            clearGlobalList();
            for (Config config : components) {
                config.build();
            }
        }
    }

    /**
     * 组件的接口类型
     *
     * @return
     */
    public Class<Config> componentType() {
        return Config.class;
    }

    /**
     * 清除一下全局变量中的内容
     */
    private void clearGlobalList() {
        Log4jUtil.info(logger, "清除全局变量中的内容。。。。。。");
        ConfigData.PROJECT_CONFIG.clear();
        ConfigData.MODULE_CONFIG.clear();
        ConfigData.MODULE_TYPE_CONFIG.clear();
        ConfigData.MODIFY_TYPE_CONFIG.clear();
        ConfigData.LOCK_ELEMENT_LIST.clear();
        ConfigData.OUTCOME_CONFIG.clear();
        ConfigData.DATABASE_LIST.clear();
        ConfigData.ALLOWED_IP_CONFIG_USE_DEPLOY_URL.clear();
        ConfigData.ALLOWED_IP_CONFIG_VIEW_DEPLOY_URL.clear();
        ConfigData.ALLOWED_IP_CONFIG_MARK_PRODUCT_DEPLOY.clear();
        ConfigData.ALLOWED_IP_CONFIG_LOCK_DEPLOY_REQUEST.clear();
        ConfigData.ALLOWED_IP_CONFIG_DEPLOY_DBSCRIPT.clear();
        ConfigData.ALLOWED_IP_CONFIG_CHANGE_CAN_EXEC_DBSCRIPT.clear();
        ConfigData.ALLOWED_IP_CONFIG_AUDIT_DEPLOY_REQUEST.clear();
        ConfigData.ALLOWED_IP_CONFIG_GENERATE_DBSCRIPT_FILE.clear();
    }
}
