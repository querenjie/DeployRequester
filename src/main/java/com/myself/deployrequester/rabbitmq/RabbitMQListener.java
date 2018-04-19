package com.myself.deployrequester.rabbitmq;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.RabbitMQReceiveSumForCreateDbscriptFile;
import com.myself.deployrequester.biz.design.dynamiccomponent.ComponentManager;
import com.myself.deployrequester.bo.RabbitMQConf;
import com.myself.deployrequester.bo.StatusMessageCarrier;
import com.myself.deployrequester.bo.TotalDBScriptInfoForFileGenerate;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.rabbitmq.RabbitMQUtil;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by QueRenJie on ${date}
 */
public class RabbitMQListener {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(RabbitMQListener.class);

    private String subject;

    public RabbitMQListener(String subject) {
        this.subject = subject;
    }

    public void start() {
        new ConsumerOfRabbitMQ(this.subject).start();
    }
}
