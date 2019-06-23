package com.zxy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "mydb",type = "questions")
public class Question implements Serializable {
    @Id
    private int id;
    private int userId;
    private String text;
    private String title;
    public Question() {

    }

    public Question(int id, int userId, String text, String title) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
