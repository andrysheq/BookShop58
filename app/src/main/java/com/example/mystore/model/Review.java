package com.example.mystore.model;

public class Review {

    private String grade, text, user,date, idOfItem, header;

    public Review(){}

    public Review(String grade, String text, String user, String date, String idOfItem, String header) {
        this.grade = grade;
        this.text = text;
        this.date = date;
        this.user = user;
        this.idOfItem = idOfItem;
        this.header = header;
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

    public String getIdOfItem() {
        return idOfItem;
    }

    public void setIdOfItem(String idOfItem) {
        this.idOfItem = idOfItem;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
