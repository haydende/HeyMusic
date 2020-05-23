package com.haydende.heymusic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder> {

    private final HashMap<String, String> attributes;

    private final Bitmap coverArt;

    private final Activity activity;

    public NowPlayingAdapter(Activity activity, HashMap attributes, Bitmap coverArt) {
        this.activity = activity;
        this.attributes = attributes;
        this.coverArt = coverArt;
    }

    @NonNull
    @Override
    public NowPlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.now_playing_item, parent, false);
        return new NowPlayingAdapter.NowPlayingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingViewHolder holder, int position) {
        holder.albumCover.setImageBitmap(coverArt);
        holder.trackName.setText(attributes.get("Title"));
        holder.albumName.setText(attributes.get("Album"));
        holder.artistName.setText(attributes.get("Artist"));
        // holder.fileFormat.setText("format");
        // holder.bitRate.setText("bitrate");
        // holder.bitDepth.setText("bitDepth");
        // holder.sampleRate.setText("sampleRate");
    }

    @Override
    public int getItemCount() {
        return attributes == null ? 0 : 1;
    }

    public static class NowPlayingViewHolder extends RecyclerView.ViewHolder {

        public final ImageButton albumCover;
        public final SeekBar seekBar;
        public final TextView trackName;
        public final TextView albumName;
        public final TextView artistName;
        public final TextView fileFormat;
        public final TextView bitRate;
        public final TextView bitDepth;
        public final TextView sampleRate;

        public NowPlayingViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.nowPlayingItem_coverImage);
            seekBar = itemView.findViewById(R.id.nowPlayingItem_seekBar);
            trackName = itemView.findViewById(R.id.nowPlayingItem_trackName);
            albumName = itemView.findViewById(R.id.nowPlayingItem_albumName);
            artistName = itemView.findViewById(R.id.nowPlayingItem_artistName);
            fileFormat = itemView.findViewById(R.id.nowPlayingItem_format);
            bitRate = itemView.findViewById(R.id.nowPlayingItem_bitRate);
            bitDepth = itemView.findViewById(R.id.nowPlayingItem_bitDepth);
            sampleRate = itemView.findViewById(R.id.nowPlayingItem_sampleRate);
        }
    }
}
