package com.example.mystore;

import static com.example.mystore.MainActivity.fullBooksList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mystore.adapter.SearchAdapter;
import com.example.mystore.model.Book;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RecyclerView booksSearchRecycler;
    ImageButton buttonBack;
    SearchView searchView;
    SearchAdapter searchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.search);
        booksSearchRecycler = view.findViewById(R.id.search_recycler);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        buttonBack = view.findViewById(R.id.btn_search_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        booksSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        booksSearchRecycler.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(getContext(),fullBooksList);
        booksSearchRecycler.setAdapter(searchAdapter);

        searchAdapter.notifyDataSetChanged();
    }

    private void filterList(String text) {
        ArrayList<Book> filteredBooks = new ArrayList<>();
        for(Book book : fullBooksList){
            if(book.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredBooks.add(book);
            }
        }
        searchAdapter.setFilteredList(filteredBooks);
    }
}