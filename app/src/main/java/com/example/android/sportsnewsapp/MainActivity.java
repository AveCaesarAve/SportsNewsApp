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
    SportsNewsAdapter sportsNewsAdapter;
    ArrayList<SportNewsModel> newsList;
    private TextView mEmptyStateTextView;
    private View loadingCircle;
    private static final String REQUEST_URL = "http://content.guardianapis.com/search?show-tags=contributor&section=sport&q=cycling&api-key=f56abef9-94f7-4fad-b92f-c9a26b5f37c2";
    private static final int LOADER_NEWS_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView =findViewById(R.id.emptyView);
        recyclerView = findViewById(R.id.mainView);
        loadingCircle = findViewById(R.id.loadingCircle);
        newsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sportsNewsAdapter = new SportsNewsAdapter( this, newsList);
        recyclerView.setAdapter(sportsNewsAdapter);

        // set-up the connectivity manager
        // ConnectivityManager - check connection to internet
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;

        if (networkInfo != null && networkInfo.isConnected()) {
            // get a reference to the LoaderManager and initialize with chosen ID
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_NEWS_ID, null, this);
        } else {
            // action if no connection to the internet is possible - message
            loadingCircle.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.noInternetMessage);
        }
    }

    // onCreateLoader
    @Override
    public Loader<List<SportNewsModel>> onCreateLoader(int i, Bundle bundle) {
        return new SportNewsLoader(this, REQUEST_URL);
    }

    // onLoadFinished
    @Override
    public void onLoadFinished(Loader<List<SportNewsModel>> loader, List<SportNewsModel> myNewsList) {

        loadingCircle.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.noDataToShowMessage);
        sportsNewsAdapter.clearAllData();

        if (myNewsList != null && !myNewsList.isEmpty()) {
            mEmptyStateTextView.setVisibility(View.GONE);
            sportsNewsAdapter.addAllData(myNewsList);
        }
    }

    // onLoaderReset
    @Override
    public void onLoaderReset(Loader<List<SportNewsModel>> loader) {
        sportsNewsAdapter.clearAllData();
    }
}
