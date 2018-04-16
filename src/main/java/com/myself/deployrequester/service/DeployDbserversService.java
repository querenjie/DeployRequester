package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.EnvOfDBEnum;
import com.myself.deployrequester.bo.DBScriptInfoForFileGenerate;
import com.myself.deployrequester.bo.DeployDbservers;
import com.myself.deployrequester.bo.Module;
import com.myself.deployrequester.bo.Project;
import com.myself.deployrequester.dao.DeployDbserversDAO;
import com.myself.deployrequester.model.DeployDbserversDO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.po.DeployDbserversPO;
import com.myself.deployrequester.po.DeployRequesterPO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployDbserversService extends CommonDataService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DeployDbserversService.class);

    @Autowired
    private DeployDbserversDAO deployDbserversDAO;

    /**
     * 修改或插入数据到t_deploy_dbservers表中
     * @param deployDbserversDO
     * @return
     * @throws Exception
     */
    public int saveDeployDbservers(DeployDbserversDO deployDbserversDO) throws Exception {
        DeployDbserversPO deployDbserversPO = new DeployDbserversPO();
        BeanUtils.copyProperties(deployDbserversDO, deployDbserversPO, false);
        int updateSuccessRecordCount = deployDbserversDAO.update1(deployDbserversPO);
        if (updateSuccessRecordCount == 1) {
            return 1;
        }
        int insertSuccessRecordCount = deployDbserversDAO.insert(deployDbserversPO);
        if (insertSuccessRecordCount == 1) {
            deployDbserversDO.setDeploydbserversid(deployDbserversPO.getDeploydbserversid());
        }
        return insertSuccessRecordCount;
    }

    /**
     * insert data into table t_deploy_dbservers
     *
     * @param deployDbserversDO
     * @return 1 indicates success, other values indicates failure
     */
    public int insertDeployDbservers(DeployDbserversDO deployDbserversDO) throws Exception {
        DeployDbserversPO deployDbserversPO = new DeployDbserversPO();
        BeanUtils.copyProperties(deployDbserversDO, deployDbserversPO, false);
        int successRecCount = deployDbserversDAO.insert(deployDbserversPO);
        if (successRecCount == 1) {
            deployDbserversDO.setDeploydbserversid(deployDbserversPO.getDeploydbserversid());
        }
        return successRecCount;
    }

    /**
     * 修改记录中的数据
     * @param deployDbserversDO
     * @return
     * @throws Exception
     */
    public int updateByPrimaryKeySelective(DeployDbserversDO deployDbserversDO) throws Exception {
        DeployDbserversPO deployDbserversPO = new DeployDbserversPO();
        BeanUtils.copyProperties(deployDbserversDO, deployDbserversPO, true);
        return deployDbserversDAO.updateByPrimaryKeySelective(deployDbserversPO);
    }

    /**
     * 删除指定的记录
     * @param deploydbserversid
     * @return
     * @throws Exception
     */
    public int deleteByPrimaryKey(String deploydbserversid) throws Exception {
        return deployDbserversDAO.deleteByPrimaryKey(deploydbserversid);
    }

    /**
     * 检测数据库是否能连上。其实也是在检测输入的参数内容是否正确。
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return  返回连接报错的内容。如果能正常连接则返回空字符串。
     */
    public String checkConnection(String ip, Integer port, String username, String password, String dbname) {
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        Integer result;
        try{
            Class.forName("org.postgresql.Driver").newInstance();
            String connectUrl ="jdbc:postgresql://" + ip + ":" + port + "/" + dbname;
            conn = DriverManager.getConnection(connectUrl, username, password);
            st = conn.createStatement();
            String sql = "SELECT 1";
            rs = st.executeQuery(sql);
            while(rs.next()){
                result = rs.getInt(1);
                if (result != null && result.intValue() == 1) {
                    return "";
                }
            }
        } catch (Exception e) {
            return "连接数据库有问题:" + e.toString();
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
        return "连接数据库有问题。";
    }

    public List<DeployDbservers> selectByDeployDbserversDO(DeployDbserversDO deployDbserversDO) throws Exception {
        DeployDbserversPO deployDbserversPO = new DeployDbserversPO();
        BeanUtils.copyProperties(deployDbserversDO, deployDbserversPO, true);
        List<DeployDbserversPO> deployDbserversPOList = deployDbserversDAO.selectByDeployDbserversPO(deployDbserversPO);
        if (deployDbserversPOList == null) {
            return null;
        }
        List<DeployDbservers> deployDbserversList = new ArrayList<DeployDbservers>();
        for (DeployDbserversPO deployDbserversPO1 : deployDbserversPOList) {
            DeployDbservers deployDbservers = new DeployDbservers();
            BeanUtils.copyProperties(deployDbserversPO1, deployDbservers, true);
            fillDeployDbservers(deployDbservers);
            deployDbserversList.add(deployDbservers);
        }
        return deployDbserversList;
    }

    /**
     * 根据主键获取数据库连接
     * @param deploydbserversid
     * @return
     * @throws Exception
     */
    public DeployDbservers selectByPrimarykey(String deploydbserversid) throws Exception {
        DeployDbserversPO deployDbserversPO = deployDbserversDAO.selectByPrimaryKey(deploydbserversid);
        if (deployDbserversPO == null) {
            return null;
        }
        DeployDbservers deployDbservers = new DeployDbservers();
        BeanUtils.copyProperties(deployDbserversPO, deployDbservers, true);
        fillDeployDbservers(deployDbservers);

        return deployDbservers;
    }

    public List<DeployDbservers> selectAll() throws Exception {
        List<DeployDbserversPO> deployDbserversPOList = deployDbserversDAO.selectAll();
        if (deployDbserversPOList == null) {
            return null;
        }
        List<DeployDbservers> deployDbserversList = new ArrayList<DeployDbservers>();
        for (DeployDbserversPO deployDbserversPO1 : deployDbserversPOList) {
            DeployDbservers deployDbservers = new DeployDbservers();
            BeanUtils.copyProperties(deployDbserversPO1, deployDbservers, true);
            fillDeployDbservers(deployDbservers);
            deployDbserversList.add(deployDbservers);
        }
        return deployDbserversList;
    }

    /**
     * 填充数组对象中的每个DBScriptInfoForFileGenerate对象中的属性值
     * @param dbScriptInfoForFileGenerateList
     * @param isSync        是否为同步。
     * @return
     * @throws Exception
     */
    public List<DBScriptInfoForFileGenerate> fillObject(List<DBScriptInfoForFileGenerate> dbScriptInfoForFileGenerateList, boolean isSync) throws Exception {
        for (DBScriptInfoForFileGenerate dbScriptInfoForFileGenerate : dbScriptInfoForFileGenerateList) {
            String deploydbserversid = dbScriptInfoForFileGenerate.getDeploydbserversid();
            if (StringUtils.isBlank(deploydbserversid)) {
                continue;
            }
            DeployDbserversPO deployDbserversPO = deployDbserversDAO.selectByPrimaryKey(deploydbserversid);
            if (deployDbserversPO == null) {
                continue;
            }
            if (isSync) {
                //如果是同步库的话数据库名称直接写死了。目前各项目的同步库名称都是这个。
                //理论上不该写死，但是这样会比较消耗cpu。
                dbScriptInfoForFileGenerate.setDbname("vr-reportcenter");
            } else {
                dbScriptInfoForFileGenerate.setDbname(deployDbserversPO.getDbname());
            }
        }
        return dbScriptInfoForFileGenerateList;
    }

    private void fillDeployDbservers(DeployDbservers deployDbservers) {
        if (deployDbservers == null) {
            return;
        }

        /********************补全deployDbservers中的属性值(begin)*******************************/
        Project project = getProjectById(deployDbservers.getProjectid().intValue());
        deployDbservers.setProjectName(project.getProjectName());

        Module module = getModuleById(deployDbservers.getModuleid());
        deployDbservers.setModuleName(module.getCode() + "-" + module.getName());

        deployDbservers.setBelongName(EnvOfDBEnum.getDescByCode(deployDbservers.getBelong().intValue()));
        /********************补全deployDbservers中的属性值( end )*******************************/
    }

}
