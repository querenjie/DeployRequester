package com.myself.deployrequester.biz.config.sharedata;

import com.myself.deployrequester.bo.*;
import sun.rmi.runtime.Log;

import java.util.*;

/**
 * 配置数据服务
 * Created by QueRenJie on ${date}
 */
public class ConfigData {
    /**
     * 项目配置
     */
    public static List<Project> PROJECT_CONFIG = new ArrayList<Project>();
    /**
     * 模块配置
     */
    public static List<Module> MODULE_CONFIG = new ArrayList<Module>();
    /**
     * 模块类型配置
     */
    public static List<ModuleType> MODULE_TYPE_CONFIG = new ArrayList<ModuleType>();
    /**
     * 修改类型配置
     */
    public static List<ModifyType> MODIFY_TYPE_CONFIG = new ArrayList<ModifyType>();
    /**
     * 模块的部署URL配置
     */
    public static Map<String, DeployURL> MODULE_DEPLOY_URL_CONFIG = new HashMap<String, DeployURL>();

    /**
     * 返回结果的配置
     */
    public static List<OutcomeConfig> OUTCOME_CONFIG = new ArrayList<OutcomeConfig>();

    /**
     * 测试环境上的日志URL的配置
     */
    public static Map<String, LogURL> TEST_LOG_URL_CONFIG = new HashMap<String, LogURL>();

    /**
     * 生产环境上的日志URL的配置
     */
    public static Map<String, LogURL> PRODUCT_LOG_URL_CONFIG = new HashMap<String, LogURL>();

    /**
     * 预生产环境上的日志URL的配置
     */
    public static Map<String, LogURL> PRE_LOG_URL_CONFIG = new HashMap<String, LogURL>();

    /**
     * 允许访问发布URL的ip地址
     */
    public static Set<String> ALLOWED_IP_CONFIG_USE_DEPLOY_URL = new HashSet<String>();

    /**
     * 允许查看发布的url的ip地址
     */
    public static Set<String> ALLOWED_IP_CONFIG_VIEW_DEPLOY_URL = new HashSet<String>();

    /**
     * 允许标识生产发布的ip地址
     */
    public static Set<String> ALLOWED_IP_CONFIG_MARK_PRODUCT_DEPLOY = new HashSet<String>();

    /**
     * 客户端ip地址和对应的开发人员姓名的映射关系
     */
    public static Map<String, String> IP_CREWNAME_MAPPING = new HashMap<String, String>();

    /**
     * 存放当前正开启着本工具的客户端ip
     * key:     ip地址
     * value:   当前毫秒数
     */
    public static Map<String, Long> IP_ALIVED_MAP = new HashMap<String, Long>();

    /**
     * 客户端ip地址和角色的对应关系
     */
    public static Map<String, RoleEnum> IP_ROLE_MAPPING = new HashMap<String, RoleEnum>();
}
