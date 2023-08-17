package com.example.mystore;

import static com.example.mystore.CatalogBooksFragment.catalogBooksAdapter;
import static com.example.mystore.MainActivity.categories;
import static com.example.mystore.MainActivity.fullBooksList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mystore.adapter.CartAdapter;
import com.example.mystore.adapter.CatalogBooksAdapter;
import com.example.mystore.adapter.CatalogCategoryAdapter;
import com.example.mystore.adapter.CatalogFullBooksAdapter;
import com.example.mystore.adapter.SearchAdapter;
import com.example.mystore.model.Book;
import com.example.mystore.model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView categoryCatalogRecycler;
    RecyclerView booksCatalogRecycler;
    TextView booksAmount;
    ImageButton searchButton;
    ProgressBar progressBar;
    NestedScrollView scrollView;
    CatalogFullBooksAdapter catalogFullBooksAdapter;
    CatalogCategoryAdapter catalogCategoryAdapter;

    public CatalogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogFragment newInstance(String param1, String param2) {
        CatalogFragment fragment = new CatalogFragment();
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

        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView = view.findViewById(R.id.scrollViewCatalog);
        progressBar = view.findViewById(R.id.progressBarCatalog);
        categoryCatalogRecycler = view.findViewById(R.id.category_catalog_list);
        booksCatalogRecycler = view.findViewById(R.id.catalog_list);
        booksAmount = view.findViewById(R.id.books_amount);
        searchButton = view.findViewById(R.id.search_catalog);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menu_catalog_to_search_fragment);
            }
        });

        scrollView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        DatabaseReference mDatabaseCategories = FirebaseDatabase.getInstance().getReference().child("Categories");
        mDatabaseCategories.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    //Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if (categories.size() > 0) {
                        categories.clear();
                    }
                    for(DataSnapshot ds : task.getResult().getChildren()){
                        String name = (String) ((HashMap<String, Object>) ds.getValue()).get("name");
                        Long id = (Long) ((HashMap<String, Object>) ds.getValue()).get("id");
                        Category category = new Category(Math.toIntExact(id), name);
                        categories.add(category);
                    }

                    catalogCategoryAdapter.notifyDataSetChanged();

                    if(fullBooksList.size()%10==2 || fullBooksList.size()%10==3 || fullBooksList.size()%10==4){
                        booksAmount.setText(fullBooksList.size()+" товара");
                    }else if(fullBooksList.size()%10==1){
                        booksAmount.setText(fullBooksList.size()+" товар");
                    }else {
                        booksAmount.setText(fullBooksList.size() + " товаров");
                    }

                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        });

        categoryCatalogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryCatalogRecycler.setHasFixedSize(true);
        catalogCategoryAdapter = new CatalogCategoryAdapter(getContext(),categories);
        categoryCatalogRecycler.setAdapter(catalogCategoryAdapter);

        booksCatalogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        booksCatalogRecycler.setHasFixedSize(true);
        catalogFullBooksAdapter = new CatalogFullBooksAdapter(getContext(),fullBooksList);
        booksCatalogRecycler.setAdapter(catalogFullBooksAdapter);
        catalogFullBooksAdapter.notifyDataSetChanged();
    }
}