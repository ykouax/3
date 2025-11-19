package com.stx.web.info;

public class CourseInfo {
    private Integer couId;
    private  String couName;
    private  Integer couXf;


    public CourseInfo(Integer couId, String couName, Integer couXf) {
        this.couId = couId;
        this.couName = couName;
        this.couXf = couXf;
    }

    public CourseInfo() {

    }

    public Integer getCouId() {
        return couId;
    }

    public void setCouId(Integer couId) {
        this.couId = couId;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public Integer getCouXf() {
        return couXf;
    }

    public void setCouXf(Integer couXf) {
        this.couXf = couXf;
    }
}
