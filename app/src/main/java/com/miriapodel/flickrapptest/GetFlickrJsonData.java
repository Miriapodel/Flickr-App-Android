package com.miriapodel.flickrapptest;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;

public class GetFlickrJsonData implements GetRawData.OnDownloadComplete
{
    private static final String TAG = "GetFlickrJsonData";

    ArrayList<Photo> photos;

    String language;
    String baseURL;

    boolean matchAll;

    OnDataAvailable onDataAvailable;

    public GetFlickrJsonData(String language, String baseURL, boolean matchAll, OnDataAvailable onDataAvailable) {
        this.language = language;
        this.baseURL = baseURL;
        this.matchAll = matchAll;
        this.onDataAvailable = onDataAvailable;
    }

    interface OnDataAvailable
    {
        void onDataAvailable(ArrayList<Photo> photos, DownloadStatus status);
    }

    public void executeOnSameThread(String searchCriteria)
    {
        Log.d(TAG, "executeOnSameThread: starts");

        String destinationUri = createUri(searchCriteria, language, matchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);

        Log.d(TAG, "executeOnSameThread: ends");
    }

    public String createUri(String searchCriteria,  String language, boolean matchAll)
    {
        Log.d(TAG, "createUri: starts");

        return Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", language)
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status)
    {
        if(status == DownloadStatus.OK)
        {
            photos = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(data)
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject photo = jsonArray.getJSONObject(i);
                    JSONObject media = photo.getString("media");

                    String author = photo.getString("author");
                    String author_id = photo.getString("author_id");
                    String title = photo.getString("title");
                    String image = photo.getString("m");
                    String link = image.replaceFirst("_m.", "_b.");
                }
            }
        }
    }
}
