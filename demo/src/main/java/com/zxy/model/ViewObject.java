package com.zxy.model;

public class ViewObject {
    private User user;
    private Question question;
    private Answer answer;
    private int likes;
    private int likeStatus;

    public ViewObject() {
    }

    public ViewObject(User user, Question question, Answer answer, int likes) {
        this.user = user;
        this.question = question;
        this.answer = answer;
        this.likes = likes;
    }

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
