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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView booksSearchRecycler;
    ImageButton buttonBack;
    SearchView searchView;
    SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
//                String key = getArguments().getString("destinationName");
//                switch (key){
//                    case "Home":
//                        Navigation.findNavController(view).navigate(R.id.action_search_fragment_to_menu_home);
//                        break;
//                    case "CatalogBooks":
//                        Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_catalogBooksFragment);
//                        break;
//                    case "Catalog":
//                        Navigation.findNavController(view).navigate(R.id.action_search_fragment_to_menu_catalog);
//                        break;
//                    case "Profile":
//                        Navigation.findNavController(view).navigate(R.id.action_search_fragment_to_menu_profile);
//                        break;
//                }
                Navigation.findNavController(view).navigate(R.id.action_search_fragment_to_menu_home);
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