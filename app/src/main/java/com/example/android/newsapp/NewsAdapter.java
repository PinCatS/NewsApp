package com.example.android.newsapp;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<News> news;
    RecyclerViewClickListener listener;

    NewsAdapter(List<News> news, RecyclerViewClickListener listener) {
        this.news = news;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new NewsViewHolder(v, listener, news);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.title.setText(news.getTitle());
        holder.sectionName.setText(news.getSectionName());
        holder.ratingStars.setRating(news.getRating());
        holder.ratingValue.setText(String.format("%.1f", news.getRating()));

        if (TextUtils.isEmpty(news.getAuthorName())) {
            holder.author.setVisibility(View.GONE);
        } else {
            holder.author.setText(news.getAuthorName());
        }
        holder.date.setText(news.getPublicationDate());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void clear() {
        news.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<News> news) {
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView sectionName;
        RatingBar ratingStars;
        TextView ratingValue;
        TextView author;
        TextView date;
        RecyclerViewClickListener listener;
        List<News> news;

        NewsViewHolder(View v, RecyclerViewClickListener listener, List<News> news) {
            super(v);
            title = v.findViewById(R.id.card_title);
            sectionName = v.findViewById(R.id.section_name);
            ratingStars = v.findViewById(R.id.rating_indicator);
            ratingValue = v.findViewById(R.id.rating_value);
            author = v.findViewById(R.id.author);
            date = v.findViewById(R.id.publication_date);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition(), news);
        }
    }
}
