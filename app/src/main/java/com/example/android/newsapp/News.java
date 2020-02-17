package com.example.android.newsapp;

public class News {

    private String mTitle;
    private String mSectionName;
    private String mUrl;
    private float mRating;
    private String mAuthorName;
    private String mPublicationDate;

    public News(String title, String sectionName, String url) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mUrl = url;
    }

    public News(String title, String sectionName, String url, float rating, String authorName, String publicationDate) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mAuthorName = authorName;
        this.mPublicationDate = publicationDate;
        this.mRating = rating;
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public float getRating() {
        return mRating;
    }

    public String getUrl() {
        return mUrl;
    }
}
