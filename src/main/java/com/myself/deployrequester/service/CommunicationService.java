package com.myself.deployrequester.service;

import com.myself.deployrequester.bo.DBScriptInfoForFileGenerate;
import com.myself.deployrequester.util.Log4jUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class CommunicationService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(CommunicationService.class);

    public String sendObjects(String toIpAddr, int toPort, List<DBScriptInfoForFileGenerate> dbScriptInfoForFileGenerateList) {
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            socket = new Socket(toIpAddr, toPort);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(dbScriptInfoForFileGenerateList);
            return "ok";
        } catch (IOException e) {
            Log4jUtil.error(logger, "socket发送数组对象报错", e);
            return "socket发送数组对象报错，连接" + toIpAddr + ":" + toPort + "失败，请手工启动客户端的jar文件。";
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
