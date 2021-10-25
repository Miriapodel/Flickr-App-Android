package com.miriapodel.flickrapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute("https://www.flickr.com/services/feeds/photos_public.gne?format=json&jsoncallback=1");

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status)
    {
        if(status == DownloadStatus.OK)
        {
            Log.d(TAG, "onDownloadComplete: The data is: " + data);
        }
        else
        {
            Log.e(TAG, "onDownloadComplete: Unable to complete download: " + status);
        }
    }
}