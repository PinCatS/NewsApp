package com.example.android.newsapp;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

class NewsAsyncTaskLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    NewsAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {

        if (TextUtils.isEmpty(mUrl)) {
            return null;
        }

        return QueryUtils.fetchNewsData(mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
