package com.stx.java.com.shop.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stx.java.com.shop.dao.CartDAO;
import com.stx.java.com.shop.entity.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/cart")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 获取用户ID（这里简化处理，实际应该从session中获取）
            int userId = Integer.parseInt(request.getParameter("userId"));
            // 获取购物车列表
            List<Cart> carts = cartDAO.getCartByUserId(userId);
            // 转换为JSON并返回
            String json = objectMapper.writeValueAsString(carts);
            out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"服务器错误\"}");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 解析请求体
            CartRequest cartRequest = objectMapper.readValue(request.getReader(), CartRequest.class);
            
            // 打印调试信息
            System.out.println("接收到添加购物车请求 - 用户ID: " + cartRequest.getUserId() + 
                             ", 商品ID: " + cartRequest.getProductId() + 
                             ", 数量: " + cartRequest.getQuantity());
            
            // 添加到购物车
            cartDAO.addToCart(cartRequest.getUserId(), cartRequest.getProductId(), cartRequest.getQuantity());
            response.setStatus(HttpServletResponse.SC_OK);
            out.println("{\"message\": \"添加到购物车成功\"}");
        } catch (Exception e) {
            // 打印详细错误信息
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // 返回具体错误信息给前端
            String errorMsg = e.getMessage() != null ? e.getMessage() : "未知错误";
            out.println("{\"error\": \"服务器错误: " + errorMsg.replace("\"", "'") + "\"}");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 解析请求体
            CartUpdateRequest updateRequest = objectMapper.readValue(request.getReader(), CartUpdateRequest.class);
            // 更新购物车数量
            cartDAO.updateCartQuantity(updateRequest.getId(), updateRequest.getQuantity());
            out.println("{\"message\": \"购物车更新成功\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"服务器错误\"}");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 获取参数
            String idParam = request.getParameter("id");
            String userIdParam = request.getParameter("userId");
            String productIdParam = request.getParameter("productId");
            String clearAllParam = request.getParameter("clearAll");

            if ("true".equals(clearAllParam) && userIdParam != null) {
                // 清空指定用户的购物车
                int userId = Integer.parseInt(userIdParam);
                cartDAO.clearCartByUserId(userId);
                out.println("{\"message\": \"清空购物车成功\"}");
            } else if (idParam != null) {
                // 根据购物车ID删除
                int id = Integer.parseInt(idParam);
                cartDAO.removeFromCart(id);
                out.println("{\"message\": \"从购物车移除成功\"}");
            } else if (userIdParam != null && productIdParam != null) {
                // 根据用户ID和商品ID删除
                int userId = Integer.parseInt(userIdParam);
                int productId = Integer.parseInt(productIdParam);
                cartDAO.removeFromCartByUserAndProduct(userId, productId);
                out.println("{\"message\": \"从购物车移除成功\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"服务器错误\"}");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // 内部类用于接收添加购物车请求
    static class CartRequest {
        private int userId;
        private int productId;
        private int quantity;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // 内部类用于接收更新购物车请求
    static class CartUpdateRequest {
        private int id;
        private int quantity;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}