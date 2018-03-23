package com.myself.deployrequester.bo;

import java.util.List;

public class DBServer {
    private String id;   //id
    private String ip;   //ip
    private String templateDBName;  //模板名称
    private String userName;    //用户名
    private String password;    //密码
    private String port;        //端口
    private String comment;  //备注
    private List<Database> databaseList;
    private String typeCode;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTemplateDBName() {
        return templateDBName;
    }

    public void setTemplateDBName(String templateDBName) {
        this.templateDBName = templateDBName;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Database> getDatabaseList() {
        return databaseList;
    }

    public void setDatabaseList(List<Database> databaseList) {
        this.databaseList = databaseList;
    }
}
