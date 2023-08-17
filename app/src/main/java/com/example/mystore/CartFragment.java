package com.example.mystore;

import static com.example.mystore.MainActivity.fullBooksList;
import static com.example.mystore.MainActivity.orders;
import static com.example.mystore.model.Cart.cart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystore.adapter.CartAdapter;
import com.example.mystore.model.BookInCart;
import com.example.mystore.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static TextView amountOfItems;
    public static TextView finalPrice;
    RecyclerView cartRecycler;
    ImageButton btnBuy;
    public static CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        amountOfItems = view.findViewById(R.id.amountOfItems);
        finalPrice = view.findViewById(R.id.finalPrice);
        btnBuy = view.findViewById(R.id.buy_cart);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        cartRecycler = view.findViewById(R.id.cartRecycler);
        cartRecycler.setLayoutManager(llm);
        cartRecycler.setHasFixedSize(true);
        cartAdapter = new CartAdapter(getContext(),cart);
        cartRecycler.setAdapter(cartAdapter);
        //cartAdapter.notifyDataSetChanged();

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(getContext(), SignRegActivity.class));
                    Toast.makeText(getContext(), R.string.not_sign_error, Toast.LENGTH_LONG).show();
                }else {
                    Order newOrder = new Order(String.valueOf(orders.size()+1),String.valueOf(cartAdapter.getItemsAmount()), LocalDate.now().toString(), String.valueOf(cartAdapter.getItemsPrice()),"Принят");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("number", newOrder.getNumber());
                    map.put("quantity", newOrder.getQuantity());
                    map.put("date", newOrder.getDate());
                    map.put("amount", newOrder.getAmount());
                    map.put("status", newOrder.getStatus());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(String.valueOf(newOrder.getNumber())).updateChildren(map);
                    Toast.makeText(getContext(), R.string.buy_success, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}