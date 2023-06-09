package com.shurish.newsapp;

import android.content.Context;
import android.content.Intent;
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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false);

        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        Articles articles = articlesArrayList.get(position);
        holder.subheadline.setText(articles.getDescription());
        holder.headline.setText(articles.getTitle());
       // Picasso.get().load(articles.getUrlToImage()).into(holder.newsImg);

        Glide.with(context)
                .load(articles.getUrlToImage())
                .into(holder.newsImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("desc",articles.getDescription());
                intent.putExtra("image",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView headline , subheadline;
        private ImageView newsImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            headline = itemView.findViewById(R.id.news_headline);
            subheadline = itemView.findViewById(R.id.new_subheading);
            newsImg = itemView.findViewById(R.id.news_image);
        }
    }
}
