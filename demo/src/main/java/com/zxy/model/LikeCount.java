package com.zxy.model;

public class LikeCount {
    private int a_id;
    private int cnt;

    public LikeCount() {
    }

    public LikeCount(int a_id, int cnt) {
        this.a_id = a_id;
        this.cnt = cnt;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
