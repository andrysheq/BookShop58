package com.example.mystore.model;

public class Review {

    private String grade, text, user,date;

    public Review(){}

    public Review(String grade, String text, String user, String date) {
        this.grade = grade;
        this.text = text;
        this.date = date;
        this.user = user;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
