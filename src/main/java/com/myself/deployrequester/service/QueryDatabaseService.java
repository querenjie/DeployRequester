package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.util.JdbcUtilForPostgres1;
import com.myself.deployrequester.util.Log4jUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hengli on ${date}
 */
@Service
public class QueryDatabaseService  extends CommonDataService{
    /**
     * 日志
     */
    private static final Logger logger = LogManager.getLogger(QueryDatabaseService.class);


    /**
     * 获取指定的数据库中的表.
     * @param dbServer          在这个dbServer中可以看出是那个数据库.
     * @return
     * @throws Exception
     */
    public List<Table> getTables(Database database,DBServer dbServer) throws Exception {
        JdbcUtilForPostgres1 jdbcUtilForPostgres1 = new JdbcUtilForPostgres1();
        Connection conn = jdbcUtilForPostgres1.getConn(dbServer.getIp(), Integer.parseInt(dbServer.getPort()), dbServer.getUserName(), dbServer.getPassword(), database.getDataBaseName());

        List<Table> tableList = null;

        String sql = "SELECT " +
                "relname AS tabname, " +
                "CAST ( " +
                "obj_description (relfilenode, 'pg_class') AS VARCHAR " +
                ") AS COMMENT " +
                "FROM " +
                "pg_class " +
                "WHERE " +
                "relkind = 'r' " +
                "AND relname NOT LIKE 'pg_%' " +
                "AND relname NOT LIKE 'sql_%' " +
                "AND relname NOT IN ('dao_log', 'tab_list', 'user_history') " +
                "ORDER BY " +
                "relname";

        ResultSet rs = jdbcUtilForPostgres1.executeQuery(sql, conn);
        int j=1;
        if (rs != null) {
            tableList = new ArrayList<Table>();
            while (rs.next()) {
                Table table = new Table();
                String tableName = rs.getString(1);
                String comment = rs.getString(2);
                table.setTableName(tableName);
                table.setComment(comment);
                table.setId(database.getId()+"-"+j);
                j++;
                tableList.add(table);
            }
        }
        if(rs != null)
        {   // 关闭记录集
            try{
                rs.close() ;
            }catch(SQLException e){
                e.printStackTrace() ;
            }
        }
        return tableList;
    }
    /**
     * 获取template1数据库中的数据库信息
     * @param dbServer
     * @return
     * @throws Exception
     */
    public List<Database> getDatabases(DBServer dbServer) throws Exception {
        JdbcUtilForPostgres1 jdbcUtilForPostgres1 = new JdbcUtilForPostgres1();
        Connection conn = jdbcUtilForPostgres1.getConn(dbServer.getIp(), Integer.parseInt(dbServer.getPort()), dbServer.getUserName(), dbServer.getPassword(), dbServer.getTemplateDBName());

        List<Database> databaseList = null;

        String sql = "SELECT datname FROM pg_database where datistemplate='f' AND datname <> 'postgres' AND datname NOT LIKE '%crm%' AND datname NOT LIKE '%configcenter%' AND datname NOT LIKE '%tracesource%' AND datname NOT LIKE '%tms%' AND datname NOT LIKE '%wms%' AND datname NOT LIKE '%mall%'";

        ResultSet rs = jdbcUtilForPostgres1.executeQuery(sql, conn);
        int i=1;
        if (rs != null) {
            databaseList = new ArrayList<Database>();
            while (rs.next()) {
                Database database = new Database();
                String databaseName = rs.getString(1);
                database.setDataBaseName(databaseName);
                database.setId(dbServer.getId()+"-"+i);
                i++;
                DBServer dbServer1 =new DBServer();
                dbServer1.setId(dbServer.getId());
                dbServer1.setPort(dbServer.getPort());
                dbServer1.setPassword(dbServer.getPassword());
                dbServer1.setUserName(dbServer.getUserName());
                dbServer1.setTemplateDBName(databaseName);
                dbServer1.setIp(dbServer.getIp());
                dbServer1.setComment(dbServer.getComment());
                List<Table> tableList = getTables(database,dbServer1);
                database.setTableList(tableList);
                databaseList.add(database);
            }
        }
        if(rs != null)
        {
            try{
                rs.close() ;
            }catch(SQLException e){
                e.printStackTrace() ;
            }
        }
        if (conn != null) {
            conn.close();
        }
        return databaseList;
    }

