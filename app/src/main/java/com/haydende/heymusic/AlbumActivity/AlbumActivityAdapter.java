package com.haydende.heymusic.AlbumActivity;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumActivityAdapter extends RecyclerView.Adapter<AlbumActivityAdapter.AlbumActivityViewHolder> {
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
        return 0;
    }

    class AlbumActivityViewHolder extends RecyclerView.ViewHolder {

        public AlbumActivityViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
