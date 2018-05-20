package com.example.android.sportsnewsapp;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class SportNewsQuery {

    private static final String LOG_DATA = SportNewsQuery.class.getSimpleName();

    public SportNewsQuery() {
    }

    public static List<SportNewsModel> getSportNewsData(String requestUrl) {

        //TODO convert string to url
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            //TODO convert url to JSON
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_DATA, "HTTP request not possible", e);
        }

        // TODO return a list extracted from JSON
        return extractFeatureFromJson(jsonResponse);
    }

    //TODO return URL from string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_DATA, "Can't create URL", e);
        }
        return url;
    }

    //TODO convert URL JSON to string
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        if (url == null) {
            return jsonResponse;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //TODO request successful - send response code 200 - go to stream and read data
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_DATA, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_DATA, "Problem retrieving the sport news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //TODO convert JSON data into string and return a list with items
    private static List<SportNewsModel> extractFeatureFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<SportNewsModel> newsList = new ArrayList<>();

        try {
            JSONObject baseJsonRoot = new JSONObject(newsJSON);
            //TODO get JSON
            JSONObject newsObjectResponse = baseJsonRoot.getJSONObject("response");
            JSONArray newsArrayResults = newsObjectResponse.getJSONArray("results");

            //TODO loop the results array
            for (int i = 0; i < newsArrayResults.length(); i++) {

                JSONObject currentSportNewsData = newsArrayResults.getJSONObject(i);

                //TODO get string data
                String newsTitle = currentSportNewsData.getString("webTitle");
                String newsDate = currentSportNewsData.getString("webPublicationDate");
                String newsSection = currentSportNewsData.getString("sectionName");
                String urnewsAuthor = "unknown";

                //TODO add title if existing
                JSONArray sportNewsArrayTags = currentSportNewsData.getJSONArray("tags");
                if (sportNewsArrayTags != null && sportNewsArrayTags.length() > 0) {
                    JSONObject currentSportNewsDataTags = sportNewsArrayTags.getJSONObject(0);
                    urnewsAuthor = currentSportNewsDataTags.getString("webTitle");
                }

                String newsUrl = currentSportNewsData.getString("webUrl");

                //TODO add data to the list
                newsList.add(new SportNewsModel(newsTitle, newsSection, urnewsAuthor, newsDate, newsUrl));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the publications JSON result", e);
        }

        return newsList;
    }
}