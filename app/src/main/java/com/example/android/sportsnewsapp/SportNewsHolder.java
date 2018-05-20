package com.example.android.sportsnewsapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SportNewsHolder extends RecyclerView.ViewHolder {

    TextView articleTitle;
    TextView articleSection;
    TextView articleAuthor;
    TextView articleDate;

    //TODO create a new SportNewsHolder
    SportNewsHolder(View itemView) {
        super(itemView);
        articleTitle = itemView.findViewById(R.id.articleTitleTxt);
        articleSection = itemView.findViewById(R.id.articleSectionNameTxt);
        articleAuthor = itemView.findViewById(R.id.articleAuthorTxt);
        articleDate = itemView.findViewById(R.id.articleDateTxt);

    }
}

