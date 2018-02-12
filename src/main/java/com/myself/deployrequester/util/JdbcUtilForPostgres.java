package com.myself.deployrequester.util;

import java.sql.*;

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
     * @return
     * @throws SQLException
     */
    public void executeSql(String sql, Connection conn) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return;
    }

    /**
     * 获取sql结果集合
     * @param sql
     * @param conn
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
