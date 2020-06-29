package com.haydende.heymusic.AlbumActivity;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumActivityAdapter extends RecyclerView.Adapter<AlbumActivityAdapter.AlbumActivityViewHolder> {

    private Cursor mCursor;

    private Activity mActivity;

    public AlbumActivityAdapter (Activity activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public AlbumActivityAdapter.AlbumActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumActivityAdapter.AlbumActivityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public static class AlbumActivityViewHolder extends RecyclerView.ViewHolder {

        public AlbumActivityViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private Cursor swapCursor(Cursor newCursor) {
        return (newCursor == mCursor) ? null : mCursor;
    }

    public void changeCursor(Cursor newCursor) {
        Cursor oldCursor = swapCursor(newCursor);
        if (oldCursor != null) {
            oldCursor.close();
            mCursor = newCursor;
        }
    }
}
