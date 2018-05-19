package com.example.android.sportsnewsapp;

public class SportNewsModel {

    private String articleTitle;
    private String sectionName;
    private String authorName;
    private String publishDate;
    private String webUrl;

    SportNewsModel(String articleTitle, String sectionName, String authorName, String publishDate, String webUrl) {
        this.articleTitle = articleTitle;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.publishDate = publishDate;
        this.webUrl = webUrl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDateOfCreate() {
        return publishDate;
    }

    public String getWebUrl() {
        return webUrl;
    }
}