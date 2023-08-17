package com.example.mystore.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystore.CartFragment;
import com.example.mystore.R;
import com.example.mystore.model.BookInCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<BookInCart> order;

    public CartAdapter(Context context, ArrayList<BookInCart> order){
        this.context = context;
        this.order = order;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noveltyItems = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(noveltyItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.noveltyTitle.setText(order.get(position).getTitle());
        holder.noveltyPrice.setText(String.valueOf(order.get(position).getPrice()) + " ₽");
        holder.noveltyWriter.setText(order.get(position).getWriter());
        holder.bookAmount.setText(String.valueOf(order.get(position).getAmount()));
        //int imageId = context.getResources().getIdentifier(order.get(position).getImg(),"drawable", context.getPackageName());
        //holder.noveltyImage.setImageResource(imageId);
        String url = order.get(position).getImg();
        Glide.with(context).load(url).into(holder.noveltyImage);
        CartFragment.finalPrice.setText(String.valueOf(getItemsPrice()) + " ₽");
        CartFragment.amountOfItems.setText(String.valueOf(getItemsAmount()));

        holder.increaseBtn.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                order.get(position).increaseAmount();
                notifyDataSetChanged();
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    order.remove(order.get(position));
                    CartFragment.finalPrice.setText(R.string.cart_check_null);
                    CartFragment.amountOfItems.setText(R.string.cart_amount_null);
                    notifyDataSetChanged();
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").child(String.valueOf(order.get(position).getId())).removeValue();
                    order.remove(order.get(position));
                    CartFragment.finalPrice.setText(R.string.cart_check_null);
                    CartFragment.amountOfItems.setText(R.string.cart_amount_null);
                    notifyDataSetChanged();
                }
            }
        });

        holder.decreaseBtn.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if(order.get(position).getAmount()<2){
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        order.remove(order.get(position));
                        CartFragment.finalPrice.setText(R.string.cart_check_null);
                        CartFragment.amountOfItems.setText(R.string.cart_amount_null);
                        notifyDataSetChanged();
                    } else {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").child(String.valueOf(order.get(position).getId())).removeValue();
                        order.remove(order.get(position));
                        CartFragment.finalPrice.setText(R.string.cart_check_null);
                        CartFragment.amountOfItems.setText(R.string.cart_amount_null);
                        notifyDataSetChanged();
                    }
                }else {
                    order.get(position).decreaseAmount();
                    notifyDataSetChanged();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("title", order.get(position).getTitle());
                bundle.putString("writer", order.get(position).getWriter());
                bundle.putString("description", order.get(position).getDescription());
                bundle.putString("price", String.valueOf(order.get(position).getPrice()) + " ₽");
                bundle.putString("image", url);
                bundle.putInt("bookId", order.get(position).getId());
                Navigation.findNavController(view).navigate(R.id.action_menu_cart_to_book_page,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public int getItemsPrice(){
        int res= order.stream().mapToInt(o1 -> (int)o1.getAmount() * (int)o1.getPrice()).sum();
        return res;
    }

    public int getItemsAmount(){
        int res= order.stream().mapToInt(BookInCart::getAmount).sum();
        return res;
    }

    public static final class CartViewHolder extends RecyclerView.ViewHolder{

        TextView noveltyWriter;
        TextView noveltyTitle;
        TextView noveltyPrice;
        ImageView noveltyImage;
        TextView bookAmount;
        ImageButton increaseBtn;
        ImageButton decreaseBtn;
        ImageButton deleteBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            increaseBtn = itemView.findViewById(R.id.plusButton);
            decreaseBtn = itemView.findViewById(R.id.minusButton);
            noveltyImage = itemView.findViewById(R.id.noveltyImage);
            bookAmount = itemView.findViewById(R.id.bookAmount);
            noveltyPrice = itemView.findViewById(R.id.noveltyPrice);
            noveltyWriter = itemView.findViewById(R.id.noveltyWriter);
            noveltyTitle = itemView.findViewById(R.id.noveltyTitle);
            deleteBtn = itemView.findViewById(R.id.btn_delete_from_cart);
        }
    }
}

