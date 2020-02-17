package com.example.android.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String queryUrl = "https://content.guardianapis.com/search?q=12%20years%20a%20slave&format=json&tag=film/film,tone/reviews&from-date=2019-11-01&show-tags=contributor&show-fields=starRating,headline,thumbnail,short-url&order-by=relevance&api-key=f5bac5c1-be69-4049-a2f1-9125f0403108";
        final List<News> news = QueryUtils.fetchNewsData(queryUrl);


        /*
         * On card click, user moves to Details activity
         * */
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                News n = news.get(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(n.getUrl())));
            }
        };


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new NewsAdapter(news, listener);
        recyclerView.setAdapter(mAdapter);
    }
}
