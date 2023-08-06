package com.example.mystore;

import static com.example.mystore.CartFragment.cartAdapter;
import static com.example.mystore.MainActivity.fullBooksList;
import static com.example.mystore.Order.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mystore.model.BookInCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageButton btnBack;
    ImageButton btnAddToCart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookPageFragment newInstance(String param1, String param2) {
        BookPageFragment fragment = new BookPageFragment();
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
        return inflater.inflate(R.layout.fragment_book_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView bookImage = view.findViewById(R.id.bookPageImage);
        TextView bookTitle = view.findViewById(R.id.bookPageTitle);
        TextView bookWriter = view.findViewById(R.id.bookPageWriter);
        TextView bookPrice = view.findViewById(R.id.bookPagePrice);

        String url = getArguments().getString("image");
        Glide.with(getContext()).load(url).into(bookImage);
        //bookImage.setImageResource(getArguments().getInt("image",0));
        bookWriter.setText(getArguments().getString("writer"));
        bookPrice.setText(getArguments().getString("price"));
        bookTitle.setText(getArguments().getString("title"));

        btnBack = view.findViewById(R.id.btn_back_book_page);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = getArguments().getString("destinationName");
                switch (key){
                    case "Home":
                        Navigation.findNavController(view).navigate(R.id.action_book_page_to_menu_home);
                        break;
                    case "Search":
                        Navigation.findNavController(view).navigate(R.id.action_book_page_to_search_fragment);
                        break;
                    case "Cart":
                        Navigation.findNavController(view).navigate(R.id.action_book_page_to_menu_cart);
                        break;
                    case "CatalogBooks":
                        Navigation.findNavController(view).navigate(R.id.action_book_page_to_catalog_books_fragment);
                        break;
                    case "Catalog":
                        Navigation.findNavController(view).navigate(R.id.action_book_page_to_menu_catalog);
                        break;
                }
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
                    BookInCart newBook = new BookInCart(fullBooksList.get(id - 1).getId(), fullBooksList.get(id - 1).getAgeLimit(), fullBooksList.get(id - 1).getPrice(), fullBooksList.get(id - 1).getGenre(), fullBooksList.get(id - 1).getTitle(), fullBooksList.get(id - 1).getImg(), fullBooksList.get(id - 1).getWriter(), fullBooksList.get(id - 1).getCategory(), 1);
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
                    BookInCart newBook = new BookInCart(fullBooksList.get(id - 1).getId(), fullBooksList.get(id - 1).getAgeLimit(), fullBooksList.get(id - 1).getPrice(), fullBooksList.get(id - 1).getGenre(), fullBooksList.get(id - 1).getTitle(), fullBooksList.get(id - 1).getImg(), fullBooksList.get(id - 1).getWriter(), fullBooksList.get(id - 1).getCategory(), 1);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("title", newBook.getTitle());
                    map.put("id", newBook.getId());
                    map.put("genre", newBook.getGenre());
                    map.put("price", newBook.getPrice());
                    map.put("writer", newBook.getWriter());
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