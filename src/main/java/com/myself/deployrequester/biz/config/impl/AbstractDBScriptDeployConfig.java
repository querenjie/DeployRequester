package com.myself.deployrequester.biz.config.impl;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.DBServer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by QueRenJie on ${date}
 */
public abstract class AbstractDBScriptDeployConfig extends AbstractConfig {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(AbstractDBScriptDeployConfig.class);

    /**
     * 添加数据库服务器的配置信息
     * @param config
     */
    protected void addDatabaseConfig(Object[] config) {
        DBServer dbServer =new DBServer();
        String id =(String)config[0];
        String ip =(String)config[1];
        String templateDBName=(String)config[2];
        String userName=(String)config[3];
        String password=(String)config[4];
        String port=(String)config[5];
        String comment =(String) config[6];
        String typeCode =(String) config[7];
        dbServer.setId(id);
        dbServer.setIp(ip);
        dbServer.setTemplateDBName(templateDBName);
        dbServer.setUserName(userName);
        dbServer.setPassword(password);
        dbServer.setPort(port);
        dbServer.setComment(comment);
        dbServer.setTypeCode(typeCode);
        ConfigData.DATABASE_LIST.add(dbServer);
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
     * RabbitMQ的配置
     */
    @Override
    public void buildRabbitMQConfig() {

    }
}
