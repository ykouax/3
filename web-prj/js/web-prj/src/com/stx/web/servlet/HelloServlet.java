package com.stx.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stx.web.info.CourseInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
*   1.要提供一个网页访问的路径
*   2.要继承自httlservlet类
*   3.重写httpservlet类的http方法，类如：get，post
* */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private ObjectMapper ObjectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setContentType("application/json;charset=utf-8");
        CourseInfo info =
                new CourseInfo(1,"JavaEE企业级开发",3);
        ObjectMapper.writeValue(resp.getOutputStream(),info);
    }
}
