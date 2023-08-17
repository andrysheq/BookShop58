package com.example.mystore;

import static com.example.mystore.MainActivity.currentUser;
import static com.example.mystore.MainActivity.orders;
import static com.example.mystore.model.Cart.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mystore.model.Order;
import com.example.mystore.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileFragment extends Fragment {

    ImageButton signOutBtn;
    TextView userEmail;
    TextView userLogin;
    TextView orderCount;
    ImageButton buttonSearch;
    CardView myOrders;
    LinearLayout layout;
    NestedScrollView scrollView;
    ProgressBar progressBar;
    DatabaseReference ref;
    ArrayAdapter<String> adapter;
    ArrayList<String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signOutBtn = view.findViewById(R.id.button_sign_out);
        userEmail = view.findViewById(R.id.email_user);
        userLogin = view.findViewById(R.id.login_user);
        buttonSearch = view.findViewById(R.id.button_search_profile);
        myOrders = view.findViewById(R.id.orders_card);
        layout = view.findViewById(R.id.extra_param_layout);
        orderCount = myOrders.findViewById(R.id.order_count);
        scrollView = view.findViewById(R.id.profileScrollView);
        progressBar = view.findViewById(R.id.profileProgressBar);

        scrollView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        userEmail.setText(currentUser.getEmail());
        userLogin.setText(currentUser.getLogin());

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference mDatabaseOrders = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders");
            mDatabaseOrders.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        //Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        if (orders.size() > 0) {
                            orders.clear();
                        }
                        for(DataSnapshot ds : task.getResult().getChildren()){
                            String number = (String) ((HashMap<String, Object>) ds.getValue()).get("number");
                            String quantity = (String) ((HashMap<String, Object>) ds.getValue()).get("quantity");
                            String date = (String) ((HashMap<String, Object>) ds.getValue()).get("date");
                            String amount = (String) ((HashMap<String, Object>) ds.getValue()).get("amount");
                            String status = (String) ((HashMap<String, Object>) ds.getValue()).get("status");
                            Order order = new Order(number, quantity, date, amount, status);
                            orders.add(order);
                        }

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("У вас ");
                        stringBuilder.append(orders.size());
                        if(orders.size()%10==1){
                            stringBuilder.append(" активный заказ");
                        }else if(orders.size()%10>=2 && orders.size()%10<=4){
                            stringBuilder.append(" активных заказа");
                        }else if(orders.size()%10==0 || orders.size()%10>=5 && orders.size()%10<=9){
                            stringBuilder.append(" активных заказов");
                        }
                        orderCount.setText(stringBuilder);

                        progressBar.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menu_profile_to_search_fragment);
            }
        });

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_menu_profile_to_orders_fragment);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                cart.clear();
                startActivity(new Intent(getContext(),SignRegActivity.class));
            }
        });
    }
}