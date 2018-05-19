package com.example.android.sportsnewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<SportNewsModel>> {

    RecyclerView recyclerView;
    SportsNewsAdapter newsAdapter;
    ArrayList<SportNewsModel> newsList;
    private TextView mEmptyStateTextView;
    private View loadingBar;

    private static final String REQUEST_URL = "http://content.guardianapis.com/search?show-tags=contributor&section=sport&q=cycling&api-key=f56abef9-94f7-4fad-b92f-c9a26b5f37c2";
    private static final int LOADER_NEWS_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView =findViewById(R.id.emptyView);
        recyclerView = findViewById(R.id.mainView);
        loadingBar= findViewById(R.id.loadingCircle);

        newsList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        newsAdapter = new SportsNewsAdapter( this, newsList);
        recyclerView.setAdapter(newsAdapter);

        // ConnectivityManager - check connection to internet (get info about connection of not null)
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;

        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager adn initialize with chosen ID
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_NEWS_ID, null, this);
        } else {

            //If have not connection with internet
            loadingBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.internetConnectionMessage);
        }
    }

    @Override
    public Loader<List<SportNewsModel>> onCreateLoader(int i, Bundle bundle) {

        return new SportNewsLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<SportNewsModel>> loader, List<SportNewsModel> myNewsList) {

        loadingBar.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.nothingToShowMessage);
        newsAdapter.clearAllData();

        if (myNewsList != null && !myNewsList.isEmpty()) {

            mEmptyStateTextView.setVisibility(View.GONE);
            newsAdapter.addAllData(myNewsList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<SportNewsModel>> loader) {
        newsAdapter.clearAllData();
    }
}
