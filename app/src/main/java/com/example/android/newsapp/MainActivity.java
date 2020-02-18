package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int LOADER_ID = 0;
    private static final String QUERY_URL = "https://content.guardianapis.com/search?q=12%20years%20a%20slave&format=json&tag=film/film,tone/reviews&from-date=2019-11-01&show-tags=contributor&show-fields=starRating,headline,thumbnail,short-url&order-by=relevance&api-key=f5bac5c1-be69-4049-a2f1-9125f0403108";

    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * On card click, user moves to Details activity
         * */
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position, List<News> news) {
                News n = news.get(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(n.getUrl())));
            }
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mNewsAdapter = new NewsAdapter(new ArrayList<News>(), listener);
        recyclerView.setAdapter(mNewsAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.v(LOG_TAG, "TEST: onCreateLoader: " + QUERY_URL);
        return new NewsAsyncTaskLoader(this, QUERY_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        Log.v(LOG_TAG, "TEST: onLoadFinished: " + news.size());
        mNewsAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mNewsAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        Log.v(LOG_TAG, "TEST: onLoaderReset");
        mNewsAdapter.clear();
    }
}
