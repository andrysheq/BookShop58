package com.example.mystore.model;

public class Order {


    private String number, quantity, date, amount, status;

    public Order(){}

    public Order(String number, String quantity, String date, String amount, String status) {
        this.number = number;
        this.quantity = quantity;
        this.date = date;
        this.amount = amount;
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
