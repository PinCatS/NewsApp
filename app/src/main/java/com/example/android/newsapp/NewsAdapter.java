package com.example.android.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<News> news;

    NewsAdapter(List<News> news) {
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.title.setText(news.getTitle());
        holder.sectionName.setText(news.getSectionName());
        holder.ratingStars.setRating(news.getRating());
        holder.ratingValue.setText(String.format("%.1f", news.getRating()));
        holder.author.setText(news.getAuthorName());
        holder.date.setText(news.getAuthorName());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView sectionName;
        RatingBar ratingStars;
        TextView ratingValue;
        TextView author;
        TextView date;


        NewsViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.card_title);
            sectionName = v.findViewById(R.id.section_name);
            ratingStars = v.findViewById(R.id.rating_indicator);
            ratingValue = v.findViewById(R.id.rating_value);
            author = v.findViewById(R.id.author);
            date = v.findViewById(R.id.publication_date);
        }
    }
}