    public List<Column> getTableColumns(String id,String codename, String ip, String port, String userName, String password,String dataBaseName) throws Exception {

        List<Column> columnList = null;

        JdbcUtilForPostgres1 jdbcUtilForPostgres1 = new JdbcUtilForPostgres1();
        Connection conn = jdbcUtilForPostgres1.getConn(ip, Integer.parseInt(port), userName, password, dataBaseName);

        String sql = "SELECT " +
                "col_description (A .attrelid, A .attnum) AS COMMENT, " +
                "format_type (A .atttypid, A .atttypmod) AS TYPE, " +
                "A .attname AS NAME, " +
                "A .attnotnull AS NOTNULL " +
                "FROM " +
                "pg_class AS C, " +
                "pg_attribute AS A " +
                "WHERE " +
                "C .relname = '" + codename + "' " +
                "AND A .attrelid = C .oid " +
                "AND A .attnum > 0";
        String sql1 = "SELECT  " +
                " pg_attribute.attname AS colname  " +
                "FROM  " +
                " pg_constraint  " +
                "INNER JOIN pg_class ON pg_constraint.conrelid = pg_class.oid  " +
                "INNER JOIN pg_attribute ON pg_attribute.attrelid = pg_class.oid  " +
                "AND pg_attribute.attnum = pg_constraint.conkey [ 1 ]  " +
                "INNER JOIN pg_type ON pg_type.oid = pg_attribute.atttypid  " +
                "WHERE  " +
                " pg_class.relname = '" + codename + "'  " +
                "AND pg_constraint.contype = 'p'  " +
                "AND pg_table_is_visible (pg_class.oid)";

        ResultSet rs = jdbcUtilForPostgres1.executeQuery(sql, conn);
        ResultSet rs1 = jdbcUtilForPostgres1.executeQuery(sql1, conn);
        if (rs != null ) {
            columnList = new ArrayList<Column>();
            while (rs.next()) {
                Column column = new Column();
                String comment = rs.getString(1);
                String type = rs.getString(2);
                String name = rs.getString(3);
                String notNull = rs.getString(4);
                column.setColumnName(name);
                column.setColumnType(type);
                column.setComment(comment);
                column.setNotNull("t".equalsIgnoreCase(notNull) ? true : false);
                columnList.add(column);
                if (rs1 != null) {
                    while (rs1.next()) {
                        String primaryKey = rs1.getString(1);//查询的是主键
                        column.setPrimaryKey(primaryKey);
                    }
                }
            }
        }
        if(rs != null)
        {   // 关闭记录集
            try{
                rs.close() ;
            }catch(SQLException e){
                e.printStackTrace() ;
            }
        }
        if(rs1 != null)
        {   // 关闭记录集
            try{
                rs1.close() ;
            }catch(SQLException e){
                e.printStackTrace() ;
            }
        }
        if (conn != null) {
            conn.close();
        }
        return columnList;
    }

    public List<ZtreeType>  getZtreeData(){
        List<DBServer> dbServer = ConfigData.DATABASE_LIST;
        List<ZtreeType> ztreeTypeList =new ArrayList<ZtreeType>();
        if (dbServer !=null && dbServer.size()>0) {
            for (DBServer db :dbServer){
                ZtreeType ztreeTypeDBServer =new ZtreeType();
                ztreeTypeDBServer.setId(db.getId());
                ztreeTypeDBServer.setpId("0");
                ztreeTypeDBServer.setName(db.getComment());
                ztreeTypeDBServer.setIsparent(true);
                ztreeTypeList.add(ztreeTypeDBServer);
                try {
                    List<Database> databases =getDatabases(db);
                    if(databases !=null && databases.size()>0){
                        for (Database database : databases) {
                            ZtreeType ztreeTypeDatabase =new ZtreeType();
                            ztreeTypeDatabase.setId(database.getId());
                            ztreeTypeDatabase.setpId(db.getId());
                            ztreeTypeDatabase.setName(database.getDataBaseName());
                            ztreeTypeDatabase.setIsparent(true);
                            ztreeTypeList.add(ztreeTypeDatabase);

                            List<Table> tableList = database.getTableList();
                            if (tableList !=null && tableList.size()>0){
                                for (Table table: tableList){
                                    ZtreeType ztreeTypeTable =new ZtreeType();
                                    ztreeTypeTable.setId(table.getId());
                                    ztreeTypeTable.setpId(database.getId());
                                    ztreeTypeTable.setName(table.getTableName());
                                    ztreeTypeTable.setIsparent(false);
                                    //新增zTree中的返回值,用户点击查询的参数
                                    ztreeTypeTable.setDbServerId(db.getId());
                                    ztreeTypeTable.setIp(db.getIp());
                                    ztreeTypeTable.setPort(db.getPort());
                                    ztreeTypeTable.setUserName(db.getUserName());
                                    ztreeTypeTable.setPassword(db.getPassword());
                                    ztreeTypeTable.setDataBaseName(database.getDataBaseName());
                                    ztreeTypeList.add(ztreeTypeTable);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log4jUtil.error(logger, "查询出现问题", e);
                }
            }
        }
        return ztreeTypeList;
    }

}