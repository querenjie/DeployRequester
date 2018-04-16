package com.myself.deployrequester.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
public class StatusMessageCarrier implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientIp;                //客户端的IP
    private String subject;                 //消息主题
    private List<String> messageList;       //消息内容列表

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return "StatusMessageCarrier{" +
                "clientIp='" + clientIp + '\'' +
                ", subject='" + subject + '\'' +
                ", messageList=" + messageList +
                '}';
    }
}
