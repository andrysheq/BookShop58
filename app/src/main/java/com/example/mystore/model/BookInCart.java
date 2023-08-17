package com.example.mystore.model;


import android.widget.Toast;

import com.example.mystore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BookInCart extends Book{
    private int amount;
    public BookInCart(int id, int ageLimit, int price, String genre, String title, String img, String writer, int category,int amount,String description) {
        super(id, ageLimit, price, genre, title, img, writer, category,description);
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void increaseAmount(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            this.amount++;
        } else {
            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").child(String.valueOf(getId())).child("amount").setValue(++this.amount);
        }
    }

    public void decreaseAmount(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            this.amount--;
        }else {
            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").child(String.valueOf(getId())).child("amount").setValue(--this.amount);
        }
    }
}
