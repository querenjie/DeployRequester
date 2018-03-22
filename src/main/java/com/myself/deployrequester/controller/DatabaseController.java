package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.ZtreeDataConfig;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.service.QueryDatabaseService;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.json.JsonResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("queryDatabase")
public class DatabaseController extends CommonMethodWrapper {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DatabaseController.class);

    @RequestMapping("/query_database")
    public String gotoDatabase() {
        return "query_database";
    }

    @Autowired
    private QueryDatabaseService queryDatabaseService;

    //获取树信息
    @ResponseBody
    @RequestMapping(value = "/initTreeNode",method =RequestMethod.POST)
    public JsonResult qureyAllTreeNode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取DBServer中id相同的库中所有的数据库,然后返回给页面
        JsonResult result = null;
        List<ZtreeType> ztreeData = queryDatabaseService.getZtreeData();
        if(ztreeData !=null && ztreeData.size()>0){
            result = JsonResult.createSuccess("ok");
            result.addDataAll(ztreeData);
            return result;
        }
        return null;
    }

    //查询数据库中字段信息
    //ip:treeNode.ip,//服务器ip
    //:treeNode.port,//服务器端口号
    //userName:treeNode.userName,//服务器用户名
    //password:treeNode.password,//服务器密码
    @ResponseBody
    @RequestMapping(value = { "/queryColumn" }, method = {RequestMethod.POST})
    public JsonResult getTableColumns(@RequestParam("id") String id,
                                      @RequestParam("dataBaseName") String dataBaseName,
                                      @RequestParam("tableName") String tableName,
                                      @RequestParam("ip") String ip,
                                      @RequestParam("port") String port,
                                      @RequestParam("userName") String userName,
                                      @RequestParam("password") String password
                                      ) throws Exception {
        System.out.println("id:  "+id +" dataBaseName: "+dataBaseName +"  tableName:  "+tableName+"  ip:  "+ip+"  port:  "+port+"  userName:  "+userName+"  password:  "+password);
        JsonResult result = null;
        if(port !=null && port != "") {
            try {
                List<Column> columnList = queryDatabaseService.getTableColumns(id, tableName, ip, port, userName, password, dataBaseName);
                result = JsonResult.createSuccess("获取数据表字段正常");
                result.addDataAll(columnList);
            } catch (Exception e) {
                Log4jUtil.error(logger, "查询数据表字段异常", e);
                e.printStackTrace();
                result = JsonResult.createFailed("query tableColum failed");
                result.addData("查询数据表字段异常:" + e.getStackTrace().toString());
            }
        }
        return result;
    }
}
