package com.myself.deployrequester.model;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployDbserversDO {
    private String deploydbserversid;

    private String linkname;

    private String linknamedesc;

    private String ip;

    private Integer port;

    private String username;

    private Short belong;

    private String password;

    private Short needrecpwd;

    private String dbname;

    public String getDeploydbserversid() {
        return deploydbserversid;
    }

    public void setDeploydbserversid(String deploydbserversid) {
        this.deploydbserversid = deploydbserversid;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }

    public String getLinknamedesc() {
        return linknamedesc;
    }

    public void setLinknamedesc(String linknamedesc) {
        this.linknamedesc = linknamedesc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
        this.username = username;
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
        this.password = password;
    }

    public Short getNeedrecpwd() {
        return needrecpwd;
    }

    public void setNeedrecpwd(Short needrecpwd) {
        this.needrecpwd = needrecpwd;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
}
