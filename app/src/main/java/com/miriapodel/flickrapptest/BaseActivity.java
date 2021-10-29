package com.miriapodel.flickrapptest;

import android.app.Notification;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity
{
    private static final String TAG = "BaseActivity";
    public final String FLICKR_QUERY = "FLICKR_QUERY";
    public final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    public void activateToolbar(boolean enableHome)
    {
        Log.d(TAG, "activateToolbar: starts");

        ActionBar actionBar = getSupportActionBar();

        if(actionBar == null)
        {
            Toolbar toolbar = findViewById(R.id.toolbar);

            if(toolbar != null)
            {
                setSupportActionBar(toolbar);

                actionBar = getSupportActionBar();
            }
        }

        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}
