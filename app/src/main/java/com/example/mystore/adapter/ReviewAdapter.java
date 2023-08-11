package com.example.mystore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.example.mystore.model.Order;
import com.example.mystore.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<com.example.mystore.adapter.ReviewAdapter.ReviewViewHolder> {

    Context context;
    ArrayList<Review> reviews;

    public ReviewAdapter(Context context, ArrayList<Review> reviews){
        this.context = context;
        this.reviews = reviews;
    }
    @Override
    public com.example.mystore.adapter.ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reviewItems = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(reviewItems);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.loginUser.setText(reviews.get(position).getUser());
        holder.reviewDate.setText(reviews.get(position).getDate());
        holder.reviewGrade.setText(reviews.get(position).getGrade());
        holder.reviewText.setText(reviews.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static final class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView loginUser;
        TextView reviewDate;
        TextView reviewGrade;
        TextView reviewText;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            loginUser = itemView.findViewById(R.id.login_reviewer);
            reviewDate = itemView.findViewById(R.id.review_date);
            reviewGrade = itemView.findViewById(R.id.review_grade);
            reviewText = itemView.findViewById(R.id.review_text);

        }
    }
}

