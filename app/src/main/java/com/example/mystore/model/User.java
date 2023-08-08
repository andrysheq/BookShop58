package com.example.mystore.model;

public class User {

    private String login, phone, email;

    public User(){}

    public User(String login, String phone, String email) {
        this.login = login;
        //this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

//    public String getPassword() {
//        return password;
//    }

    public String getPhone() {
        return phone;
    }

    public void setLogin(String login) {
        this.login = login;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
