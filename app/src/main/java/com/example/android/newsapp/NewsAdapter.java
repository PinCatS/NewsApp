package com.example.android.newsapp;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    List<News> news;
    RecyclerViewClickListener listener;

    NewsAdapter(List<News> news, RecyclerViewClickListener listener) {
        this.news = news;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            return new NewsViewHolder(view, listener, this.news);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NewsViewHolder) {
            populateNewsItems((NewsViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return news.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void populateNewsItems(NewsViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.title.setText(news.getTitle());
        holder.sectionName.setText(news.getSectionName());

        if (news.getRating() == -1) {
            holder.ratingStars.setVisibility(View.GONE);
            holder.ratingValue.setVisibility(View.GONE);
        } else {
            holder.ratingStars.setRating(news.getRating());
            holder.ratingValue.setText(String.format("%.1f", news.getRating()));
        }

        if (TextUtils.isEmpty(news.getAuthorName())) {
            holder.author.setVisibility(View.GONE);
        } else {
            holder.author.setText(news.getAuthorName());
        }
        holder.date.setText(news.getPublicationDate());

        if (news.getThumbnailUrl() != null) {
            new ImageDownloaderTask(holder.thumbnail).execute(news.getThumbnailUrl());
        } else {
            holder.thumbnail.setVisibility(View.GONE);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    public void clear() {
        news.clear();
        notifyDataSetChanged();
    }

    public void add(News news) {
        this.news.add(news);
        notifyItemInserted(this.size() - 1);
    }

    public News getItemAt(int position) {
        return this.news.get(position);
    }

    public News remove(int position) {
        News news = this.news.remove(position);
        notifyItemRemoved(position);
        return news;
    }

    public void addAll(List<News> news) {
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    public int size() {
        return this.news.size();
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView sectionName;
        RatingBar ratingStars;
        TextView ratingValue;
        TextView author;
        TextView date;
        ImageView thumbnail;
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
            thumbnail = v.findViewById(R.id.card_image);
            this.news = news;
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition(), this.news);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
