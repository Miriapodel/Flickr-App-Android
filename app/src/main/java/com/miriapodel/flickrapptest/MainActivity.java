package com.miriapodel.flickrapptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;

    private FlickrRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar(false);

        initVar();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        Log.d(TAG, "onCreate: ends");
    }

    private void initVar()
    {
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new FlickrRecyclerViewAdapter(this);
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

            adapter.setPhotos(photos);
        }
        else
        {
            Log.e(TAG, "onDownloadComplete: Unable to complete download: " + status);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Log.d(TAG, "onItemClick: starts");

        Toast.makeText(this, "Normal tap at position: " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongClick(View view, int position) {

        Log.d(TAG, "onItemLongClick: starts");

        Toast.makeText(this, "Long tap at position: " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PhotoDetailsActivity.class);
        intent.putExtra(PHOTO_TRANSFER, adapter.getItem(position));

        startActivity(intent);

    }
}