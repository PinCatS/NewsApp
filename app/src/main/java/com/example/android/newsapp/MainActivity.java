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
    private static final String QUERY_URL = "https://content.guardianapis.com/search?q=format=json&page-size=10&section=film&show-fields=starRating,thumbnail&show-tags=contributor&api-key=f5bac5c1-be69-4049-a2f1-9125f0403108";

    private NewsAdapter mNewsAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private LoaderManager loaderManager;

    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderManager = getSupportLoaderManager();

        /*
         * On card click, user moves to Details activity
         * */
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position, List<News> news) {
                Log.v(LOG_TAG, "onClick item position: " + position);
                News n = news.get(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(n.getUrl())));
            }
        };

        recyclerView = findViewById(R.id.recyclerView);

        // use a linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mNewsAdapter = new NewsAdapter(new ArrayList<News>(), listener);
        recyclerView.setAdapter(mNewsAdapter);

        initScrollListener();

        int page = QueryUtils.getCurrentPage();
        // Building URL
        Uri baseUri = Uri.parse(QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("page", Integer.toString(page));

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        Bundle loaderArgs = new Bundle();
        loaderArgs.putString("QUERY_URL", uriBuilder.toString());

        loaderManager.initLoader(LOADER_ID, loaderArgs, this);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    Log.v(LOG_TAG, "TEST: Adapter size: " + mNewsAdapter.size());
                    if (linearLayoutManager != null && mNewsAdapter.getItemAt(mNewsAdapter.size() - 1) == null &&
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() == mNewsAdapter.size() - 1) {
                        // if we listed already half of the items and there are more pages to load (last is null)
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        mNewsAdapter.remove(mNewsAdapter.size() - 1);

        int page = QueryUtils.getCurrentPage();

        // Building URL
        Uri baseUri = Uri.parse(QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("page", Integer.toString(++page));

        Bundle loaderArgs = new Bundle();
        loaderArgs.putString("QUERY_URL", uriBuilder.toString());
        loaderManager.restartLoader(LOADER_ID, loaderArgs, this);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.v(LOG_TAG, "TEST: onCreateLoader: " + args.getString("QUERY_URL"));
        return new NewsAsyncTaskLoader(this, args.getString("QUERY_URL"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        Log.v(LOG_TAG, "TEST: onLoadFinished: " + news.size());
        /*mNewsAdapter.clear();*/

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mNewsAdapter.addAll(news);
        }

        isLoading = false;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        Log.v(LOG_TAG, "TEST: onLoaderReset");
        mNewsAdapter.clear();
    }
}
