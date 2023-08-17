package com.example.mystore.model;

public class Book {
    private int id, ageLimit, price, category;
    private String img,genre,title,writer,description;

    public Book(int id, int ageLimit, int price, String genre, String title, String img, String writer, int category, String description){
        this.id = id;
        this.ageLimit = ageLimit;
        this.price = price;
        this.genre = genre;
        this.title = title;
        this.img = img;
        this.writer = writer;
        this.category = category;
        this.description = description;
    }

    public Book(Book book) {
        this.id = book.getId();
        this.ageLimit = book.getAgeLimit();
        this.price = book.getPrice();
        this.genre = book.getGenre();
        this.title = book.getTitle();
        this.img = book.getImg();
        this.writer = book.getWriter();
        this.category = book.getCategory();
        this.description = book.getDescription();
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWriter(String writer){
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
