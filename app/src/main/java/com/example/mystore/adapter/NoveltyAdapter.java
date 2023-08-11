package com.example.mystore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.example.mystore.model.Book;

import java.util.ArrayList;

public class NoveltyAdapter extends RecyclerView.Adapter<NoveltyAdapter.NoveltyViewHolder> {

    Context context;
    ArrayList<Book> novelties;

    public NoveltyAdapter(Context context, ArrayList<Book> novelties){
        this.context = context;
        this.novelties = novelties;
    }
    @NonNull
    @Override
    public NoveltyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noveltyItems = LayoutInflater.from(context).inflate(R.layout.novelty_item, parent, false);
        return new NoveltyViewHolder(noveltyItems);
    }

    @Override
    public void onBindViewHolder(@NonNull NoveltyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.noveltyTitle.setText(novelties.get(position).getTitle());
        holder.noveltyPrice.setText(String.valueOf(novelties.get(position).getPrice()) + " ₽");
        holder.noveltyWriter.setText(novelties.get(position).getWriter());
        //int imageId = context.getResources().getIdentifier(novelties.get(position).getImg(),"drawable", context.getPackageName());
        //holder.noveltyImage.setImageResource(imageId);
        String url = novelties.get(position).getImg();
        Glide.with(context).load(url).into(holder.noveltyImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", novelties.get(position).getTitle());
                bundle.putString("writer", novelties.get(position).getWriter());
                bundle.putString("price", String.valueOf(novelties.get(position).getPrice()) + " ₽");
                bundle.putString("image", url);
                bundle.putInt("bookId", novelties.get(position).getId());
                bundle.putString("destinationName","Home");
                Navigation.findNavController(view).navigate(R.id.action_menu_home_to_book_page,bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return novelties.size();
    }

    public static final class NoveltyViewHolder extends RecyclerView.ViewHolder{

        TextView noveltyWriter;
        TextView noveltyTitle;
        TextView noveltyPrice;
        ImageView noveltyImage;

        public NoveltyViewHolder(@NonNull View itemView) {
            super(itemView);

            noveltyImage = itemView.findViewById(R.id.noveltyImage);
            noveltyPrice = itemView.findViewById(R.id.noveltyPrice);
            noveltyWriter = itemView.findViewById(R.id.noveltyWriter);
            noveltyTitle = itemView.findViewById(R.id.noveltyTitle);

        }
    }
}
