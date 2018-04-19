package com.myself.deployrequester.util.rabbitmq;

import com.mongodb.Bytes;
import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.design.dynamiccomponent.ComponentManager;
import com.myself.deployrequester.bo.RabbitMQConf;
import com.myself.deployrequester.bo.TotalDBScriptInfoForFileGenerate;
import com.myself.deployrequester.util.Log4jUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;


/**
 * Created by QueRenJie on ${date}
 */
public class RabbitMQUtil {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(RabbitMQUtil.class);

    private RabbitMQConf rabbitMQConf;
    private String clientIpAddr;        //客户端IP，用于拼装队列名称
    private String subject = null;

    /**
     * 用于向客户端发送消息时用的
     * @param subject
     * @param clientIpAddr
     */
    public RabbitMQUtil(String subject, String clientIpAddr) {
        this.rabbitMQConf = ConfigData.SUBJECT_RABBITMQCONF_MAPPING.get(subject);
        this.clientIpAddr = clientIpAddr;
        this.subject = subject;
    }

    /**
     * 用于从客户端读取消息时用的
     * @param subject
     */
    public RabbitMQUtil(String subject) {
        this.rabbitMQConf = ConfigData.SUBJECT_RABBITMQCONF_MAPPING.get(subject);
        this.subject = subject;
    }


    /**
     * 检查实例化本类的对象后相关属性中是否已有数据，只要有其中一项属性没数据就返回false。
     * @return
     */
    private boolean checkParams() {
        if (this.rabbitMQConf == null) {
            Log4jUtil.info(logger, "未能获取到配置文件中的RabbitMQ的配置");
            return false;
        }
        return true;
    }

    /**
     * 创建channel
     * @param subject
     * @return
     */
    private Channel createChannel() throws IOException, TimeoutException {
        if (!checkParams()) {
            return null;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQConf.getRabbitmqHost());
        factory.setUsername(rabbitMQConf.getUserName());
        factory.setPassword(rabbitMQConf.getPassword());
        factory.setPort(rabbitMQConf.getRabbitmqPort());

        Log4jUtil.info(logger, "连接RabbitMQ的参数：");
        Log4jUtil.info(logger, "factory.getHost()=" + factory.getHost());
        Log4jUtil.info(logger, "factory.getUsername()=" + factory.getUsername());
        Log4jUtil.info(logger, "factory.getPassword()=" + factory.getPassword());
        Log4jUtil.info(logger, "factory.getPort()=" + factory.getPort());
        Log4jUtil.info(logger, "然后执行语句Connection connection = factory.newConnection()");
        // 打开连接和创建频道，与发送端一样
        Connection connection = null;
        connection = factory.newConnection();
        Log4jUtil.info(logger, "创建connection对象成功。");
        Channel channel = connection.createChannel();
        if (channel != null) {
            Log4jUtil.info(logger, "创建channel对象成功。");
        }
        return channel;
    }

    /**
     * 创建用于写业务数据到客户端的Channel
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private Channel getChannelForSendBizData() throws IOException, TimeoutException {
        Log4jUtil.info(logger, "进入RabbitMQUtil.getChannelForSendBizData。");

        /**
         * 创建连接连接到MabbitMQ
         */
        Channel channel = createChannel();
        if (this.clientIpAddr == null) {
            Log4jUtil.info(logger, "未能获取到本机的IP地址");
            return null;
        }
        // 指定一个队列
        channel.queueDeclare(rabbitMQConf.getQueueName() + "_" + clientIpAddr, false, false, false, null);

        return channel;
    }

    /**
     * 向客户端写业务数据
     * @param totalDBScriptInfoForFileGenerate
     * @throws IOException
     * @throws TimeoutException
     */
    public void sendBizDataToClient(TotalDBScriptInfoForFileGenerate totalDBScriptInfoForFileGenerate) throws IOException, TimeoutException {
        Log4jUtil.info(logger, "进入RabbitMQUtil.sendBizDataToClient方法。");
        Channel channel = getChannelForSendBizData();
        if (channel == null) {
            Log4jUtil.info(logger, "创建channel对象失败。");
            return;
        }
        channel.basicPublish("", rabbitMQConf.getQueueName() + "_" + clientIpAddr, null, SerializationUtils.serialize(totalDBScriptInfoForFileGenerate));
        Log4jUtil.info(logger, "channel绑定了队列" + rabbitMQConf.getQueueName() + "_" + clientIpAddr + ",并发送信息到队列。");
        channel.close();
    }

    /**
     * 创建用于写状态信息到客户端的Channel
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private Channel getChannelForSendStatusInfo() throws IOException, TimeoutException {
        /**
         * 创建连接连接到MabbitMQ
         */
        Channel channel = createChannel();
        if (channel == null) {
            return null;
        }
        // 指定一个队列
        channel.queueDeclare(rabbitMQConf.getQueueName() + "_status_" + clientIpAddr, false, false, false, null);

        return channel;
    }

    /**
     * 向客户端写状态信息
     * @param totalDBScriptInfoForFileGenerate
     * @throws IOException
     * @throws TimeoutException
     */
    public void sendStatusInfoToClient(TotalDBScriptInfoForFileGenerate totalDBScriptInfoForFileGenerate) throws IOException, TimeoutException {
        Channel channel = getChannelForSendBizData();
        if (channel == null) {
            return;
        }
        channel.basicPublish("", rabbitMQConf.getQueueName() + "_status_" + clientIpAddr, null, SerializationUtils.serialize(totalDBScriptInfoForFileGenerate));
    }

    /**
     * 生成一个用于接收来自客户端的状态信息的channel
     * @return
     */
    public Channel getChannelForReceiveStatusInfo() throws IOException, TimeoutException {
        Log4jUtil.info(logger, "进入RabbitMQUtil.getChannelForReceiveStatusInfo(),queue name is : " + rabbitMQConf.getQueueName() + "_status_toserver");
        Channel channel = createChannel();
        if (channel == null) {
            Log4jUtil.info(logger, "创建channel对象失败。");
            return null;
        }
        Log4jUtil.info(logger, "Channel channel = createChannel()执行成功。");

        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        try {
            channel.queueDeclare(rabbitMQConf.getQueueName() + "_status_toserver", false, false, false, null);
        } catch (IOException e) {
            Log4jUtil.error(logger, "创建channel报错", e);
            e.printStackTrace();
            return null;
        }

        return channel;
    }

    /**
     * 启动RabbitMQ消息接收的监听服务，监听来自客户端发过来的状态信息
     * @param channelForReceiveMsg
     * @param consumer
     * @throws IOException
     */
    public void receiveStatusInfoListening(Channel channelForReceiveMsg, Consumer consumer) throws IOException {
        if (channelForReceiveMsg == null || consumer == null) {
            return;
        }
        channelForReceiveMsg.basicConsume(this.rabbitMQConf.getQueueName() + "_status_toserver", true, consumer);
        Log4jUtil.info(logger, "RabbitMQUtil.receiveStatusInfoListening(),监听的queue name is : " + rabbitMQConf.getQueueName() + "_status_toserver");
    }


}
