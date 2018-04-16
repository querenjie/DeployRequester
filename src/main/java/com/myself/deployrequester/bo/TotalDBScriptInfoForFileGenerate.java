package com.myself.deployrequester.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
public class TotalDBScriptInfoForFileGenerate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientIpAddress;         //客户端的ip，以便客户端知道自己应该接收哪些消息。
    private List<DBScriptInfoForFileGenerate> dbScriptInfoForFileGenerateList;

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public List<DBScriptInfoForFileGenerate> getDbScriptInfoForFileGenerateList() {
        return dbScriptInfoForFileGenerateList;
    }

    public void setDbScriptInfoForFileGenerateList(List<DBScriptInfoForFileGenerate> dbScriptInfoForFileGenerateList) {
        this.dbScriptInfoForFileGenerateList = dbScriptInfoForFileGenerateList;
    }

    @Override
    public String toString() {
        return "TotalDBScriptInfoForFileGenerate{" +
                "clientIpAddress='" + clientIpAddress + '\'' +
                ", dbScriptInfoForFileGenerateList=" + dbScriptInfoForFileGenerateList +
                '}';
    }
}
