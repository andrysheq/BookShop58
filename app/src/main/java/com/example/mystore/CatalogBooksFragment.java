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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogBooksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView booksRecycler;
    static CatalogBooksAdapter catalogBooksAdapter;
    ImageButton btnBack;
    ImageButton searchButton;
    TextView booksAmount;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CatalogBooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogBooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogBooksFragment newInstance(String param1, String param2) {
        CatalogBooksFragment fragment = new CatalogBooksFragment();
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
//                Bundle bundle = new Bundle();
//                bundle.putString("destinationName","CatalogBooks");
//                Navigation.findNavController(view).navigate(R.id.action_catalog_books_fragment_to_search_fragment,bundle);
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