package com.khtn.mammam.pojo;

/**
 * Created by Sayuri Kurata on 2017/01/31.
 */

public class Restaurant {

    private String restAddr;
    private String restImg;
    private String restName;
    private String restTopComment;
    private String restTopCommenter;
    private String Latitude;
    private String Longitude;

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestImg() {
        return restImg;
    }

    public void setRestImg(String restImg) {
        this.restImg = restImg;
    }

    public String getRestAddr() {
        return restAddr;
    }

    public void setRestAddr(String restAddr) {
        this.restAddr = restAddr;
    }

    public String getRestTopCommenter() {
        return restTopCommenter;
    }

    public void setRestTopCommenter(String restTopCommenter) {
        this.restTopCommenter = restTopCommenter;
    }

    public String getRestTopComment() {
        return restTopComment;
    }

    public void setRestTopComment(String restTopComment) {
        this.restTopComment = restTopComment;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
