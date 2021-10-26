package com.miriapodel.flickrapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetFlickrJsonData.OnDataAvailable {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();

        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData("en-us", "https://www.flickr.com/services/feeds/photos_public.gne", true, this );
        getFlickrJsonData.execute("android, nougat");

        Log.d(TAG, "onResume: ends");
    }

    @Override
    public void onDataAvailable(ArrayList<Photo> photos, DownloadStatus status)
    {
        if(status == DownloadStatus.OK)
        {
            Log.d(TAG, "onDownloadComplete: The data is: " + photos.toString());
        }
        else
        {
            Log.e(TAG, "onDownloadComplete: Unable to complete download: " + status);
        }
    }
}