package com.example.mystore;

import static com.example.mystore.CartFragment.cartAdapter;
import static com.example.mystore.HomeFragment.noveltyAdapter;
import static com.example.mystore.Order.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.mystore.databinding.ActivityMainBinding;
import com.example.mystore.model.BookInCart;
import com.example.mystore.model.Category;
import com.example.mystore.model.Book;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<Book> fullBooksList = new ArrayList<>();
    static ArrayList<Book> novelties = new ArrayList<>();
    static ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_home, R.id.menu_cart, R.id.menu_catalog,R.id.menu_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //getSupportActionBar().hide();

        DatabaseReference mDatabaseBooks = FirebaseDatabase.getInstance().getReference().child("Books");
        DatabaseReference mDatabaseNovelties = FirebaseDatabase.getInstance().getReference().child("Novelties");
        DatabaseReference mDatabaseCategories = FirebaseDatabase.getInstance().getReference().child("Categories");
        mDatabaseBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (books.size() > 0) {
                    books.clear();
                }
                if (fullBooksList.size() > 0) {
                    fullBooksList.clear();
                }
                for(DataSnapshot ds : snapshot.getChildren()){
                    String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
                    String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
                    Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
                    String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
                    Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
                    Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
                    String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
                    Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category));
                    books.add(book);
                }
                fullBooksList.addAll(books);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference mDatabaseCart = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart");
            mDatabaseCart.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (cart.size() > 0) {
                        cart.clear();
                    }
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
                        String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
                        String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
                        Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
                        String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
                        Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
                        Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
                        String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
                        Long amount = (Long) ((HashMap<String, Object>) ds.getValue()).get("amount");
                        BookInCart book = new BookInCart(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category), Math.toIntExact(amount));
                        cart.add(book);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        mDatabaseNovelties.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (novelties.size() > 0) {
                    novelties.clear();
                }
                for(DataSnapshot ds : snapshot.getChildren()){
                    String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
                    String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
                    Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
                    String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
                    Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
                    Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
                    String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
                    Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category));
                    novelties.add(book);
                }

                books.addAll(novelties);
                fullBooksList.addAll(novelties);

                noveltyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (categories.size() > 0) {
                    categories.clear();
                }
                for(DataSnapshot ds : snapshot.getChildren()){
                    String name = (String) ((HashMap<String, Object>) ds.getValue()).get("name");
                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
                    Category category = new Category(Math.toIntExact(id), name);
                    categories.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openSignRegActivity(){
        startActivity(new Intent(this,SignRegActivity.class));
        finish();
    }
}