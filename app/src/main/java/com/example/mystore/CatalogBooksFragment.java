package com.example.mystore;

import static com.example.mystore.MainActivity.books;
import static com.example.mystore.MainActivity.fullBooksList;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mystore.adapter.CatalogBooksAdapter;
import com.example.mystore.adapter.SearchAdapter;
import com.example.mystore.model.Book;

import java.util.ArrayList;

public class CatalogBooksFragment extends Fragment {

    RecyclerView booksRecycler;
    static CatalogBooksAdapter catalogBooksAdapter;
    ImageButton btnBack;
    ImageButton searchButton;
    TextView booksAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = view.findViewById(R.id.btn_back_catalog_books);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        booksAmount = view.findViewById(R.id.books_amount_catalog_books);
        if(books.size()%10==2 || books.size()%10==3 || books.size()%10==4){
            booksAmount.setText(books.size()+" товара");
        }else if(books.size()%10==1){
            booksAmount.setText(books.size()+" товар");
        }else {
            booksAmount.setText(books.size() + " товаров");
        }

        searchButton = view.findViewById(R.id.search_catalog_books);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_catalog_books_fragment_to_search_fragment);
            }
        });

        booksRecycler = view.findViewById(R.id.catalog_book_list);
        booksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        booksRecycler.setHasFixedSize(true);
        catalogBooksAdapter = new CatalogBooksAdapter(getContext(),books);
        booksRecycler.setAdapter(catalogBooksAdapter);

        catalogBooksAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void showBooksByCategory(int category){
        books.clear();
        books.addAll(fullBooksList);
        if(category!=1) {
            ArrayList<Book> sortedNoveltiesByCategory = new ArrayList<>();
            books.forEach((novelty) -> {
                if (novelty.getCategory() == category) {
                    sortedNoveltiesByCategory.add(novelty);
                }
            });
            books.clear();
            books.addAll(sortedNoveltiesByCategory);
        }
        if(catalogBooksAdapter!=null)
            catalogBooksAdapter.notifyDataSetChanged();
    }
}