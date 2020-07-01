package com.haydende.heymusic.AlbumActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static android.provider.MediaStore.Audio.Media;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.haydende.heymusic.NowPlaying.NowPlayingActivity;
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
            Intent nowPlayingActivity = new Intent(mActivity, NowPlayingActivity.class);
            Log.i("SongListItem",
                    String.format(
                            "OnClickListener: Track %s has been clicked",
                            getTrackNumber(position)
                    )
            );

            nowPlayingActivity.putExtra("uri", getTrackUri(position));
            nowPlayingActivity.putExtra("name", getTrackName(position));
            nowPlayingActivity.putExtra("album_id", getAlbumID(position));
            nowPlayingActivity.putExtra("album_name", getAlbumName(position));
            nowPlayingActivity.putExtra("artist_name", getArtistName(position));
            mActivity.startActivity(nowPlayingActivity);
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

    /**
     * Method for getting the track number of this track from the MediaStore.
     *
     * @param position Position in the Cursor to move to
     * @return String track number of this track
     */
    private String getTrackNumber(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.TRACK
                )
        );
    }

    /**
     * Method for getting the name of this track from the MediaStore.
     *
     * @param position Position in the Cursor to move to
     * @return String name of this track
     */
    private String getTrackName(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.TITLE
                )
        );
    }

    /**
     * Method for getting the duration field for this track from the MediaStore.
     *
     * @param position Position in the Cursor to move to
     * @return String duration for this track (in milliseconds)
     */
    private String getTrackDuration(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.DURATION
                )
        );
    }

    /**
     * Method for getting the Uri for the track based on the EXTERNAL_CONTENT_URI and this
     * tracks ID field from the MediaStore.
     *
     * @param position Position in the Cursor to move to
     * @return Uri for the current track
     */
    private Uri getTrackUri(int position) {
        mCursor.moveToPosition(position);
        return Uri.parse(Media.EXTERNAL_CONTENT_URI + "/" +
                mCursor.getString(
                    mCursor.getColumnIndex(Media._ID)
                ));
    }

    /**
     * Method for getting the name of the album this track is in.
     *
     * @param position Position in the Cursor to move to
     * @return String album name
     */
    private String getAlbumName(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.ALBUM
                )
        );
    }

    /**
     * Method for getting the ID for the album this track is in.
     *
     * @param position Position in the Cursor to move to
     * @return String album ID
     */
    private String getAlbumID(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.ALBUM_ID
                )
        );
    }

    /**
     * Method for getting the name of the artist this track was composed by.
     *
     * @param position Position in the Cursor to move to
     * @return String artist name
     */
    private String getArtistName(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(
                mCursor.getColumnIndex(
                        Media.ARTIST
                )
        );
    }

    /**
     * Internal method for determining whether the new Cursor is the same and updating this class that
     * the data set has been changed.
     *
     * @param newCursor The new Cursor with Album data from MediaStore
     * @return The old cursor if the new one is not the same, null if it is
     */
    private Cursor swapCursor(Cursor newCursor) {
        if (mCursor == newCursor) return null;
        Cursor oldCursor = mCursor;
        this.mCursor = newCursor;
        if (mCursor != null) this.notifyDataSetChanged();
        return oldCursor;
    }

    /**
     * Public method for changing the Cursor being used by the instance of this class.
     *
     * @param newCursor The new Cursor object to potentially use for this adapter
     */
    public void changeCursor(Cursor newCursor) {
        Cursor oldCursor = swapCursor(newCursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }
}
