package com.miriapodel.flickrapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;

    private TextView noPhotos;

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

        noPhotos = findViewById(R.id.no_photo);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String searchTag = sharedPreferences.getString(FLICKR_QUERY, "");

        if(searchTag.length() > 0)
        {
            GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData("en-us", "https://www.flickr.com/services/feeds/photos_public.gne", true, this );
            getFlickrJsonData.execute(searchTag);

            noPhotos.setVisibility(View.INVISIBLE);
        }
        else
        {
            noPhotos.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "onResume: ends");
    }

    @Override
    public void onDataAvailable(ArrayList<Photo> photos, DownloadStatus status)
    {
        if(status == DownloadStatus.OK)
        {
            Log.d(TAG, "onDownloadComplete: The data is: " + photos.toString());

            adapter.setPhotos(photos);

            if(adapter.getItemCount() > 0)
            {
                noPhotos.setVisibility(View.INVISIBLE);
            }
            else
            {
                noPhotos.setVisibility(View.VISIBLE);
            }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if(itemID == R.id.search_menu)
        {
            Intent intent = new Intent(this, SearchActivity.class);

            startActivity(intent);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }
}