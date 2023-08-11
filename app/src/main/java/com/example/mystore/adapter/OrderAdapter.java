package com.example.mystore.adapter;

import static com.example.mystore.MainActivity.fullBooksList;
import static com.example.mystore.model.Cart.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystore.R;
import com.example.mystore.model.Book;
import com.example.mystore.model.BookInCart;
import com.example.mystore.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<com.example.mystore.adapter.OrderAdapter.OrderViewHolder> {

    Context context;
    ArrayList<Order> orders;

    public OrderAdapter(Context context, ArrayList<Order> orders){
        this.context = context;
        this.orders = orders;
    }
    @Override
    public com.example.mystore.adapter.OrderAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View noveltyItems = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(noveltyItems);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderNumber.setText(orders.get(position).getNumber());
        holder.orderDate.setText(orders.get(position).getDate());
        holder.orderQuantity.setText(orders.get(position).getQuantity());
        holder.orderAmount.setText(orders.get(position).getAmount());
        holder.orderStatus.setText(orders.get(position).getStatus());
        switch(orders.get(position).getStatus()){
            case "Принят":
                //holder.orderStatus.setTextColor(Color.parseColor("#38f4ef"));
                break;
            case "Отменен":
                //holder.orderStatus.setTextColor(Color.parseColor("#dc143c"));
                break;
            case "В пути":
                //holder.orderStatus.setTextColor(Color.parseColor("#FFA500"));
                break;
            case "Завершен":
                //holder.orderStatus.setTextColor(Color.parseColor("#c8c8c8"));
                break;
        }

        holder.orderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("number", orders.get(position).getNumber());
                bundle.putString("date", orders.get(position).getDate());
                bundle.putString("quantity", orders.get(position).getQuantity());
                bundle.putString("amount", orders.get(position).getAmount());
                bundle.putString("status", orders.get(position).getStatus());
                //Navigation.findNavController(view).navigate(R.id.action_catalog_books_fragment_to_book_page,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static final class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView orderNumber;
        TextView orderDate;
        TextView orderQuantity;
        TextView orderAmount;
        TextView orderStatus;
        Button orderDetails;

        public OrderViewHolder(View itemView) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.order_number);
            orderDate = itemView.findViewById(R.id.order_date);
            orderQuantity = itemView.findViewById(R.id.order_quantity);
            orderAmount = itemView.findViewById(R.id.order_total_amount);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderDetails = itemView.findViewById(R.id.order_details);

        }
    }
}
