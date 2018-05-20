package com.example.android.sportsnewsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class SportNewsLoader extends AsyncTaskLoader<List<SportNewsModel>> {

    private String myUrl;

    //TODO create new SportNewsLoader
    SportNewsLoader(Context context, String myUrl) {
        super(context);
        this.myUrl = myUrl;
    }

    //TODO onStartLoading method
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //TODO loadInBackground method
    @Override
    public List<SportNewsModel> loadInBackground() {

        if (myUrl == null) {
            return null;
        }
        return SportNewsQuery.getSportNewsData(myUrl);
    }
}