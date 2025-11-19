package com.stx.web.util;

import java.sql.*;

public class DBUtils {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://47.108.131.32:3306/db_2022210416","2022210416","17691015296");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;

    }

    // DML操作 释放资源
    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }

    // DQL操作 释放资源
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean executeDML( String sql) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            result = pstmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt, conn);
        }
        return result;
    }
}

