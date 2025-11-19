package com.stx.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stx.web.dao.CourseDAO;
import com.stx.web.info.CourseInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private  CourseDAO dao = new CourseDAO();
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
         * 1.解析前端传输过来的ID
         * 2.通过DAO对象查询数据
         * 3.将第二步得到的对象序列化成JSON字符串，并返回给前端。
         * */
        resp.setHeader("Access-Control-Allow-Origin","*");
        String id = req.getParameter("id");
        int couId = Integer.parseInt(id);
        CourseInfo info = dao.findById(couId);
        mapper.writeValue(resp.getOutputStream(),info);

    }
}
