package com.vedeng.mjx.Db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInit {
    // 查询某条数据，如有，修改这条数据为目标数据，如没有插入这个目标数据
    // sql_1 查询用语句；sql_2 修改用语句；sql_3 插入用语句；
    public static void initDB(String sql_1, String sql_2, String sql_3) throws SQLException {
        Connection conn = DbJoin.getConnection();
        ResultSet rs = null;
        int count = 0;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql_1);
        if (rs.next()) {
            count = rs.getInt(1);
        }
        if (count != 0) {
            stmt.executeUpdate(sql_2);
        } else {
            stmt.executeUpdate(sql_3);
        }
        rs.close();
        stmt.close();
    }

    // 删除某条数据库数据所用
    // sql 删除语句；
    public static void deleDB(String sql) throws SQLException {
        Connection conn = DbJoin.getConnection();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.executeUpdate(sql);
        stmt.close();
    }

    // 插入一条数据到数据库
    // sql 增加语句,i为插入记录数
    public static void addDB(String sql, int i) throws SQLException {
        Connection conn = DbJoin.getConnection();
        int s = 0;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        for (s = 0; s < i; s = s + 1) {
            stmt.executeUpdate(sql);

        }
        stmt.close();
    }

    // 查询某数据库中的记录数
    public static int excuteQueryDB(String sql) throws SQLException {
        Connection conn = DbJoin.getConnection();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            int count = res.getInt(1);
            res.close();
            stmt.close();
            return count;
        }
        res.close();
        stmt.close();
        return 0;
    }

    // 获取数据库中某表的字段int值
    public static int getValue(String tableName, String columnName, String condition) throws SQLException {
        String sql = "";
        if (tableName == null || tableName.equals("")) {
            return 0;
        }
        if (columnName == null || columnName.equals("")) {
            return 0;
        }
        if (condition != null) {
            sql = "select " + columnName + " from " + tableName + " " + "where" + condition + "";
        } else {
            sql = "select " + columnName + " from " + tableName + "";
        }

        Connection conn = DbJoin.getConnection();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            if (columnName.equals("*")) {
                res.close();
                stmt.close();
                return 0;
            }
            int value = res.getInt(columnName);
            res.close();
            stmt.close();
            return value;
        }
        res.close();
        stmt.close();
        return 0;
    }

    // 判断数据库表中是否存在某条记录
    public static int IsHasRecord(String sql) throws SQLException {
        Connection conn = DbJoin.getConnection();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            res.close();
            stmt.close();
            return 1;
        }
        res.close();
        stmt.close();
        return 0;
    }

    // 批处理sql语句
    public static void executeBatch(String[] sql) throws SQLException {

        Connection conn = DbJoin.getConnection();
        conn.setAutoCommit(false);
        Statement smt = conn.createStatement();
        for (int i = 0; i < sql.length; i++) {

            smt.addBatch(sql[i]);
        }
        smt.executeBatch();
        conn.commit();
        smt.close();
    }

    public static int[] executeQueuery(String[] sql) throws SQLException {
        int[] returnData = new int[sql.length];
        Connection conn = DbJoin.getConnection();

        conn.setAutoCommit(false);
        Statement smt = conn.createStatement();
        ResultSet res = null;

        for (int i = 0; i < sql.length; i++) {
            res = smt.executeQuery(sql[i]);
            if (res.next()) {
                returnData[i] = res.getInt(1);
            }
        }
        conn.commit();
        smt.close();
        res.close();
        return returnData;
    }

    public static void main(String args[]) {


    }

}