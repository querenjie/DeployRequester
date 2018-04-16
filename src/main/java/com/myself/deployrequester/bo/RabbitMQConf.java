package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class RabbitMQConf {
    private String rabbitmqHost;            //rabbitmq的ip地址
    private int rabbitmqPort;               //rabbitmq的端口
    private String userName;                //用户名
    private String password;                //密码
    private String queueName;               //队列名称

    public String getRabbitmqHost() {
        return rabbitmqHost;
    }

    public void setRabbitmqHost(String rabbitmqHost) {
        this.rabbitmqHost = rabbitmqHost;
    }

    public int getRabbitmqPort() {
        return rabbitmqPort;
    }

    public void setRabbitmqPort(int rabbitmqPort) {
        this.rabbitmqPort = rabbitmqPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public String toString() {
        return "RabbitMQConf{" +
                "rabbitmqHost='" + rabbitmqHost + '\'' +
                ", rabbitmqPort=" + rabbitmqPort +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", queueName='" + queueName + '\'' +
                '}';
    }
}
