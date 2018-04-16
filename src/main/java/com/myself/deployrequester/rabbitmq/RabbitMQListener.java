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
        Log4jUtil.info(logger, "starting listener..");
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil(subject);
        Channel channelForReceiveMsg = null;
        try {
            channelForReceiveMsg = rabbitMQUtil.getChannelForReceiveStatusInfo();
        } catch (IOException e) {
            Log4jUtil.error(logger, "创建RabbitMQ Channel报错", e);
        } catch (TimeoutException e) {
            Log4jUtil.error(logger, "创建RabbitMQ Channel报错", e);
        }
        if (channelForReceiveMsg == null) {
            return;
        }

        // 创建队列消费者
        final Consumer consumer = new DefaultConsumer(channelForReceiveMsg) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                if ("createDbscriptFile".equals(subject)) {
                    StatusMessageCarrier statusMessageCarrier = (StatusMessageCarrier) SerializationUtils.deserialize(body);
                    Log4jUtil.info(logger, "主题：" + subject + "|" + statusMessageCarrier.toString());
                    Log4jUtil.info(logger, " [x] Proccessing... at " + new Date().toLocaleString());

                    //将从客户端发来的消息保存到全局变量中。
                    RabbitMQReceiveSumForCreateDbscriptFile.add(statusMessageCarrier.getClientIp(), statusMessageCarrier.getMessageList());

                    Log4jUtil.info(logger, " [x] Done! at " + new Date().toLocaleString());
                }
            }
        };
        //监听消息队列
        try {
            rabbitMQUtil.receiveStatusInfoListening(channelForReceiveMsg, consumer);
        } catch (IOException e) {
            Log4jUtil.error(logger, "启动RabbitMQ Channel监听报错", e);
            e.printStackTrace();
            return;
        }

        RabbitMQConf rabbitMQConf = ConfigData.SUBJECT_RABBITMQCONF_MAPPING.get(subject);
        Log4jUtil.info(logger, "RabbitMQ（主题：" + subject + "|队列配置信息：" + rabbitMQConf.toString() + "） is started.");

    }
}
