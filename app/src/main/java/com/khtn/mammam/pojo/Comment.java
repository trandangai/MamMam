package com.khtn.mammam.pojo;


public class Comment {
    private String userName;
    private String userComment;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    @Override
    public String toString() {
        return "\n[ "+userName+" ]\n"+userComment;
    }

    public Comment(String userName, String userComment) {
        this.userName = userName;
        this.userComment = userComment;
    }
}
