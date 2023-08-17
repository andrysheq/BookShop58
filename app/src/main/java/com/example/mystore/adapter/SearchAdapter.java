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

import androidx.annotation.NonNull;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<Book> books;
    ImageButton buttonBuy;

    public SearchAdapter(Context context, ArrayList<Book> books){
        this.context = context;
        this.books = books;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noveltyItems = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(noveltyItems);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bookTitle.setText(books.get(position).getTitle());
        holder.bookPrice.setText(String.valueOf(books.get(position).getPrice()) + " ₽");
        holder.bookWriter.setText(books.get(position).getWriter());
        //int imageId = context.getResources().getIdentifier(novelties.get(position).getImg(),"drawable", context.getPackageName());
        //holder.noveltyImage.setImageResource(imageId);
        String url = books.get(position).getImg();
        Glide.with(context).load(url).into(holder.bookImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("title", books.get(position).getTitle());
                bundle.putString("writer", books.get(position).getWriter());
                bundle.putString("price", String.valueOf(books.get(position).getPrice()) + " ₽");
                bundle.putString("image", url);
                bundle.putString("description", books.get(position).getDescription());
                bundle.putInt("bookId", books.get(position).getId());
                //bundle.putString("destinationName","Search");
                Navigation.findNavController(view).navigate(R.id.action_search_fragment_to_book_page,bundle);
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
                    map.put("description", newBook.getDescription());
                    map.put("price", newBook.getPrice());
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

    public static final class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView bookWriter;
        TextView bookTitle;
        TextView bookPrice;
        ImageView bookImage;
        ImageButton buyButton;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.bookImage);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            bookWriter = itemView.findViewById(R.id.bookWriter);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            buyButton = itemView.findViewById(R.id.search_list_buy);

        }
    }

    public void setFilteredList(ArrayList<Book> filteredBooks){
        this.books = filteredBooks;
        notifyDataSetChanged();
    }
}

