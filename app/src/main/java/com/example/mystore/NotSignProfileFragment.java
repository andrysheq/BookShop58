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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mystore.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class NotSignProfileFragment extends Fragment {

    ImageButton signOrRegBtn;
    CardView aboutUsCard;
    CardView contactsCard;
    ProgressBar progressBar;
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_not_sign_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signOrRegBtn = view.findViewById(R.id.sign_or_reg_button);
        progressBar = view.findViewById(R.id.unsignedProfileProgressBar);
        layout = view.findViewById(R.id.extra_param_layout_unsigned_profile);
        aboutUsCard = view.findViewById(R.id.about_us_card);
        contactsCard = view.findViewById(R.id.contacts_card);

        layout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        progressBar.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);

        signOrRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),SignRegActivity.class));
            }
        });

        aboutUsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_not_sign_profile_fragment_to_aboutUsFragment);
            }
        });

        contactsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_not_sign_profile_fragment_to_contactsFragment);
            }
        });
    }
}