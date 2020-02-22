package com.example.android.newsapp;

class News {

    private String mTitle;
    private String mSectionName;
    private String mUrl;
    private String mThumbnailUrl;
    private float mRating;
    private String mAuthorName;
    private String mPublicationDate;


    News(String title, String sectionName, String url, String thumbnailUrl, float rating, String authorName, String publicationDate) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mAuthorName = authorName;
        this.mPublicationDate = publicationDate;
        this.mRating = rating;
        this.mUrl = url;
        this.mThumbnailUrl = thumbnailUrl;
    }

    String getTitle() {
        return mTitle;
    }

    String getSectionName() {
        return mSectionName;
    }

    String getAuthorName() {
        return mAuthorName;
    }

    String getPublicationDate() {
        return mPublicationDate;
    }

    float getRating() {
        return mRating;
    }

    String getUrl() {
        return mUrl;
    }

    String getThumbnailUrl() {
        return mThumbnailUrl;
    }
}
