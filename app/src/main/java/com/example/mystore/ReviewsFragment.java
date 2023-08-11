package com.example.mystore;

import static com.example.mystore.MainActivity.orders;
import static com.example.mystore.MainActivity.reviews;

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

import com.example.mystore.adapter.OrderAdapter;
import com.example.mystore.adapter.ReviewAdapter;


public class ReviewsFragment extends Fragment {

    RecyclerView reviewsRecycler;
    ImageButton btnBack;
    ImageButton searchButton;
    ReviewAdapter reviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = view.findViewById(R.id.btn_back_reviews);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        searchButton = view.findViewById(R.id.search_review_fragment);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviews_fragment_to_search_fragment);
            }
        });

        reviewsRecycler = view.findViewById(R.id.reviews);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecycler.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(getContext(),reviews);
        reviewsRecycler.setAdapter(reviewAdapter);

        reviewAdapter.notifyDataSetChanged();
    }
}