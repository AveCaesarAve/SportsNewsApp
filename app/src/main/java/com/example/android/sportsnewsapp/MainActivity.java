package com.example.android.sportsnewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<SportNewsModel>> {

    RecyclerView mRecyclerView;
    SportsNewsAdapter mSportsNewsAdapter;
    ArrayList<SportNewsModel> mNewsList;
    private TextView mEmptyStateTextView;
    private View mLoadingCircle;
    private static final String REQUEST_URL = "http://content.guardianapis.com/search?show-tags=contributor&section=sport&q=cycling&api-key=f56abef9-94f7-4fad-b92f-c9a26b5f37c2";
    private static final int LOADER_NEWS_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView =findViewById(R.id.emptyView);
        mRecyclerView = findViewById(R.id.mainView);
        mLoadingCircle = findViewById(R.id.loadingCircle);
        mNewsList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSportsNewsAdapter = new SportsNewsAdapter( this, mNewsList);
        mRecyclerView.setAdapter(mSportsNewsAdapter);

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
            mLoadingCircle.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.noInternetMessage);
        }
    }

    // onCreateLoader
    @Override
    // onCreateLoader instantiates and returns a new Loader for the given ID
    public Loader<List<SportNewsModel>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_article_category_key),
                getString(R.string.settings_article_category_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value
        uriBuilder.appendQueryParameter("section", "sports");
        uriBuilder.appendQueryParameter("q", "cycling");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new SportNewsLoader(this, uriBuilder.toString());
    }

    // onLoadFinished
    @Override
    public void onLoadFinished(Loader<List<SportNewsModel>> loader, List<SportNewsModel> myNewsList) {

        mLoadingCircle.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.noDataToShowMessage);
        mSportsNewsAdapter.clearAllData();

        if (myNewsList != null && !myNewsList.isEmpty()) {
            mEmptyStateTextView.setVisibility(View.GONE);
            mSportsNewsAdapter.addAllData(myNewsList);
        }
    }

    // onLoaderReset
    @Override
    public void onLoaderReset(Loader<List<SportNewsModel>> loader) {
        mSportsNewsAdapter.clearAllData();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
