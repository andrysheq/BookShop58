package com.example.mystore;

import static com.example.mystore.HomeFragment.noveltyAdapter;
import static com.example.mystore.model.Cart.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.mystore.databinding.ActivityMainBinding;
import com.example.mystore.model.BookInCart;
import com.example.mystore.model.Category;
import com.example.mystore.model.Book;
import com.example.mystore.model.Order;
import com.example.mystore.model.Review;
import com.example.mystore.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();
    static ArrayList<Review> reviews = new ArrayList<>();
    public static ArrayList<Review> fullReviewsList = new ArrayList<>();
    public static ArrayList<Book> fullBooksList = new ArrayList<>();
    static ArrayList<Book> novelties = new ArrayList<>();
    static ArrayList<Category> categories = new ArrayList<>();
    public static User currentUser;

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

//        DatabaseReference mDatabaseBooks = FirebaseDatabase.getInstance().getReference().child("Books");
//        //DatabaseReference mDatabaseNovelties = FirebaseDatabase.getInstance().getReference().child("Novelties");
//
//        mDatabaseBooks.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (books.size() > 0) {
//                    books.clear();
//                }
//                if (fullBooksList.size() > 0) {
//                    fullBooksList.clear();
//                }
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
//                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
//                    String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
//                    Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
//                    String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
//                    String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
//                    Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
//                    Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
//                    String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
//                    Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category),description);
//                    books.add(book);
//                }
//                fullBooksList.addAll(books);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        mDatabaseNovelties.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (novelties.size() > 0) {
//                    novelties.clear();
//                }
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
//                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
//                    String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
//                    Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
//                    String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
//                    String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
//                    Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
//                    Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
//                    String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
//                    Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category), description);
//                    novelties.add(book);
//                }
//
//                books.addAll(novelties);
//                fullBooksList.addAll(novelties);
//
//                noveltyAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //getSupportActionBar().hide();

//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            String email = user.getEmail();
//            //userEmail.setText(email);
//            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String login = snapshot.child("login").getValue().toString();
//                    String phone = snapshot.child("phone").getValue().toString();
//                    //userLogin.setText(login);
//                    currentUser = new User(login,phone,email);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }


        //DatabaseReference mDatabaseCategories = FirebaseDatabase.getInstance().getReference().child("Categories");
        //DatabaseReference mDatabaseReviews = FirebaseDatabase.getInstance().getReference().child("Reviews");
//        DatabaseReference mDatabaseBooks = FirebaseDatabase.getInstance().getReference().child("Books");
//        DatabaseReference mDatabaseNovelties = FirebaseDatabase.getInstance().getReference().child("Novelties");
//
//        mDatabaseBooks.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (books.size() > 0) {
//                    books.clear();
//                }
//                if (fullBooksList.size() > 0) {
//                    fullBooksList.clear();
//                }
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
//                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
//                    String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
//                    Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
//                    String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
//                    String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
//                    Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
//                    Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
//                    String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
//                    Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category),description);
//                    books.add(book);
//                }
//                fullBooksList.addAll(books);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        mDatabaseNovelties.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (novelties.size() > 0) {
//                    novelties.clear();
//                }
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
//                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
//                    String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
//                    Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
//                    String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
//                    String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
//                    Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
//                    Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
//                    String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
//                    Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category), description);
//                    novelties.add(book);
//                }
//
//                books.addAll(novelties);
//                fullBooksList.addAll(novelties);
//
//                noveltyAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            DatabaseReference mDatabaseCart = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart");
//            mDatabaseCart.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (cart.size() > 0) {
//                        cart.clear();
//                    }
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
//                        String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
//                        String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
//                        Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
//                        String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
//                        String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
//                        Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
//                        Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
//                        String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
//                        Long amount = (Long) ((HashMap<String, Object>) ds.getValue()).get("amount");
//                        BookInCart book = new BookInCart(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category), Math.toIntExact(amount), description);
//                        cart.add(book);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

//        mDatabaseNovelties.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (novelties.size() > 0) {
//                    novelties.clear();
//                }
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
//                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
//                    String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
//                    Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
//                    String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
//                    String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
//                    Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
//                    Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
//                    String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
//                    Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category), description);
//                    novelties.add(book);
//                }
//
//                books.addAll(novelties);
//                fullBooksList.addAll(novelties);
//
//                noveltyAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        mDatabaseCategories.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (categories.size() > 0) {
//                    categories.clear();
//                }
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    String name = (String) ((HashMap<String, Object>) ds.getValue()).get("name");
//                    Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
//                    Category category = new Category(Math.toIntExact(id), name);
//                    categories.add(category);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            DatabaseReference mDatabaseOrders = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders");
//            mDatabaseOrders.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (orders.size() > 0) {
//                        orders.clear();
//                    }
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        String number = (String) ((HashMap<String, Object>) ds.getValue()).get("number");
//                        String quantity = (String) ((HashMap<String, Object>) ds.getValue()).get("quantity");
//                        String date = (String) ((HashMap<String, Object>) ds.getValue()).get("date");
//                        String amount = (String) ((HashMap<String, Object>) ds.getValue()).get("amount");
//                        String status = (String) ((HashMap<String, Object>) ds.getValue()).get("status");
//                        Order order = new Order(number, quantity, date, amount, status);
//                        orders.add(order);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

//        Review reviewExample = new Review("1",String.valueOf(R.string.review_example),"user1","12/31/13", "3",String.valueOf(R.string.review_example));
//        Review reviewExample2 = new Review("2",String.valueOf(R.string.review_example),"user2","13/31/13", "2", String.valueOf(R.string.review_example));
//        Review reviewExample3 = new Review("3",String.valueOf(R.string.review_example),"user3","14/31/13", "2", String.valueOf(R.string.review_example));
//        Review reviewExample4 = new Review("4",String.valueOf(R.string.review_example),"user4","15/31/13", "1", String.valueOf(R.string.review_example));
//        Review reviewExample5 = new Review("5",String.valueOf(R.string.review_example),"user5","16/31/13", "1",String.valueOf(R.string.review_example));
//
//        Collections.addAll(reviews, reviewExample, reviewExample3, reviewExample2, reviewExample4, reviewExample5);
//        Collections.addAll(fullReviewsList, reviewExample, reviewExample3, reviewExample2, reviewExample4, reviewExample5);

//        mDatabaseReviews.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (fullReviewsList.size() > 0) {
//                    fullReviewsList.clear();
//                }
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    String grade = (String) ((HashMap<String, Object>) ds.getValue()).get("grade");
//                    String text = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
//                    String header = (String) ((HashMap<String, Object>) ds.getValue()).get("header");
//                    String date = (String) ((HashMap<String, Object>) ds.getValue()).get("date");
//                    String user = (String) ((HashMap<String, Object>) ds.getValue()).get("user");
//                    String idOfItem = (String) ((HashMap<String, Object>) ds.getValue()).get("itemId");
//                    Review review = new Review(grade, text, user, date, idOfItem, header);
//                    fullReviewsList.add(review);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String login = snapshot.child("login").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    currentUser = new User(login,phone,email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

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
                        String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
                        Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
                        Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
                        String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
                        Long amount = (Long) ((HashMap<String, Object>) ds.getValue()).get("amount");
                        BookInCart book = new BookInCart(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category), Math.toIntExact(amount), description);
                        cart.add(book);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}