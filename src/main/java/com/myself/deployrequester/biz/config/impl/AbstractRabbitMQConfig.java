package com.myself.deployrequester.biz.config.impl;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.RabbitMQConf;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by QueRenJie on ${date}
 */
public abstract class AbstractRabbitMQConfig extends AbstractConfig {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(AbstractRabbitMQConfig.class);

    /**
     * 添加主题和RabbitMQ配置的对应关系
     * @param config
     */
    protected void addSubjectRabbitMQConfigMapping(Object[] config) {
        RabbitMQConf rabbitMQConf = new RabbitMQConf();
        String subject = (String)config[0];
        String rabbitmqHost = (String)config[1];
        String rabbitmqPort = (String)config[2];
        String userName = (String)config[3];
        String password = (String)config[4];
        String queueName = (String)config[5];
        rabbitMQConf.setRabbitmqHost(rabbitmqHost);
        rabbitMQConf.setRabbitmqPort(Integer.parseInt(rabbitmqPort));
        rabbitMQConf.setUserName(userName);
        rabbitMQConf.setPassword(password);
        rabbitMQConf.setQueueName(queueName);
        ConfigData.SUBJECT_RABBITMQCONF_MAPPING.put(subject, rabbitMQConf);
    }

    /**
     * 工程模块的配置
     */
    @Override
    public void buildModulesConfig() {

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
}
