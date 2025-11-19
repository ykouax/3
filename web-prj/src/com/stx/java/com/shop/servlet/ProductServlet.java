package com.stx.java.com.shop.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stx.java.com.shop.dao.ProductDAO;
import com.stx.java.com.shop.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/api/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 处理搜索请求
            String keyword = request.getParameter("keyword");
            List<Product> products;
            if (keyword != null && !keyword.isEmpty()) {
                products = productDAO.searchProducts(keyword);
            } else {
                // 获取所有商品
                products = productDAO.getAllProducts();
            }
            // 转换为JSON并返回
            String json = objectMapper.writeValueAsString(products);
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
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 解析请求体
            Product product = objectMapper.readValue(request.getReader(), Product.class);
            
            // 打印调试信息
            System.out.println("接收到添加商品请求 - ID: " + product.getId() + ", 名称: " + product.getName());
            
            // 添加商品
            productDAO.addProduct(product);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.println("{\"message\": \"商品添加成功\"}");
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
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 解析请求体
            Product product = objectMapper.readValue(request.getReader(), Product.class);
            // 更新商品
            productDAO.updateProduct(product);
            out.println("{\"message\": \"商品更新成功\"}");
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
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 获取商品ID
            int id = Integer.parseInt(request.getParameter("id"));
            // 删除商品
            productDAO.deleteProduct(id);
            out.println("{\"message\": \"商品删除成功\"}");
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
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}