package com.example.mystore;

import static com.example.mystore.MainActivity.books;
import static com.example.mystore.MainActivity.categories;
import static com.example.mystore.MainActivity.fullBooksList;
import static com.example.mystore.MainActivity.novelties;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.mystore.adapter.CatalogCategoryAdapter;
import com.example.mystore.adapter.NoveltyAdapter;
import com.example.mystore.model.Book;
import com.example.mystore.model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    RecyclerView noveltyRecycler;
    ImageButton searchButton;
    SearchView searchView;
    ProgressBar progressBar;
    LinearLayout home;
    static NoveltyAdapter noveltyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchButton = view.findViewById(R.id.search_home);
        noveltyRecycler = view.findViewById(R.id.noveltyRecycler);
        progressBar = view.findViewById(R.id.progress_bar);
        home = view.findViewById(R.id.linearLayoutHome);

        home.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menu_home_to_search_fragment);
            }
        });

        DatabaseReference mDatabaseNovelties = FirebaseDatabase.getInstance().getReference().child("Novelties");
        mDatabaseNovelties.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    //вызвать окно ошибки
                }
                else {
                    if (novelties.size() > 0) {
                        novelties.clear();
                    }
                    for(DataSnapshot ds : task.getResult().getChildren()){
                        String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
                        Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
                        String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
                        Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
                        String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
                        String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
                        Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
                        Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
                        String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
                        Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category), description);
                        novelties.add(book);
                    }

                    books.addAll(novelties);
                    fullBooksList.addAll(novelties);

                    noveltyAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    home.setVisibility(View.VISIBLE);
                }
            }
        });

        DatabaseReference mDatabaseBooks = FirebaseDatabase.getInstance().getReference().child("Books");
        mDatabaseBooks.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    //вызвать окно ошибки
                }
                else {
                    if (books.size() > 0) {
                        books.clear();
                    }
                    if (fullBooksList.size() > 0) {
                        fullBooksList.clear();
                    }
                    for(DataSnapshot ds : task.getResult().getChildren()){
                        String title = (String) ((HashMap<String, Object>) ds.getValue()).get("title");
                        Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
                        String genre = (String) ((HashMap<String, Object>) ds.getValue()).get("genre");
                        Long price = (Long) ((HashMap<String, Object>) ds.getValue()).get("price");
                        String writer = (String) ((HashMap<String, Object>) ds.getValue()).get("writer");
                        String description = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
                        Long category = (Long) ((HashMap<String, Object>) ds.getValue()).get("category");
                        Long ageLimit = (Long) ((HashMap<String, Object>) ds.getValue()).get("ageLimit");
                        String image = (String) ((HashMap<String, Object>) ds.getValue()).get("img");
                        Book book = new Book(Math.toIntExact(id), Math.toIntExact(ageLimit), Math.toIntExact(price), genre, title, image, writer, Math.toIntExact(category),description);
                        books.add(book);
                    }
                    fullBooksList.addAll(books);
                }
            }
        });

        LinearLayoutManager llm2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        noveltyRecycler.setLayoutManager(llm2);
        noveltyRecycler.setHasFixedSize(true);
        noveltyAdapter = new NoveltyAdapter(getContext(),novelties);
        noveltyRecycler.setAdapter(noveltyAdapter);
    }
}