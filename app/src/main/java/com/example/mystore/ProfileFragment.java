package com.example.mystore;

import static com.example.mystore.MainActivity.orders;
import static com.example.mystore.model.Cart.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageButton signOutBtn;
    TextView userEmail;
    TextView userLogin;
    TextView orderCount;
    ImageButton buttonSearch;
    CardView myOrders;
    DatabaseReference ref;
    ArrayAdapter<String> adapter;
    ArrayList<String> data;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signOutBtn = view.findViewById(R.id.button_sign_out);
        userEmail = view.findViewById(R.id.email_user);
        userLogin = view.findViewById(R.id.login_user);
        //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        buttonSearch = view.findViewById(R.id.button_search_profile);
        myOrders = view.findViewById(R.id.orders_card);


        orderCount = myOrders.findViewById(R.id.order_count);
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

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(getContext(),SignRegActivity.class));
        }else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail();
            userEmail.setText(email);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String login = snapshot.child("login").getValue().toString();
                    userLogin.setText(login);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


}