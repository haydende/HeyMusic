package com.haydende.heymusic.AlbumActivity;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OtherAlbumAdapter extends RecyclerView.Adapter<OtherAlbumAdapter.OtherAlbumViewHolder> {
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

    class OtherAlbumViewHolder extends RecyclerView.ViewHolder {

        public OtherAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
