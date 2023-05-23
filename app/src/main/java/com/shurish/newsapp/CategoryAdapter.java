package com.shurish.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryModel> categoryModels;
    private Context context;
    private CategoryClickInterface categoryClickInterface;


    public CategoryAdapter(ArrayList<CategoryModel> categoryModels, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryModels = categoryModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModels.get(position);
        holder.categoryText.setText(categoryModel.getCategory());
       // Picasso.get().load(categoryModel.getCategoryImageUrl()).into(holder.categoryImg);


        Glide.with(context)
                .load(categoryModel.getCategoryImageUrl())
                .into(holder.categoryImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public interface CategoryClickInterface{

        void onCategoryClick (int position);
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        ImageView categoryImg;
        TextView categoryText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImg = itemView.findViewById(R.id.category_image);
            categoryText = itemView.findViewById(R.id.category_text);
        }
    }
}
