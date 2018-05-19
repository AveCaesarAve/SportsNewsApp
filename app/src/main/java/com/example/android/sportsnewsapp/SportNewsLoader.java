package com.example.android.sportsnewsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class SportNewsLoader extends AsyncTaskLoader<List<SportNewsModel>> {

    private String myUrl;

    SportNewsLoader(Context context, String myUrl) {
        super(context);
        this.myUrl = myUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<SportNewsModel> loadInBackground() {

        if (myUrl == null) {
            return null;
        }
        return SportNewsQuery.getSportNewsData(myUrl);
    }
}