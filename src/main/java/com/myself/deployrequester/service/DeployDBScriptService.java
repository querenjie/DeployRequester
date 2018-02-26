package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.*;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.dao.DeployDbscriptDAO;
import com.myself.deployrequester.dao.DeployDbscriptDetailsqlDAO;
import com.myself.deployrequester.dao.DeployDbscriptSyncDetailsqlDAO;
import com.myself.deployrequester.dao.DeployDbserversDAO;
import com.myself.deployrequester.model.*;
import com.myself.deployrequester.po.*;
import com.myself.deployrequester.util.DBScriptUtil;
import com.myself.deployrequester.util.JdbcUtilForPostgres;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private DeployDbscriptDetailsqlDAO deployDbscriptDetailsqlDAO;
    @Autowired
    private DeployDbscriptSyncDetailsqlDAO deployDbscriptSyncDetailsqlDAO;


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

    /**
     * 根据脚本申请的主键获取脚本申请的对象
     * @param deployDbscriptId
     * @return
     * @throws Exception
     */
    public DeployDbscript getDeployDbscriptById(String deployDbscriptId) throws Exception {
        DeployDbscriptPO deployDbscriptPO = deployDbscriptDAO.selectByPrimaryKey(deployDbscriptId);
        if (deployDbscriptPO == null) {
            return null;
        }
        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptPO, deployDbscriptDO, true);
        DeployDbscript deployDbscript = new DeployDbscript();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscript, false);
        fillDeployDbscript(deployDbscript);

        return deployDbscript;
    }

    /**
     * 根据脚本申请的主键删除该条记录
     * @param deployDbscriptId
     * @return
     * @throws Exception
     */
    public int deleteById(String deployDbscriptId) throws Exception {
        deployDbscriptSyncDetailsqlDAO.deleteByDeployDbscriptId(deployDbscriptId);
        deployDbscriptDetailsqlDAO.deleteByDeployDbscriptId(deployDbscriptId);
        int delRecCount = deployDbscriptDAO.deleteByPrimaryKey(deployDbscriptId);
        return delRecCount;
    }

    /**
     * 执行脚本并记录状态
     * @param deployDbscriptDO
     * @return
     * @throws Exception
     */
    public String deployDbscript(DeployDbscriptDO deployDbscriptDO) {
        if (StringUtils.isBlank(deployDbscriptDO.getDbscript())) {
            //如果脚本内容为空则表示执行失败
            return "脚本内容为空。";
        }
        DeployDbserversPO deployDbserversPO = deployDbserversDAO.selectByPrimaryKey(deployDbscriptDO.getDeploydbserversid());
        if (deployDbserversPO == null) {
            return "找不到关联的数据库连接信息";
        }

        deployDbscriptDO.setExecutetime(new Date());

        List<DeployDbscriptDetailsqlPO> deployDbscriptDetailsqlPOList = deployDbscriptDetailsqlDAO.selectUnexecutedByDeployDbscriptId(deployDbscriptDO.getDeploydbscriptid());
        if (deployDbscriptDetailsqlPOList == null || deployDbscriptDetailsqlPOList.size() == 0) {
            DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
            BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, true);
            deployDbscriptPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));
            deployDbscriptPO.setExecutetime(new Date());
            deployDbscriptPO.setFailuremsg("");
            deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);
            return "脚本内容为空，发布脚本的过程直接归类为成功。";
        }

        if (deployDbscriptDO.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
            //如果是其他线程正在执行此脚本，则本线程退出。
            return "其他线程正在执行此脚本,本线程退出。";
        }
        if (deployDbscriptDO.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()) {
            //如果此脚本早已执行成功，则本线程退出。
            return "脚本早已执行成功,本线程退出。";
        }


        JdbcUtilForPostgres jdbcUtilForPostgres = new JdbcUtilForPostgres();
        String ip = deployDbserversPO.getIp();
        Integer port = deployDbserversPO.getPort();
        String username = deployDbserversPO.getUsername();
        String password = deployDbserversPO.getPassword();
        String dbname = deployDbserversPO.getDbname();

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = jdbcUtilForPostgres.getConn(ip, port, username, password, dbname);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "数据库连接出现问题", e);
            return "数据库连接出现问题:" + e;
        }

        //更新申请记录的执行状态
        DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, true);
        deployDbscriptPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTING.getCode())));
        deployDbscriptPO.setExecutetime(new Date());
        deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);


        if (conn != null) {
            for (DeployDbscriptDetailsqlPO deployDbscriptDetailsqlPO : deployDbscriptDetailsqlPOList) {
                String sql = deployDbscriptDetailsqlPO.getSubsql();
                deployDbscriptDetailsqlPO.setExecutorip(deployDbscriptDO.getExecutorip());
                deployDbscriptDetailsqlPO.setExecutor(deployDbscriptDO.getExecutor());
                deployDbscriptDetailsqlPO.setExecutetime(new Date());
                try {
                    jdbcUtilForPostgres.executeSql(sql, conn);
                    deployDbscriptDetailsqlPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));
                    deployDbscriptDetailsqlDAO.updateByPrimaryKeySelective(deployDbscriptDetailsqlPO);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log4jUtil.error(logger, "执行sql[" + sql + "]有问题：", e);

                    deployDbscriptDetailsqlPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_FAILED.getCode())));
                    deployDbscriptDetailsqlDAO.updateByPrimaryKeySelective(deployDbscriptDetailsqlPO);
                    deployDbscriptPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_FAILED.getCode())));
                    deployDbscriptPO.setFailuremsg("执行sql[" + sql + "]有问题：" + e);
                    deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);

                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                    return "执行sql[" + sql + "]有问题：" + e;
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

        deployDbscriptPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));
        deployDbscriptPO.setFailuremsg("");
        deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);
        return "脚本执行成功!";
    }

    /**
     * 执行同步脚本并记录状态
     * @param deployDbscriptDO
     * @return
     * @throws Exception
     */
    public String deployDbscriptForSync(DeployDbscriptDO deployDbscriptDO) {
        DeployDbserversPO deployDbserversPO = deployDbserversDAO.selectByPrimaryKey(deployDbscriptDO.getDeploydbserversid());
        if (deployDbserversPO == null) {
            return "找不到关联的数据库连接信息";
        }

        deployDbscriptDO.setExecutetimeforsync(new Date());

        List<DeployDbscriptSyncDetailsqlPO> deployDbscriptSyncDetailsqlPOList = deployDbscriptSyncDetailsqlDAO.selectUnexecutedByDeployDbscriptId(deployDbscriptDO.getDeploydbscriptid());
        if (deployDbscriptSyncDetailsqlPOList == null || deployDbscriptSyncDetailsqlPOList.size() == 0) {
            DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
            BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, true);
            deployDbscriptPO.setExecutestatusforsync(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));
            deployDbscriptPO.setExecutetimeforsync(new Date());
            deployDbscriptPO.setFailuremsgforsync("");
            deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);
            return "脚本内容为空，发布脚本的过程直接归类为成功。";
        }

        if (deployDbscriptDO.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
            //如果是其他线程正在执行此脚本，则本线程退出。
            return "其他线程正在执行此脚本,本线程退出。";
        }
        if (deployDbscriptDO.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()) {
            //如果此脚本早已执行成功，则本线程退出。
            return "脚本早已执行成功,本线程退出。";
        }

        DeployDbserversPO deployDbserversPO1 = new DeployDbserversPO();
        deployDbserversPO1.setBelong(deployDbscriptDO.getBelong());
        deployDbserversPO1.setProjectid(deployDbscriptDO.getProjectid());
        deployDbserversPO1.setIssyncdb(Short.valueOf("1"));

        List<DeployDbserversPO> syncdbList = deployDbserversDAO.selectByDeployDbserversPO(deployDbserversPO1);
        if (syncdbList == null || syncdbList.size() == 0) {
            return "找不到同步库。";
        }
        //这个就是同步库的对象
        deployDbserversPO1 = syncdbList.get(0);

        JdbcUtilForPostgres jdbcUtilForPostgres = new JdbcUtilForPostgres();
        String ip = deployDbserversPO1.getIp();
        Integer port = deployDbserversPO1.getPort();
        String username = deployDbserversPO1.getUsername();
        String password = deployDbserversPO1.getPassword();
        String dbname = deployDbserversPO1.getDbname();

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = jdbcUtilForPostgres.getConn(ip, port, username, password, dbname);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "连接同步库出现问题", e);
            return "连接同步库出现问题:" + e;
        }

        //更新申请记录的执行状态
        DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, true);
        deployDbscriptPO.setExecutestatusforsync(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTING.getCode())));
        deployDbscriptPO.setExecutetimeforsync(new Date());
        deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);


        if (conn != null) {
            for (DeployDbscriptSyncDetailsqlPO deployDbscriptSyncDetailsqlPO : deployDbscriptSyncDetailsqlPOList) {
                String sql = deployDbscriptSyncDetailsqlPO.getSubsql();
                deployDbscriptSyncDetailsqlPO.setExecutorip(deployDbscriptDO.getExecutoripforsync());
                deployDbscriptSyncDetailsqlPO.setExecutor(deployDbscriptDO.getExecutorforsync());
                deployDbscriptSyncDetailsqlPO.setExecutetime(new Date());
                try {
                    jdbcUtilForPostgres.executeSql(sql, conn);
                    deployDbscriptSyncDetailsqlPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));
                    deployDbscriptSyncDetailsqlDAO.updateByPrimaryKeySelective(deployDbscriptSyncDetailsqlPO);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log4jUtil.error(logger, "执行sql[" + sql + "]有问题：", e);

                    deployDbscriptSyncDetailsqlPO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_FAILED.getCode())));
                    deployDbscriptSyncDetailsqlDAO.updateByPrimaryKeySelective(deployDbscriptSyncDetailsqlPO);
                    deployDbscriptPO.setExecutestatusforsync(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_FAILED.getCode())));
                    deployDbscriptPO.setFailuremsgforsync("执行sql[" + sql + "]有问题：" + e);
                    deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);

                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                    return "执行sql[" + sql + "]有问题：" + e;
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

        deployDbscriptPO.setExecutestatusforsync(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));
        deployDbscriptPO.setFailuremsgforsync("");
        deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);
        return "脚本执行成功!";
    }

    /**
     * 修改脚本申请记录
     * @param deployDbscriptDO
     * @return
     */
    public int modifiy(DeployDbscriptDO deployDbscriptDO) throws Exception {
        DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, true);
        int updateSuccessCount = deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);
        return updateSuccessCount;
    }

    public Connection getConn(DeployDbservers deployDbservers) throws Exception {
        JdbcUtilForPostgres jdbcUtilForPostgres = new JdbcUtilForPostgres();
        String ip = deployDbservers.getIp();
        Integer port = deployDbservers.getPort();
        String username = deployDbservers.getUsername();
        String password = deployDbservers.getPassword();
        String dbname = deployDbservers.getDbname();

        Connection conn = null;
        try {
            conn = jdbcUtilForPostgres.getConn(ip, port, username, password, dbname);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "数据库连接出现问题", e);
            throw e;
        }

        return conn;
    }

    /**
     * 获取同步库中的所有表名
     * @param conn
     * @return
     */
    private List<String> getAllTables(Connection conn) {
        List<String> tableNameList = new ArrayList<String>();
        String sql = "select tablename from pg_tables where schemaname not in ('information_schema', 'pg_catalog')";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tableNameList.add(rs.getString("tablename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return tableNameList;
    }

    /**
     * 获取需要同步的sql
     * @param conn
     * @param seperatedStatementList
     * @return
     */
    public List<String> getSyncSqlList(Connection conn, List<String> seperatedStatementList) {
        List<String> syncSqlList = new ArrayList<String>();

        List<String> allTablesInSyncDb = getAllTables(conn);
        if (allTablesInSyncDb == null || allTablesInSyncDb.size() == 0) {
            return null;
        }
        DBScriptUtil dbScriptUtil = new DBScriptUtil();
        for (String sql : seperatedStatementList) {
            sql = sql.toLowerCase();
            String tableName = dbScriptUtil.obtainTableName(sql);
            if (StringUtils.isNotBlank(tableName)) {
                if (allTablesInSyncDb.contains(tableName)) {
                    syncSqlList.add(sql);
                }
            }
        }

        return syncSqlList;
    }

    public int updateSelective(DeployDbscriptDO deployDbscriptDO) throws Exception {
        DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, true);
        return deployDbscriptDAO.updateByPrimaryKeySelective(deployDbscriptPO);
    }

    public List<DeployDbscript> selectOnlyNeedDeployByQueryDbscriptDO(QueryDbscriptDO queryDbscriptDO) throws Exception {
        QueryDbscriptPO queryDbscriptPO = new QueryDbscriptPO();
        BeanUtils.copyProperties(queryDbscriptDO, queryDbscriptPO, true);
        List<DeployDbscriptPO> deployDbscriptPOList = deployDbscriptDAO.selectOnlyNeedDeployByQueryDbscriptPO(queryDbscriptPO);
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
                String dbLinkDesc = deployDbserversPO.getLinkname() + "(" + deployDbserversPO.getLinknamedesc() + ")" + "[" + EnvOfDBEnum.getDescByCode(deployDbserversPO.getBelong().intValue()) + "--" + deployDbserversPO.getIp() + ":" + deployDbserversPO.getPort() + "/" + deployDbserversPO.getDbname() + "]";
                deployDbscript.setDblinkDesc(dbLinkDesc);
            }
        }
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        deployDbscript.setFormatedCreateTime(formatter.format(deployDbscript.getCreatetime()));
        if (deployDbscript.getExecutetime() != null) {
            deployDbscript.setFormatedExecuteTime(formatter.format(deployDbscript.getExecutetime()));
        }

        deployDbscript.setExecuteStatusDesc(DBExecuteStatusEnum.getDescByCode(deployDbscript.getExecutestatus().intValue()));
        deployDbscript.setBelongDesc(EnvOfDBEnum.getDescByCode(deployDbscript.getBelong().intValue()));

        List<DeployDbscriptDetailsqlPO> executedDeployDbscriptDetailsqlList = deployDbscriptDetailsqlDAO.selectExecutedByDeployDbscriptId(deployDbscript.getDeploydbscriptid());
        if (executedDeployDbscriptDetailsqlList != null) {
            List<String> executedSqlList = new ArrayList<String>();
            for (DeployDbscriptDetailsqlPO deployDbscriptDetailsqlPO : executedDeployDbscriptDetailsqlList) {
                executedSqlList.add(deployDbscriptDetailsqlPO.getSubsql());
            }
            deployDbscript.setExecutedSqlList(executedSqlList);
        }

        List<DeployDbscriptDetailsqlPO> unexecutedDeployDbscriptDetialsqlList = deployDbscriptDetailsqlDAO.selectUnexecutedByDeployDbscriptId(deployDbscript.getDeploydbscriptid());
        if (unexecutedDeployDbscriptDetialsqlList != null) {
            List<String> unexecutedSqlList = new ArrayList<String>();
            for (DeployDbscriptDetailsqlPO deployDbscriptDetailsqlPO : unexecutedDeployDbscriptDetialsqlList) {
                unexecutedSqlList.add(deployDbscriptDetailsqlPO.getSubsql());
            }
            deployDbscript.setUnexecutedSqlList(unexecutedSqlList);
        }

        deployDbscript.setIsabandonedDesc(DBIsAbandonedEnum.getDescByCode(deployDbscript.getIsabandoned().intValue()));

        //和同步sql有关的
        DeployDbserversPO deployDbserversPO = new DeployDbserversPO();
        deployDbserversPO.setBelong(deployDbscript.getBelong());
        deployDbserversPO.setProjectid(deployDbscript.getProjectid());
        deployDbserversPO.setIssyncdb(Short.valueOf("1"));
        List<DeployDbserversPO> syncdbList = deployDbserversDAO.selectByDeployDbserversPO(deployDbserversPO);
        if (syncdbList != null && syncdbList.size() > 0) {
            DeployDbserversPO deployDbserversPO1 = syncdbList.get(0);
            String dbLinkDescForSync = deployDbserversPO1.getLinkname() + "(" + deployDbserversPO1.getLinknamedesc() + ")" + "[" + EnvOfDBEnum.getDescByCode(deployDbserversPO1.getBelong().intValue()) + "--" + deployDbserversPO1.getIp() + ":" + deployDbserversPO1.getPort() + "/" + deployDbserversPO1.getDbname() + "]";
            deployDbscript.setDblinkDescForSync(dbLinkDescForSync);
        } else {
            deployDbscript.setDblinkDescForSync("");
        }

        if (deployDbscript.getExecutetimeforsync() != null) {
            deployDbscript.setFormatedExceuteTimeForSync(formatter.format(deployDbscript.getExecutetimeforsync()));
        }

        if (deployDbscript.getExecutestatusforsync() == null) {
            deployDbscript.setExecuteStatusDescForSync("");
        } else {
            deployDbscript.setExecuteStatusDescForSync(DBExecuteStatusEnum.getDescByCode(deployDbscript.getExecutestatusforsync().intValue()));
        }

        List<DeployDbscriptSyncDetailsqlPO> executedDeployDbscriptSyncDetailsqlList = deployDbscriptSyncDetailsqlDAO.selectExecutedByDeployDbscriptId(deployDbscript.getDeploydbscriptid());
        if (executedDeployDbscriptSyncDetailsqlList != null && executedDeployDbscriptSyncDetailsqlList.size() > 0) {
            deployDbscript.setHasSyncSql("yes");
            List<String> executedSqlList = new ArrayList<String>();
            for (DeployDbscriptSyncDetailsqlPO deployDbscriptSyncDetailsqlPO : executedDeployDbscriptSyncDetailsqlList) {
                executedSqlList.add(deployDbscriptSyncDetailsqlPO.getSubsql());
            }
            deployDbscript.setExecutedSqlListForSync(executedSqlList);
        }

        List<DeployDbscriptSyncDetailsqlPO> unexecutedDeployDbscriptSyncDetialsqlList = deployDbscriptSyncDetailsqlDAO.selectUnexecutedByDeployDbscriptId(deployDbscript.getDeploydbscriptid());
        if (unexecutedDeployDbscriptSyncDetialsqlList != null && unexecutedDeployDbscriptSyncDetialsqlList.size() > 0) {
            deployDbscript.setHasSyncSql("yes");
            List<String> unexecutedSqlList = new ArrayList<String>();
            for (DeployDbscriptSyncDetailsqlPO deployDbscriptSyncDetailsqlPO : unexecutedDeployDbscriptSyncDetialsqlList) {
                unexecutedSqlList.add(deployDbscriptSyncDetailsqlPO.getSubsql());
            }
            deployDbscript.setUnexecutedSqlListForSync(unexecutedSqlList);
        }

        if (deployDbscript.getIsabandonedforsync() == null) {
            deployDbscript.setIsabandonedDescForSync("");
        } else {
            deployDbscript.setIsabandonedDescForSync(DBIsAbandonedEnum.getDescByCode(deployDbscript.getIsabandonedforsync().intValue()));
        }

        /********************补全deployDbscript中的属性值( end )*******************************/

    }


}
