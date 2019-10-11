package com.vedeng.mjx.Db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.Assert;

public class DbCenter {
    // 根据SQL查询某一列数据并返回
    public static String executeQuerycol(String sql, String col) throws SQLException {
        Connection conn = DbJoin.getConnection();
        String returncol = null;
        ResultSet rs = null;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql);

        if (rs.next()) {

            returncol = rs.getString(col);

        }
        rs.close();
        stmt.close();
        return returncol;

    }

    // 根据SQL查询多条记录的某一列数据并返回，
    public static String[] executeQuerycolList(String table_name, String condition, String col) throws Exception {

        if (condition == null || condition.isEmpty()) {

            throw new Exception("该方法必须传入条件condition");
        }
        int count = getCount(table_name, condition);

        Connection conn = DbJoin.getConnection();

        ResultSet rs = null;
        String sql = null;
        if (condition != null && !condition.equals("")) {

            sql = "select T.*  from " + table_name + "  T  where " + condition + "";

        }
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        rs = stmt.executeQuery(sql);

        String[] returncol = new String[count];

        int i = 0;
        while (rs.next()) {

            returncol[i] = rs.getString(col);
            i++;

        }
        rs.close();
        stmt.close();
        return returncol;

    }

    public static int executeQuery(String sql) throws SQLException {
        Connection conn = DbJoin.getConnection();
        ResultSet rs = null;
        int count = 0;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        return count;
    }

    // 查询某一特殊列的数据
    // table_name要查询的表名，condition查询条件语句，i获取第二列数据
    public static String[] QueryValue(String table_name, String condition, int i) throws SQLException {
        String sql_1 = "select count(*) from " + table_name + " where " + condition;
        String sql_2 = "select * from " + table_name + " where " + condition;
        Connection conn = DbJoin.getConnection();
        ResultSet rs = null;
        String newValue[];

        int count = 0;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql_1);
        if (rs.next()) {
            count = rs.getInt(1);
        }

        newValue = new String[count];

        rs = stmt.executeQuery(sql_2);
        int n = 0;
        while (rs.next()) {
            newValue[n] = rs.getString(i);
            n = n + 1;
        }
        rs.close();
        stmt.close();
        return newValue;
    }

    // 查询最大某列列值
    public static int QueryValue(String sql) throws SQLException {
        Connection conn = DbJoin.getConnection();
        ResultSet rs = null;
        int count = 0;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        return count;
    }

    // 查询数据库中某表的某条记录,返回一个结果集，取结果集的方法是
    /*
     * while(res.next()){ res.get(列名或列索引号); }
     * 一般情况下这种查询返回的结果集中只有一条记录，所以用下面的方法取就可以了： if(res.next()){ res.get(列名或列索引号); }
     */

    public static ResultSet getRecordValue(String table_name, String condition) throws SQLException {
        String sql = null;
        if (condition != null && !condition.equals("")) {
            sql = "select T.*  from " + table_name + "  T  where " + condition + "";
        } else {
            sql = "select T.* from " + table_name + "  T";
        }

        Connection con = null;
        Statement stm = null;
        ResultSet res = null;
        con = DbJoin.getConnection();

        stm = con.createStatement();

        res = stm.executeQuery(sql);

        return res;
    }

    // 获取记录条数,单条
    public static int getCount(String table, String condition) throws SQLException {
        String sql = null;
        if (condition != null && !condition.equals("")) {

            sql = "select count(*)   from " + table + "  T  where " + condition + "";
        } else {
            sql = "select count(*)  from " + table + "  T";
        }

        int count = executeQuery(sql);

        return count;
    }

    // 获取记录条数，多条
    public static int[] getCounts(String table[], String condition[]) throws SQLException {
        int count[] = new int[table.length];
        for (int i = 0; i < table.length; i++) {
            if (condition[i] == null || condition[i].length() == 0) {
                condition[i] = "";
            }
            String sql = "select count(*) from " + table[i] + condition[i];
            count[i] = executeQuery(sql);
        }
        return count;
    }

    public static void equalsResultSet(String tableName, String condition, String value[]) throws SQLException {
        ResultSet res = DbCenter.getRecordValue(tableName, condition);
        int i = 0;
        if (res.next()) {
            for (; i < value.length; i++) {
                Assert.assertEquals("" + tableName + "表的第" + i + "个字段不对：", value[i], res.getString(i));
            }
        }
    }

}
