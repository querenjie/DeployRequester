package com.myself.deployrequester.po;

import java.util.UUID;

public class DeployDbserversPO {
    private String deploydbserversid = UUID.randomUUID().toString();

    private String ip;

    private Integer port;

    private String username;

    private Short belong;

    private String password;

    private Short needrecpwd;

    public String getDeploydbserversid() {
        return deploydbserversid;
    }

    public void setDeploydbserversid(String deploydbserversid) {
        this.deploydbserversid = deploydbserversid == null ? null : deploydbserversid.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Short getBelong() {
        return belong;
    }

    public void setBelong(Short belong) {
        this.belong = belong;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Short getNeedrecpwd() {
        return needrecpwd;
    }

    public void setNeedrecpwd(Short needrecpwd) {
        this.needrecpwd = needrecpwd;
    }
}