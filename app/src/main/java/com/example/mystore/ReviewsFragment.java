package com.example.mystore;

import static com.example.mystore.MainActivity.currentUser;
import static com.example.mystore.MainActivity.fullReviewsList;
import static com.example.mystore.MainActivity.reviews;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mystore.adapter.ReviewAdapter;
import com.example.mystore.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class ReviewsFragment extends Fragment {

    RecyclerView reviewsRecycler;
    ImageButton btnBack;
    ImageButton createReviewButton;
    static ReviewAdapter reviewAdapter;

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

        createReviewButton = view.findViewById(R.id.create_review);

        createReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateReview();
                //Navigation.findNavController(view).popBackStack();
            }
        });

        reviewsRecycler = view.findViewById(R.id.reviews);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecycler.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(getContext(),reviews);
        reviewsRecycler.setAdapter(reviewAdapter);

        reviewAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void showReviewsByItemId(String id){
        reviews.clear();
        reviews.addAll(fullReviewsList);
        ArrayList<Review> sortedReviewsByItemId = new ArrayList<>();
        reviews.forEach((review) -> {
            if (review.getIdOfItem().equals(id)) {
                sortedReviewsByItemId.add(review);
            }
        });
        reviews.clear();
        reviews.addAll(sortedReviewsByItemId);
        if(reviewAdapter!=null)
            reviewAdapter.notifyDataSetChanged();
    }

    private void showCreateReview() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Оставьте отзыв");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View review_window = inflater.inflate(R.layout.create_review_dialog,null);
        dialog.setView(review_window);

        EditText header = review_window.findViewById(R.id.headerField);
        EditText description = review_window.findViewById(R.id.descriptionField);
        EditText grade = review_window.findViewById(R.id.gradeField);


        dialog.setNegativeButton(R.string.previous, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(header.getText().toString())){
                    //hideKeyboard();
                    //Toast.makeText(getContext(),R.string.header_error,Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(description.getText().toString())){
                    //hideKeyboard();
                    //Toast.makeText(getContext(),R.string.description_error,Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(grade.getText().toString())) {
                    //hideKeyboard();
                    //Toast.makeText(getContext(), R.string.grade_error, Toast.LENGTH_LONG).show();
                    return;
                }
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    //startActivity(new Intent(getContext(), SignRegActivity.class));
                    Toast.makeText(getContext(), R.string.unsigned_review_error, Toast.LENGTH_LONG).show();
                }else {
                    //Order newOrder = new Order(String.valueOf(orders.size()+1),String.valueOf(cartAdapter.getItemsAmount()), LocalDate.now().toString(), String.valueOf(cartAdapter.getItemsPrice()),"Принят");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("description", description.getText().toString());
                    map.put("header", header.getText().toString());
                    map.put("grade", grade.getText().toString());
                    map.put("date", LocalDate.now().toString());
                    String id = getArguments().getString("bookId");
                    map.put("itemId", id);
                    map.put("user", currentUser.getLogin());
                    FirebaseDatabase.getInstance().getReference().child("Reviews").child(String.valueOf(fullReviewsList.size()+1).toString()).updateChildren(map);
                    //Toast.makeText(getContext(), R.string.buy_success, Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).popBackStack();
                }
            }
        });

        dialog.show();
    }

    static String getGrade(){
        AtomicReference<Double> s= new AtomicReference<>((double) 0);
        AtomicReference<Double> k= new AtomicReference<>((double) 0);
        reviews.forEach((review ) -> {
            s.updateAndGet(v -> new Double((double) (v + Double.parseDouble(review.getGrade()))));
            k.getAndSet(new Double((double) (k.get() + 1)));
        });
        if(k.get()!=0) {
            return String.valueOf(s.get() / k.get());
        }else{
            return null;
        }
    }
}