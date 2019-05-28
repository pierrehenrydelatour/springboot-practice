package com.zxy.demo.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class User {
    private int id;
    private String email;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken(){
        String token = "";
        token = JWT.create().withAudience(this.getEmail()).sign(Algorithm.HMAC256(this.getPassword()));
        return token;
    }
}
