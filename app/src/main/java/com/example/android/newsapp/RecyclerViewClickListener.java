package com.example.android.newsapp;

import android.view.View;

import java.util.List;

/*
 * To handle Item click on RecycleView
 * */
interface RecyclerViewClickListener {
    void onClick(View view, int position, List<News> news);
}
