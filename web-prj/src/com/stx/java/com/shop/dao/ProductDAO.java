package com.stx.java.com.shop.dao;

import com.stx.java.com.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM t_product1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setImageUrl(rs.getString("image_url"));
                // 设置默认值，前端展示用
                product.setDescription("精选商品，品质保证");
                if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
                    product.setImageUrl("https://via.placeholder.com/200x200?text=" + product.getName());
                }
                products.add(product);
            }
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return products;
    }

    public Product getProductById(int id) throws SQLException {
        Product product = null;
        String sql = "SELECT * FROM t_product1 WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setImageUrl(rs.getString("image_url"));
                // 设置默认值，前端展示用
                product.setDescription("精选商品，品质保证");
                if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
                    product.setImageUrl("https://via.placeholder.com/200x200?text=" + product.getName());
                }
            }
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return product;
    }

    public List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM t_product1 WHERE product_name LIKE ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setImageUrl(rs.getString("image_url"));
                // 设置默认值，前端展示用
                product.setDescription("精选商品，品质保证");
                if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
                    product.setImageUrl("https://via.placeholder.com/200x200?text=" + product.getName());
                }
                products.add(product);
            }
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return products;
    }

    // 添加商品（手动指定商品ID）
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO t_product1 (product_id, product_name, price, stock, image_url) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, product.getId());  // 使用前端传来的商品ID
            stmt.setString(2, product.getName());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setString(5, product.getImageUrl());
            stmt.executeUpdate();
        } finally {
            DBUtil.close(stmt, conn);
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE t_product1 SET product_name = ?, price = ?, stock = ?, image_url = ? WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.setString(4, product.getImageUrl());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        } finally {
            DBUtil.close(stmt, conn);
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM t_product1 WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            DBUtil.close(stmt, conn);
        }
    }
}