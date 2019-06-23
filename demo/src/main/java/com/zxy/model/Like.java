package com.zxy.model;

public class Like {

    private int userId;
    private int aId;
    private Integer value;

    public Like() {
    }

    public Like(int userId, int aId, int value) {
        this.userId = userId;
        this.aId = aId;
        this.value = value;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
