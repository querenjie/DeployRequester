package com.myself.deployrequester.bo;

import java.util.List;

public class Database {
    private String id;      //数据库的id
    private  String dataBaseName;//数据库名
    private List<Table> tableList; //数据库中有的表明

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Database{" +
                "id='" + id + '\'' +
                ", dataBaseName='" + dataBaseName + '\'' +
                ", tableList=" + tableList +
                '}';
    }
}
