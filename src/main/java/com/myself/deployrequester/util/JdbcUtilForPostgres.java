package com.myself.deployrequester.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by QueRenJie on ${date}
 */
public class JdbcUtilForPostgres {
    /**
     * 创建数据库连接
     * @param ip
     * @param port
     * @param username
     * @param password
     * @param dbname
     * @return
     * @throws Exception
     */
    public Connection getConn(String ip, Integer port, String username, String password, String dbname) throws Exception {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            String connectUrl ="jdbc:postgresql://" + ip + ":" + port + "/" + dbname;
            conn = DriverManager.getConnection(connectUrl, username, password);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw e;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return conn;
    }

    /**
     * 执行一句sql
     * @param sql
     * @param conn
     * @param stmt
     * @return
     * @throws SQLException
     */
    public void executeSql(String sql, Connection conn, Statement stmt) throws SQLException {
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return;
    }
}
