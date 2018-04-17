package com.myself.deployrequester.biz.config.impl;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.LockElement;
import com.myself.deployrequester.biz.config.sharedata.RoleEnum;
import com.myself.deployrequester.biz.config.spi.Config;
import com.myself.deployrequester.bo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by QueRenJie on ${date}
 */
public abstract class AbstractConfig implements Config {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(AbstractConfig.class);

    /**
     * 构建配置
     */
    public final void build() {
        this.buildModulesConfig();
        this.buildApplicationDeployConfig();
        this.buildDBScriptDeployConfig();
        this.buildRabbitMQConfig();
    }

    /**
     * 工程模块的配置
     */
    public abstract void buildModulesConfig();

    /**
     * 应用发布的配置
     */
    public abstract void buildApplicationDeployConfig();

    /**
     * 数据库脚本发布的配置
     */
    public abstract void buildDBScriptDeployConfig();

    /**
     * RabbitMQ的配置
     */
    public abstract void buildRabbitMQConfig();

    /**
     * 添加ip地址到允许访问此发布申请系统的ip集合中
     * @param ipAddr
     */
    protected void addAllowedIpConfig(String ipAddr, int whichPrivilege) {
        if (whichPrivilege == Config.USE_DEPLOY_URL) {
            ConfigData.ALLOWED_IP_CONFIG_USE_DEPLOY_URL.add(ipAddr);
        }
        if (whichPrivilege == Config.VIEW_DEPLOY_URL) {
            ConfigData.ALLOWED_IP_CONFIG_VIEW_DEPLOY_URL.add(ipAddr);
        }
        if (whichPrivilege == Config.MARK_PRODUCT_DEPLOY) {
            ConfigData.ALLOWED_IP_CONFIG_MARK_PRODUCT_DEPLOY.add(ipAddr);
        }
        if (whichPrivilege == Config.LOCK_DEPLOY_REQUEST) {
            ConfigData.ALLOWED_IP_CONFIG_LOCK_DEPLOY_REQUEST.add(ipAddr);
        }
        if (whichPrivilege == Config.DEPLOY_DBSCRIPT) {
            ConfigData.ALLOWED_IP_CONFIG_DEPLOY_DBSCRIPT.add(ipAddr);
        }
        if (whichPrivilege == Config.CHANGE_CAN_EXEC_DBSCRIPT) {
            ConfigData.ALLOWED_IP_CONFIG_CHANGE_CAN_EXEC_DBSCRIPT.add(ipAddr);
        }
        if (whichPrivilege == Config.AUDIT_DEPLOY_REQUEST) {
            ConfigData.ALLOWED_IP_CONFIG_AUDIT_DEPLOY_REQUEST.add(ipAddr);
        }
        if (whichPrivilege == Config.GENERATE_DBSCRIPT_FILE) {
            ConfigData.ALLOWED_IP_CONFIG_GENERATE_DBSCRIPT_FILE.add(ipAddr);
        }
    }




}
