package com.khtn.mammam.pojo;

import java.io.Serializable;

public class Rating implements Serializable{
    private int numOfUser;
    private int score;

    public int getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(int numOfUser) {
        this.numOfUser = numOfUser;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
