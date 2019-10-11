package com.vedeng.mjx.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbJoin {
    private static Connection m_conn = null;
    public static int i = 0;

    public static Connection getConnection() throws SQLException {
        if (m_conn == null) {
            System.out.println("新建连接的次数：" + i++);
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String connstr = "jdbc:mysql://192.168.2.54:3306/QA_GOMANAGER_VEDENG_COM?characterEncoding=utf-8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true";

            m_conn = DriverManager.getConnection(connstr, "qauser", "qa@#$%6789");
        }
        return m_conn;
    }

    public void close() {
        if (m_conn != null) {
            try {
                m_conn.close();
                m_conn = null;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}