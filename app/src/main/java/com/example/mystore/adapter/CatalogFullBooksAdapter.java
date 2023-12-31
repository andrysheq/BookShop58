package com.example.mystore.adapter;

import static com.example.mystore.MainActivity.fullBooksList;
import static com.example.mystore.model.Cart.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CatalogFullBooksAdapter extends RecyclerView.Adapter<com.example.mystore.adapter.CatalogFullBooksAdapter.CatalogFullBooksViewHolder> {

    Context context;
    ArrayList<Book> books;

    public CatalogFullBooksAdapter(Context context, ArrayList<Book> books){
        this.context = context;
        this.books = books;
    }
    @Override
    public com.example.mystore.adapter.CatalogFullBooksAdapter.CatalogFullBooksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View noveltyItems = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new CatalogFullBooksViewHolder(noveltyItems);
    }

    @Override
    public void onBindViewHolder(CatalogFullBooksAdapter.CatalogFullBooksViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.noveltyTitle.setText(books.get(position).getTitle());
        holder.noveltyPrice.setText(String.valueOf(books.get(position).getPrice()) + " ₽");
        holder.noveltyWriter.setText(books.get(position).getWriter());
        //int imageId = context.getResources().getIdentifier(books.get(position).getImg(),"drawable", context.getPackageName());
        //holder.noveltyImage.setImageResource(imageId);
        String url = books.get(position).getImg();
        Glide.with(context).load(url).into(holder.noveltyImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, BookPage.class);
//
//                ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation((Activity) context, new Pair<View,String>(holder.noveltyImage, "noveltyImage"));
//
//                intent.putExtra("title", books.get(position).getTitle());
//                intent.putExtra("writer", books.get(position).getWriter());
//                intent.putExtra("price", String.valueOf(books.get(position).getPrice()) + " ₽");
//                intent.putExtra("image", imageId);
//                intent.putExtra("bookId", books.get(position).getId());
//                context.startActivity(intent, ao.toBundle());

                Bundle bundle = new Bundle();
                bundle.putString("title", books.get(position).getTitle());
                bundle.putString("writer", books.get(position).getWriter());
                bundle.putString("price", String.valueOf(books.get(position).getPrice()) + " ₽");
                bundle.putString("description", books.get(position).getDescription());
                bundle.putString("image", url);
                bundle.putInt("bookId", books.get(position).getId());
                bundle.putString("destinationName","Catalog");
                Navigation.findNavController(view).navigate(R.id.action_menu_catalog_to_book_page,bundle);
            }
        });

        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                int id = books.get(position).getId();
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//                    startActivity(new Intent(getContext(), SignRegActivity.class));
//                    Toast.makeText(getContext(), R.string.not_sign_error, Toast.LENGTH_LONG).show();
                    for (BookInCart book : cart) {
                        if (book.getId() == id) {
                            Toast.makeText(context, R.string.cart_add_error, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    BookInCart newBook = new BookInCart(fullBooksList.get(id - 1).getId(), fullBooksList.get(id - 1).getAgeLimit(), fullBooksList.get(id - 1).getPrice(), fullBooksList.get(id - 1).getGenre(), fullBooksList.get(id - 1).getTitle(), fullBooksList.get(id - 1).getImg(), fullBooksList.get(id - 1).getWriter(), fullBooksList.get(id - 1).getCategory(), 1, fullBooksList.get(id - 1).getDescription());
                    cart.add(newBook);
                    Toast.makeText(context, R.string.cart_add, Toast.LENGTH_LONG).show();
                } else {
                    for (BookInCart book : cart) {
                        if (book.getId() == id) {
                            Toast.makeText(context, R.string.cart_add_error, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    BookInCart newBook = new BookInCart(fullBooksList.get(id - 1).getId(), fullBooksList.get(id - 1).getAgeLimit(), fullBooksList.get(id - 1).getPrice(), fullBooksList.get(id - 1).getGenre(), fullBooksList.get(id - 1).getTitle(), fullBooksList.get(id - 1).getImg(), fullBooksList.get(id - 1).getWriter(), fullBooksList.get(id - 1).getCategory(), 1, fullBooksList.get(id - 1).getDescription());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("title", newBook.getTitle());
                    map.put("id", newBook.getId());
                    map.put("genre", newBook.getGenre());
                    map.put("price", newBook.getPrice());
                    map.put("description", newBook.getDescription());
                    map.put("writer", newBook.getWriter());
                    map.put("img", newBook.getImg());
                    map.put("category", newBook.getCategory());
                    map.put("ageLimit", newBook.getAgeLimit());
                    map.put("amount", newBook.getAmount());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").child(String.valueOf(newBook.getId())).updateChildren(map);
                    Toast.makeText(context, R.string.cart_add, Toast.LENGTH_LONG).show();
                    //cartAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static final class CatalogFullBooksViewHolder extends RecyclerView.ViewHolder{

        TextView noveltyWriter;
        TextView noveltyTitle;
        TextView noveltyPrice;
        ImageView noveltyImage;
        ImageButton buyButton;

        public CatalogFullBooksViewHolder(View itemView) {
            super(itemView);

            noveltyImage = itemView.findViewById(R.id.bookImage);
            noveltyPrice = itemView.findViewById(R.id.bookPrice);
            noveltyWriter = itemView.findViewById(R.id.bookWriter);
            noveltyTitle = itemView.findViewById(R.id.bookTitle);
            buyButton = itemView.findViewById(R.id.search_list_buy);

        }
    }

    public void setFilteredList(ArrayList<Book> filteredBooks){
        this.books = filteredBooks;
        notifyDataSetChanged();
    }
}
