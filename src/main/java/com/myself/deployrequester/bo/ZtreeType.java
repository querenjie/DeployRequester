package com.myself.deployrequester.bo;

public class ZtreeType {
    private  String id;//子节点id
    private  String pId; //父节点id
    private  String name; //节点名称
    private  boolean isparent;//是否为父节点
    private String DbServerId;   //id
    private String ip;   //ip
    private String userName;    //用户名
    private String password;    //密码
    private String port;        //端口
    private String dataBaseName;//数据库名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsparent() {
        return isparent;
    }

    public void setIsparent(boolean isparent) {
        this.isparent = isparent;
    }

    public String getDbServerId() {
        return DbServerId;
    }

    public void setDbServerId(String dbServerId) {
        DbServerId = dbServerId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    @Override
    public String toString() {
        return "ZtreeType{" +
                "id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                ", isparent=" + isparent +
                ", DbServerId='" + DbServerId + '\'' +
                ", ip='" + ip + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", port='" + port + '\'' +
                ", dataBaseName='" + dataBaseName + '\'' +
                '}';
    }
}
