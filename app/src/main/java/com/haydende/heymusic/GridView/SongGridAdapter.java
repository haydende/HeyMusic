package com.haydende.heymusic.GridView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haydende.heymusic.NowPlaying.NowPlayingActivity;
import com.haydende.heymusic.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>Song</code>
 * data for use in UI components.
 */
public class SongGridAdapter extends RecyclerView.Adapter<SongGridAdapter.SongViewHolder>
implements GridAdapter {

    /**
        Cursor instance used for accessing the results of a MediaStore query.
     */
    private Cursor mediaStoreCursor;

    /**
     * Instance of Activity that this class has been created in.
     */
    private final Activity activity;

    /**
     * Default constructor for this class.
     * @param activity Activity instance this class was created in
     */
    public SongGridAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public SongGridAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new SongGridAdapter.SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongGridAdapter.SongViewHolder holder, int position) {
        holder.getImageButton().setOnClickListener(v -> {
            Intent nowPlayingActivity = new Intent(activity, NowPlayingActivity.class);

            nowPlayingActivity.putExtra("uri", getUri(position));
            nowPlayingActivity.putExtra("name", getSongName(position));
            nowPlayingActivity.putExtra("album_id", getAlbumID(position));
            nowPlayingActivity.putExtra("album_name", getAlbumName(position));
            nowPlayingActivity.putExtra("artist_name", getArtistName(position));
            nowPlayingActivity.putExtra("data", getData(position));
            activity.startActivity(nowPlayingActivity);

        });
        Glide.with(activity)
                .load(Uri.parse("content://media/external/audio/albumart/" + getAlbumID(position)))
                .centerCrop()
                .override(320,320)
                .into(holder.getImageButton());
        Log.i("SongGridAdapter", "Album cover has been applied");
        holder.getTextView().setText(getSongName(position));

    }

    @Override
    public int getItemCount() {
        return (mediaStoreCursor == null) ? 0 : mediaStoreCursor.getCount();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        /**
         * {@link ImageButton} view for {@code albumLayout_imageButton}
         */
        private final ImageButton imageButton;

        /**
         * {@link TextView} view for {@code albumLayout_text}
         */
        private final TextView textView;

        /**
         * Default constructor for this class.
         * <p>
         *     Sets the {@code imageButton} and {@code textView} members.
         * </p>
         * @param itemView The {@link View} that will be hosting the layout
         */
        public SongViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.gridItem_imageButton);
            textView = itemView.findViewById(R.id.gridItem_textView);
        }

        /**
         * Accessor for {@code imageButton}.
         * @return {@code imageButton}
         */
        public ImageButton getImageButton() { return this.imageButton; }

        /**
         * Accessor for {@code textView}.
         * @return {@code textView}
         */
        public TextView getTextView() { return this.textView; }
    }

    /**
     * Private method for getting the Song Title from the {@link this#mediaStoreCursor}
     * @param position Integer position for the mediaStoreCursor to point to
     * @return String song title
     *
     * @see Cursor
     * @see MediaStore
     */
    private String getSongName(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Media.TITLE
                )
        );
    }

    /**
     * Private method for getting the Album Title from the {@link this#mediaStoreCursor}
     * @param position Integer position for the mediaStoreCursor to point to
     * @return String album title
     *
     * @see Cursor
     * @see MediaStore
     */
    private String getAlbumName(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Media.ALBUM
                )
        );
    }

    /**
     * Private method for getting the Artist Name from the {@link this#mediaStoreCursor}
     * @param position Integer position for the mediaStoreCursor to point to
     * @return String artist name
     *
     * @see Cursor
     * @see MediaStore
     */
    private String getArtistName(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Media.ARTIST
                )
        );
    }

    /**
     * Method for getting the album cover as a {@link Bitmap} image.
     * @param position Position for the mediaStoreCursor to look in
     * @return Album cover to be used for ImageButton image
     */
    private String getAlbumID(int position) {
        Log.d("getAlbumCover", "Starting method... ");
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Media.ALBUM_ID
                )
        );
    }

    /**
     * Method for getting the {@link Uri} for the track at position.
     * @param position position for mediaStoreCursor to move to
     * @return Uri object referencing the URI for the track at position in the mediaStoreCursor
     */
    private Uri getUri(int position) {
        mediaStoreCursor.moveToPosition(position);
        // get Uri for VOLUME_NAME (this will get the one for this specific file,
        // making it adaptable)
        Uri fileUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // replace Uri value with old value + /_ID (to make it point to the specific track)
        fileUri = Uri.parse(
                fileUri.toString()
                + "/"
                + mediaStoreCursor.getString(
                    mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Media._ID
                    )
                )
        );
        Log.d("fileUri ", fileUri.toString());
        return fileUri;
    }

    /**
     * Method for getting the DATA column header from the mediaStoreCursor for the track at
     * {@code position}
     *
     * @param position int position for the record to go to in the mediaStoreCursor
     * @return String contents of the DATA column in the mediaStoreCursor
     */
    private String getData(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.AudioColumns.DATA
                )
        );
    }

    /**
     * Internal method for swapping the Cursor object in use.
     * <p>
     *     Checks if the new Cursor is the same as the old one so that
     *     {@link this#changeCursor(Cursor)} can decide whether to close it or not.
     * </p>
     *
     * @param cursor New Cursor object to swap to
     * @return The old Cursor if the new one is in fact new; null if they're the same
     */
    private Cursor swapCursor(Cursor cursor) {
        if (mediaStoreCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mediaStoreCursor;
        this.mediaStoreCursor = cursor;
        if (mediaStoreCursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    /**
     * Method for setting the new Cursor to use. Calls {@link this#swapCursor(Cursor)} to determine
     * whether whether the new cursor is actually new.
     *
     * @param cursor New Cursor that has been provided
     */
    public void changeCursor(Cursor cursor) {
        Cursor oldCursor = swapCursor(cursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }
}
