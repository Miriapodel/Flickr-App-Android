package com.miriapodel.flickrapptest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.ViewHolder>
{
    private ArrayList<Photo> photos;

    private Context context;

    public FlickrRecyclerViewAdapter(Context context)
    {
        this.context = context;

        photos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Photo photo = photos.get(position);

        Glide.with(context).asBitmap().load(photo.getImage()).into(holder.thumbnail);

        holder.photo_title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        private static final String TAG = "ViewHolder";

        private ImageView thumbnail;

        private TextView photo_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d(TAG, "ViewHolder: starts");

            thumbnail = itemView.findViewById(R.id.thumbnail);

            photo_title = itemView.findViewById(R.id.photo_title);

            Log.d(TAG, "ViewHolder: ends");
        }
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;

        notifyDataSetChanged();
    }
}
