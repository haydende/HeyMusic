package com.haydende.heymusic.Adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OtherAlbumAdapter extends RecyclerView.Adapter<OtherAlbumAdapter.OtherAlbumViewHolder> {

    private Cursor mCursor;

    private Activity mActivity;

    public OtherAlbumAdapter (Activity activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public OtherAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OtherAlbumViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class OtherAlbumViewHolder extends RecyclerView.ViewHolder {

        public OtherAlbumViewHolder(@NonNull View itemView) {
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
