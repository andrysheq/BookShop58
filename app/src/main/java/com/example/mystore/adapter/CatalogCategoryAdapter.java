package com.example.mystore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.CatalogBooksFragment;
import com.example.mystore.R;
import com.example.mystore.model.Category;

import java.util.ArrayList;

public class CatalogCategoryAdapter extends RecyclerView.Adapter<CatalogCategoryAdapter.CatalogCategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public CatalogCategoryAdapter(Context context, ArrayList<Category> categories){
        this.context = context;
        this.categories = categories;
    }
    @NonNull
    @Override
    public CatalogCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.catalog_category_item, parent, false);
        return new CatalogCategoryViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogCategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryTitle.setText(categories.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CatalogBooksFragment.showBooksByCategory(categories.get(position).getId());
                Navigation.findNavController(view).navigate(R.id.action_menu_catalog_to_catalog_books_fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static final class CatalogCategoryViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitle;

        public CatalogCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.categoryTitle);
        }
    }
}
