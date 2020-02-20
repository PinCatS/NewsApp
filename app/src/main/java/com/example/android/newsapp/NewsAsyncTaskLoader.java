package com.example.android.newsapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

class NewsAsyncTaskLoader extends AsyncTaskLoader<List<News>> {

    String mUrl;

    public NewsAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {

        Log.v("NewsAsyncTaskLoader", "TEST: loadInBackground: " + mUrl);

        if (TextUtils.isEmpty(mUrl)) {
            return null;
        }

        List<News> result = QueryUtils.fetchNewsData(mUrl);
        Log.v("NewsAsyncTaskLoader", "TEST: loadInBackground results: " + result.size());
        return result;
    }

    @Override
    protected void onStartLoading() {
        Log.v("NewsAsyncTaskLoader", "TEST: onStartLoading");
        forceLoad();
    }
}
