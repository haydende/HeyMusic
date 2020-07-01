package com.haydende.heymusic.AlbumActivity;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static android.provider.MediaStore.Audio.Media;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.haydende.heymusic.R;

public class AlbumActivityAdapter extends RecyclerView.Adapter<AlbumActivityAdapter.AlbumActivityViewHolder> {

    private Cursor mCursor;

    private Activity mActivity;

    public AlbumActivityAdapter (Activity activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public AlbumActivityAdapter.AlbumActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.song_list_item,
                        parent,
                        false
                );
        return new AlbumActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumActivityAdapter.AlbumActivityViewHolder holder, int position) {
        holder.getWholeLayout().setOnClickListener((View v) -> {
            Log.i("SongListItem",
                    String.format(
                            "OnClickListener: Track %s has been clicked",
                            getTrackNumber(position)
                    )
            );
        });
        holder.getTrackNumber().setText(getTrackNumber(position));
        holder.getTrackName().setText(getTrackName(position));
        holder.getTrackDuration().setText(getTrackDuration(position));
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public static class AlbumActivityViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout wholeLayout;

        private final TextView trackNumber;

        private final TextView trackName;

        private final TextView trackDuration;

        public AlbumActivityViewHolder(View itemView) {
            super(itemView);
            wholeLayout = itemView.findViewById(R.id.songListItem);
            trackNumber = itemView.findViewById(R.id.songListItem_trackNum);
            trackName = itemView.findViewById(R.id.songListItem_trackName);
            trackDuration = itemView.findViewById(R.id.songListItem_trackDuration);
        }

        public ConstraintLayout getWholeLayout() { return wholeLayout; }

        public TextView getTrackNumber() {
            return trackNumber;
        }

        public TextView getTrackName() {
            return trackName;
        }

        public TextView getTrackDuration() {
            return trackDuration;
        }

    }

    private String getTrackNumber(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.TRACK
                )
        );
    }

    private String getTrackName(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.TITLE
                )
        );
    }

    private String getTrackDuration(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.DURATION
                )
        );
    }

    private Cursor swapCursor(Cursor newCursor) {
        if (mCursor == newCursor) return null;
        Cursor oldCursor = mCursor;
        this.mCursor = newCursor;
        if (mCursor != null) this.notifyDataSetChanged();
        return oldCursor;
    }

    public void changeCursor(Cursor newCursor) {
        Cursor oldCursor = swapCursor(newCursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }
}
