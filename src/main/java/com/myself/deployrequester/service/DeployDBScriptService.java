package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.DeployStatusForProdEnvEnum;
import com.myself.deployrequester.biz.config.sharedata.EnvOfDBEnum;
import com.myself.deployrequester.biz.config.sharedata.TestStatusEnum;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.dao.DeployDbscriptDAO;
import com.myself.deployrequester.dao.DeployDbserversDAO;
import com.myself.deployrequester.model.DeployDbscriptDO;
import com.myself.deployrequester.model.QueryDbscriptDO;
import com.myself.deployrequester.po.DeployDbscriptPO;
import com.myself.deployrequester.po.DeployDbserversPO;
import com.myself.deployrequester.po.QueryDbscriptPO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployDBScriptService extends CommonDataService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DeployDBScriptService.class);

    @Autowired
    private DeployDbscriptDAO deployDbscriptDAO;
    @Autowired
    private DeployDbserversDAO deployDbserversDAO;

    public int insert(DeployDbscriptDO deployDbscriptDO) throws Exception {
        DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, false);
        int insertSuccessRecordCount = deployDbscriptDAO.insert(deployDbscriptPO);
        if (insertSuccessRecordCount == 1) {
            //如果插入数据成功的话就直接把id赋值给DO对象
            deployDbscriptDO.setDeploydbscriptid(deployDbscriptPO.getDeploydbscriptid());
        }
        return insertSuccessRecordCount;
    }

    public List<DeployDbscript> selectByQueryDbscriptDO(QueryDbscriptDO queryDbscriptDO) throws Exception {
        QueryDbscriptPO queryDbscriptPO = new QueryDbscriptPO();
        BeanUtils.copyProperties(queryDbscriptDO, queryDbscriptPO, true);
        List<DeployDbscriptPO> deployDbscriptPOList = deployDbscriptDAO.selectByQueryDbscriptPO(queryDbscriptPO);
        if (deployDbscriptPOList == null) {
            return null;
        }
        List<DeployDbscript> deployDbscriptList = new ArrayList<DeployDbscript>();
        for (DeployDbscriptPO deployDbscriptPO : deployDbscriptPOList) {
            DeployDbscript deployDbscript = new DeployDbscript();
            BeanUtils.copyProperties(deployDbscriptPO, deployDbscript, true);
            fillDeployDbscript(deployDbscript);
            deployDbscriptList.add(deployDbscript);
        }
        return deployDbscriptList;
    }

    private void fillDeployDbscript(DeployDbscript deployDbscript) {
        if (deployDbscript == null) {
            return;
        }

        /********************补全deployDbscript中的属性值(begin)*******************************/
        Project project = getProjectById(deployDbscript.getProjectid().intValue());
        deployDbscript.setProjectName(project.getProjectName());

        Module module = getModuleById(deployDbscript.getModuleid());
        deployDbscript.setModuleName(module.getCode() + "-" + module.getName());

        if (deployDbscript.getDeploydbserversid() != null) {
            DeployDbserversPO deployDbserversPO = deployDbserversDAO.selectByPrimaryKey(deployDbscript.getDeploydbserversid());
            if (deployDbserversPO != null) {
                String dbLinkDesc = EnvOfDBEnum.getDescByCode(deployDbserversPO.getBelong().intValue()) + "--" + deployDbserversPO.getIp() + ":" + deployDbserversPO.getPort();
                deployDbscript.setDblinkDesc(dbLinkDesc);
            }
        }
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        deployDbscript.setFormatedCreateTime(formatter.format(deployDbscript.getCreatetime()));
        if (deployDbscript.getExecutetime() != null) {
            deployDbscript.setFormatedExecutetime(formatter.format(deployDbscript.getExecutetime()));
        }
        /********************补全deployDbscript中的属性值( end )*******************************/

    }


    public static void main(String[] args) {
        DeployDBScriptService deployDBScriptService = new DeployDBScriptService();
        String ip = "172.19.14.200";
        Integer port = 5432;
        String username = "postgres";
        String password = "postgres";
        boolean canConnect = deployDBScriptService.checkConnection(ip, port, username, password);
        if (canConnect) {
            System.out.println("连接成功");
        } else {
            System.out.println("连接失败");
        }
    }

    public boolean checkConnection(String ip, Integer port, String username, String password) {
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        Integer result;
        try{
            Class.forName("org.postgresql.Driver").newInstance();
            String connectUrl ="jdbc:postgresql://" + ip + ":" + port + "/postgres";
            conn = DriverManager.getConnection(connectUrl, username, password);
            st = conn.createStatement();
            String sql = "SELECT 1";
            rs = st.executeQuery(sql);
            while(rs.next()){
                result = rs.getInt(1);
                if (result != null && result.intValue() == 1) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
