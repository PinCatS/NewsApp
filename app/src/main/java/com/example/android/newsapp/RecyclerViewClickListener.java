package com.example.android.newsapp;

import android.view.View;

import java.util.List;

interface RecyclerViewClickListener {
    void onClick(View view, int position, List<News> news);
}
