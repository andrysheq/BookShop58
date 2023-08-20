package com.example.mystore;

import static com.example.mystore.MainActivity.fullBooksList;
import static com.example.mystore.MainActivity.fullReviewsList;
import static com.example.mystore.ReviewsFragment.getGrade;
import static com.example.mystore.model.Cart.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mystore.model.BookInCart;
import com.example.mystore.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BookPageFragment extends Fragment {

    ImageButton btnBack;
    ImageButton btnReviews;
    ImageButton btnAddToCart;
    NestedScrollView scrollView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView bookImage = view.findViewById(R.id.bookPageImage);
        TextView bookTitle = view.findViewById(R.id.bookPageTitle);
        TextView bookWriter = view.findViewById(R.id.bookPageWriter);
        TextView description = view.findViewById(R.id.book_description);
        TextView grade = view.findViewById(R.id.book_reviews_grade);
        TextView bookPrice = view.findViewById(R.id.bookPagePrice);
        scrollView = view.findViewById(R.id.bookPageScrollView);
        progressBar = view.findViewById(R.id.bookPageProgressBar);

        scrollView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        String url = getArguments().getString("image");
        Glide.with(getContext()).load(url).into(bookImage);
        bookWriter.setText(getArguments().getString("writer"));
        bookPrice.setText(getArguments().getString("price"));
        bookTitle.setText(getArguments().getString("title"));
        description.setText(getArguments().getString("description"));

        DatabaseReference mDatabaseReviews = FirebaseDatabase.getInstance().getReference().child("Reviews");
        mDatabaseReviews.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (fullReviewsList.size() > 0) {
                    fullReviewsList.clear();
                }
                for(DataSnapshot ds : snapshot.getChildren()){
                    String grade = (String) ((HashMap<String, Object>) ds.getValue()).get("grade");
                    String text = (String) ((HashMap<String, Object>) ds.getValue()).get("description");
                    String header = (String) ((HashMap<String, Object>) ds.getValue()).get("header");
                    String date = (String) ((HashMap<String, Object>) ds.getValue()).get("date");
                    String user = (String) ((HashMap<String, Object>) ds.getValue()).get("user");
                    String idOfItem = (String) ((HashMap<String, Object>) ds.getValue()).get("itemId");
                    Review review = new Review(grade, text, user, date, idOfItem, header);
                    fullReviewsList.add(review);
                }

                int id = getArguments().getInt("bookId", 0);
                ReviewsFragment.showReviewsByItemId(String.valueOf(id));
                String gradeString = getGrade();
                if(gradeString==null) {
                    grade.setText(getString(R.string.not_reviews));
                }else{
                    grade.setText(gradeString);
                }

                progressBar.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnReviews = view.findViewById(R.id.reviews_button);
        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                int id = getArguments().getInt("bookId", 0);
                bundle.putString("bookId",String.valueOf(id));
                ReviewsFragment.showReviewsByItemId(String.valueOf(id));
                Navigation.findNavController(view).navigate(R.id.action_book_page_to_reviews_fragment,bundle);
            }
        });

        btnBack = view.findViewById(R.id.btn_back_book_page);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        btnAddToCart = view.findViewById(R.id.add_to_cart_button);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//                    startActivity(new Intent(getContext(), SignRegActivity.class));
//                    Toast.makeText(getContext(), R.string.not_sign_error, Toast.LENGTH_LONG).show();
                    int id = getArguments().getInt("bookId", 0);
                    for (BookInCart book : cart) {
                        if (book.getId() == id) {
                            Toast.makeText(getContext(), R.string.cart_add_error, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    BookInCart newBook = new BookInCart(fullBooksList.get(id - 1).getId(), fullBooksList.get(id - 1).getAgeLimit(), fullBooksList.get(id - 1).getPrice(), fullBooksList.get(id - 1).getGenre(), fullBooksList.get(id - 1).getTitle(), fullBooksList.get(id - 1).getImg(), fullBooksList.get(id - 1).getWriter(), fullBooksList.get(id - 1).getCategory(), 1, fullBooksList.get(id - 1).getDescription());
                    cart.add(newBook);
                    Toast.makeText(getContext(), R.string.cart_add, Toast.LENGTH_LONG).show();
                } else {
                    int id = getArguments().getInt("bookId", 0);
                    for (BookInCart book : cart) {
                        if (book.getId() == id) {
                            Toast.makeText(getContext(), R.string.cart_add_error, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    BookInCart newBook = new BookInCart(fullBooksList.get(id - 1).getId(), fullBooksList.get(id - 1).getAgeLimit(), fullBooksList.get(id - 1).getPrice(), fullBooksList.get(id - 1).getGenre(), fullBooksList.get(id - 1).getTitle(), fullBooksList.get(id - 1).getImg(), fullBooksList.get(id - 1).getWriter(), fullBooksList.get(id - 1).getCategory(), 1, fullBooksList.get(id - 1).getDescription());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("title", newBook.getTitle());
                    map.put("id", newBook.getId());
                    map.put("genre", newBook.getGenre());
                    map.put("price", newBook.getPrice());
                    map.put("writer", newBook.getWriter());
                    map.put("description", newBook.getDescription());
                    map.put("img", newBook.getImg());
                    map.put("category", newBook.getCategory());
                    map.put("ageLimit", newBook.getAgeLimit());
                    map.put("amount", newBook.getAmount());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart").child(String.valueOf(newBook.getId())).updateChildren(map);
                    Toast.makeText(getContext(), R.string.cart_add, Toast.LENGTH_LONG).show();
                    //cartAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}