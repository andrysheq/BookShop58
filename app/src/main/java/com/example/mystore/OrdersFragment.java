package com.example.mystore;

import static com.example.mystore.MainActivity.books;
import static com.example.mystore.MainActivity.orders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mystore.adapter.CatalogBooksAdapter;
import com.example.mystore.adapter.OrderAdapter;
import com.example.mystore.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OrdersFragment extends Fragment {

    RecyclerView ordersRecycler;
    ImageButton btnBack;
    ImageButton searchButton;
    OrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = view.findViewById(R.id.btn_back_catalog_books);

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


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        searchButton = view.findViewById(R.id.search_catalog_books);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_orders_fragment_to_search_fragment);
            }
        });

        ordersRecycler = view.findViewById(R.id.orders);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersRecycler.setHasFixedSize(true);
        orderAdapter = new OrderAdapter(getContext(),orders);
        ordersRecycler.setAdapter(orderAdapter);

        orderAdapter.notifyDataSetChanged();
    }
}