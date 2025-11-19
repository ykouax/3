package com.stx.java.com.shop.dao;

import com.stx.java.com.shop.entity.Cart;
import com.stx.java.com.shop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private ProductDAO productDAO = new ProductDAO();

    public List<Cart> getCartByUserId(int userId) throws SQLException {
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT * FROM t_cart WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
                // 获取关联的商品信息
                Product product = productDAO.getProductById(cart.getProductId());
                cart.setProduct(product);
                carts.add(cart);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return carts;
    }

    // 添加商品到购物车（智能处理：如果已存在则增加数量，否则新增）
    public void addToCart(int userId, int productId, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            
            // 先检查购物车中是否已有该商品
            Cart existingCart = getCartByUserAndProduct(userId, productId);
            
            if (existingCart != null) {
                // 如果已存在，则更新数量（累加）
                String updateSql = "UPDATE t_cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, quantity);
                pstmt.setInt(2, userId);
                pstmt.setInt(3, productId);
                pstmt.executeUpdate();
            } else {
                // 如果不存在，则新增
                String insertSql = "INSERT INTO t_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, productId);
                pstmt.setInt(3, quantity);
                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("添加到购物车失败: " + ex.getMessage());
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    public void updateCartQuantity(int id, int quantity) throws SQLException {
        String sql = "UPDATE t_cart SET quantity = ? WHERE cart_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    public void removeFromCart(int id) throws SQLException {
        String sql = "DELETE FROM t_cart WHERE cart_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    public void removeFromCartByUserAndProduct(int userId, int productId) throws SQLException {
        String sql = "DELETE FROM t_cart WHERE user_id = ? AND product_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    public Cart getCartByUserAndProduct(int userId, int productId) throws SQLException {
        Cart cart = null;
        String sql = "SELECT * FROM t_cart WHERE user_id = ? AND product_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cart = new Cart();
                cart.setId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return cart;
    }

    // 清空指定用户的购物车
    public void clearCartByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM t_cart WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("清空购物车失败: " + ex.getMessage());
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }
}