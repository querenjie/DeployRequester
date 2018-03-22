package com.myself.deployrequester.bo;

import java.util.List;

public class Table {
    private String id;   //数据表的id
    private  String tableName;//表名
    private  String comment; //备注
    private List<Column> columnList; //表中的字段

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", tableName='" + tableName + '\'' +
                ", comment='" + comment + '\'' +
                ", columnList=" + columnList +
                '}';
    }
}
