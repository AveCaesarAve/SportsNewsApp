package com.example.android.sportsnewsapp;

public class SportNewsModel {

    private String mArticleTitle;
    private String mSectionName;
    private String mAuthorName;
    private String mPublishDate;
    private String mWebUrl;

    // create the SportNewsModel
    SportNewsModel(String mArticleTitle, String mSectionName, String mAuthorName, String mPublishDate, String mWebUrl) {
        this.mArticleTitle = mArticleTitle;
        this.mSectionName = mSectionName;
        this.mAuthorName = mAuthorName;
        this.mPublishDate = mPublishDate;
        this.mWebUrl = mWebUrl;
    }

    // create methods for getting the data from SportNewsModel
    public String getmArticleTitle() {
        return mArticleTitle;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getDateOfCreate() {
        return mPublishDate;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }
}