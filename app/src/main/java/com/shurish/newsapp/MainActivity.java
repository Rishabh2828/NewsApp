package com.shurish.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface{

    RecyclerView newsrecyclerview;
    RecyclerView categoryrecyclerview;
    ProgressBar progressBar;
    ArrayList<Articles> articlesArrayList;
    ArrayList<CategoryModel> categoryModelArrayList;
    CategoryAdapter categoryAdapter;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      newsrecyclerview = findViewById(R.id.newRecycler);
      categoryrecyclerview = findViewById(R.id.categoryRecycler);
      progressBar=findViewById(R.id.progressBar);
      articlesArrayList =new ArrayList<>();
      categoryModelArrayList = new ArrayList<>();
      categoryAdapter = new CategoryAdapter(categoryModelArrayList, this, this::onCategoryClick);
      newsAdapter = new NewsAdapter(articlesArrayList,this);
      newsrecyclerview.setLayoutManager(new LinearLayoutManager(this));
      newsrecyclerview.setAdapter(newsAdapter);
      categoryrecyclerview.setAdapter(categoryAdapter);
      getcategories();
      getNews("All");
      newsAdapter.notifyDataSetChanged();


    }

    private void  getcategories(){
        categoryModelArrayList.add(new CategoryModel("All" , "https://images.pexels.com/photos/7562114/pexels-photo-7562114.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryModelArrayList.add(new CategoryModel("Technology" , "https://images.unsplash.com/photo-1605810230434-7631ac76ec81?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80"));
        categoryModelArrayList.add(new CategoryModel("Science" , "https://images.unsplash.com/photo-1582719471384-894fbb16e074?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryModelArrayList.add(new CategoryModel("Sports" , "https://images.pexels.com/photos/3657154/pexels-photo-3657154.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryModelArrayList.add(new CategoryModel("General" , "https://images.pexels.com/photos/5146462/pexels-photo-5146462.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryModelArrayList.add(new CategoryModel("Business" , "https://images.pexels.com/photos/2182973/pexels-photo-2182973.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryModelArrayList.add(new CategoryModel("Entertainment" , "https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryModelArrayList.add(new CategoryModel("Health" , "https://images.pexels.com/photos/6698513/pexels-photo-6698513.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        progressBar.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category +"&apiKey=fc5c54af2e4441f6a667d769596a462c";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=fc5c54af2e4441f6a667d769596a462c";
        String Base_URl = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if (category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                progressBar.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i=0; i<articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(),
                            articles.get(i).getContent(), articles.get(i).getUrl()));

                }
           newsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get news", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCategoryClick(int position) {

        String category = categoryModelArrayList.get(position).category;
        getNews(category);

    }
}