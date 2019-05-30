package com.zxy.demo.model;

public class Answer {

    private int aId;
    private int qId;
    private int userId;
    private int like;
    String text;

    public Answer() {

    }

    public Answer(int aId, int qId, int userId, String text) {
        this.aId = aId;
        this.qId = qId;
        this.userId = userId;
        this.text = text;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
