package com.miriapodel.flickrapptest;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;

public class GetFlickrJsonData extends AsyncTask<String, Void, ArrayList<Photo>> implements GetRawData.OnDownloadComplete
{
    private static final String TAG = "GetFlickrJsonData";

    ArrayList<Photo> photos;

    String language;
    String baseURL;

    boolean matchAll;
    boolean runningOnSameThread = false;

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

        runningOnSameThread = true;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);

        Log.d(TAG, "executeOnSameThread: ends");
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        Log.d(TAG, "onPostExecute: starts");

        if(onDataAvailable != null)
        {
            onDataAvailable.onDataAvailable(photos, DownloadStatus.OK);
        }

        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        
        String destinationUri = createUri(params[0], language, matchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.executeInSameThread(destinationUri);

        Log.d(TAG, "doInBackground: ends");

        return photos;
    }

    public String createUri(String searchCriteria, String language, boolean matchAll)
    {
        Log.d(TAG, "createUri: starts");

        return Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", language)
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("format", "json")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status)
    {
        if(status == DownloadStatus.OK)
        {
            photos = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject photo = jsonArray.getJSONObject(i);
                    JSONObject media = photo.getJSONObject("media");

                    String author = photo.getString("author");
                    String author_id = photo.getString("author_id");
                    String title = photo.getString("title");
                    String image = media.getString("m");
                    String link = image.replaceFirst("_m.", "_b.");
                    String tags = photo.getString("tags");

                    Photo photoObject = new Photo(author, author_id, image, link, title, tags);

                    photos.add(photoObject);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error parsing the Json data");
            }

            if(runningOnSameThread && onDataAvailable != null)
            {
                onDataAvailable.onDataAvailable(photos, status);
            }
        }
    }
}
