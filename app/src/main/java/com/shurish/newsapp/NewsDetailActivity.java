package com.shurish.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class NewsDetailActivity extends AppCompatActivity {
    String title, desc, content, imageUrl, url;

    TextView titleTV, sub_desc, contentTV;
    ImageView newsImg;
    Button readnewsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageUrl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");


        titleTV = findViewById(R.id.desc_news_title);
        sub_desc = findViewById(R.id.desc_sub_desc);
        contentTV  =findViewById(R.id.desc_content);

        newsImg = findViewById(R.id.Desc_news_img);
        readnewsBtn = findViewById(R.id.btn_readnews);

        titleTV.setText(title);
        sub_desc.setText(desc);
        contentTV.setText(content);

        Glide.with(this).load(imageUrl).into(newsImg);
        readnewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });

    }
}