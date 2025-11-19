package com.stx.java.com.shop.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stx.java.com.shop.dao.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/admins")
public class AdminServlet extends HttpServlet {
    private AdminDAO adminDAO = new AdminDAO();
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化时确保管理员表和默认数据存在
        try {
            adminDAO.createAdminTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize admin table: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // 获取请求参数
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // 验证管理员身份
            if (adminDAO.validateAdmin(username, password)) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"登录成功\", \"success\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.println("{\"message\": \"用户名或密码错误\", \"success\": false}");
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
}