package com.miriapodel.flickrapptest;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    OnRecyclerClickListener callBack;

    GestureDetectorCompat gestureDetector;

    interface OnRecyclerClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, RecyclerView recyclerView, OnRecyclerClickListener callBack) {
        this.callBack = callBack;

        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                Log.d(TAG, "onSingleTapUp: starts");

                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if(callBack != null && childView != null)
                {
                    callBack.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }

                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

                Log.d(TAG, "onLongPress: starts");

                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if(callBack != null && childView != null)
                {
                    callBack.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        if(gestureDetector != null)
        {
            boolean result = gestureDetector.onTouchEvent(e);

            Log.d(TAG, "onInterceptTouchEvent: the result was: " + result);

            return result;
        }
        else
        {
            Log.d(TAG, "onInterceptTouchEvent: returns false");

            return false;
        }

    }
}
