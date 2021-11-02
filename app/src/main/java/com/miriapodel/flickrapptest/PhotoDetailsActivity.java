package com.miriapodel.flickrapptest;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.miriapodel.flickrapptest.databinding.ActivityPhotoDetailsBinding;

public class PhotoDetailsActivity extends BaseActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPhotoDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPhotoDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       activateToolbar(true);

       Intent intent = getIntent();

       Photo photoItem = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

       if(photoItem != null)
       {
           TextView imageTitle = findViewById(R.id.image_title);
           imageTitle.setText(photoItem.getTitle());

           TextView image_author = findViewById(R.id.image_author);
           image_author.setText(photoItem.getAuthor());

           TextView image_tags = findViewById(R.id.image_tags);
           image_tags.setText(photoItem.getTags());

           ImageView imageView = findViewById(R.id.imageView);
           Glide.with(this).asBitmap().load(photoItem.getLink()).into(imageView);
       }

    }

}