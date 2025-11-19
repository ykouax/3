package com.stx.java.com.shop.dao;

import com.stx.java.com.shop.entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDAO {
    
    public Admin getAdminByUsername(String username) throws SQLException {
        Admin admin = null;
        String sql = "SELECT * FROM t_admin WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
            }
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return admin;
    }
    
    public boolean validateAdmin(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM t_admin WHERE username = ? AND password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
    }
    
    // 添加创建管理员表的方法
    public void createAdminTableIfNotExists() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXISTS t_admin (" +
                "admin_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(50) NOT NULL)";
        
        String insertAdminSql = "INSERT IGNORE INTO t_admin (username, password) VALUES ('admin', 'admin123')";
        
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            
            // 创建表
            stmt.executeUpdate(createTableSql);
            
            // 插入默认管理员账户
            stmt.executeUpdate(insertAdminSql);
            
            System.out.println("Admin table created and default admin user inserted successfully!");
        } finally {
            DBUtil.close(stmt, conn);
        }
    }
}