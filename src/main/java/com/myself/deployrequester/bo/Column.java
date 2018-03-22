package com.myself.deployrequester.bo;

public class Column {
    private  String columnName;//列名
    private  String comment; //备注
    private  String columnType; //类型
    private  boolean isNotNull;//是否为不空
    private  String primaryKey;//是否主键

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", comment='" + comment + '\'' +
                ", columnType='" + columnType + '\'' +
                ", isNotNull=" + isNotNull +
                ", primaryKey='" + primaryKey + '\'' +
                '}';
    }
}
