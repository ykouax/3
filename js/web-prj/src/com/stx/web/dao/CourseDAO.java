package com.stx.web.dao;

import com.stx.web.info.CourseInfo;
import com.stx.web.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseDAO {
    public CourseInfo findById(int couId){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CourseInfo info = null;
        try{
           conn = DBUtils.getConnection();
           String sql = "select * from t_course where cou_id = ?";

           pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,couId);
           rs = pstmt.executeQuery();
           if(rs.next()){
               int id = rs.getInt("cou_id");
               String couName = rs.getString("cou_name");
               int couXf = rs.getInt("cou_xf");
               info=new CourseInfo(id,couName,couXf);
           }
        } catch (Exception ex) {
           ex.printStackTrace();
        }finally {
            DBUtils.close(rs,pstmt,conn);
        }
        return info;
    }
}
