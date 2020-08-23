package com.haydende.heymusic.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haydende.heymusic.Activity.AlbumActivity;
import com.haydende.heymusic.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Subclass of <code>RecyclerView</code>.<code>com.haydende.heymusic.Adapter</code> that adapts the <code>Album</code>
 * data for use in UI components.
 */
public class AlbumGridAdapter extends RecyclerView.Adapter<AlbumGridAdapter.AlbumViewHolder>
implements GridAdapter {


    private Cursor mediaStoreCursor;

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);

    private final AppCompatActivity activity;

    public AlbumGridAdapter(AppCompatActivity activity) {
        Log.i("AlbumGridAdapter", "I have been called");
        this.activity = activity;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        String title = null;
        Intent albumActivity = new Intent(activity, AlbumActivity.class);
        try {
            Glide.with(activity)
                    .load(threadPool.submit(() -> getAlbumCover(position)).get())
                    .centerCrop()
                    .override(320,320)
                    .into(holder.getImageButton());
            title = threadPool.submit(() -> getAlbumName(position)).get();
        } catch (ExecutionException | InterruptedException e) {
            // e.printStackTrace();
        } catch (NullPointerException npe) {
            Log.i("AlbumGridAdapter", "No album art found");
        }
        holder.getTextView().setText((title == null) ? "Unknown" : title);

        holder.getImageButton().setOnClickListener((View v) -> {
            albumActivity.putExtra(
                    "album_id",
                    getAlbumID(position)
            );
            albumActivity.putExtra(
                    "album_cover",
                    getAlbumCover(position)
            );
            albumActivity.putExtra(
                    "album_name",
                    getAlbumName(position)
            );
            albumActivity.putExtra(
                    "artist_name",
                    getArtistName(position)
            );
            albumActivity.putExtra(
                    "first_year",
                    getFirstYear(position)
            );
            albumActivity.putExtra(
                    "num_songs",
                    getNumOfTracks(position)
            );
            activity.startActivity(albumActivity);
        });
    }

    @Override
    public int getItemCount() {
        return (mediaStoreCursor == null) ? 0 : mediaStoreCursor.getCount();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        /**
         * {@link ImageButton} value for representing {@code albumLayout_imageButton}
         */
        private final ImageButton imageButton;

        /**
         * {@link TextView} value for representing {@code albumLayout_text}
         */
        private final TextView textView;

        /**
         * Default constructor for this class.
         * <p>
         *     Sets the {@code imageButton} and {@link #textView} members.
         * </p>
         * @param itemView The {@link View} that will be hosting the layout
         */
        public AlbumViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.gridItem_imageButton);
            textView = itemView.findViewById(R.id.gridItem_textView);
        }

        /**
         * Accessor for {@link #imageButton}.
         * @return {@link #imageButton}
         */
        public ImageButton getImageButton() { return this.imageButton; }

        /**
         * Accessor for {@link #textView}.
         * @return {@link #textView}
         */
        public TextView getTextView() { return this.textView; }
    }

    /**
     * Method for providing a new {@link Cursor} value to be used by {@link #mediaStoreCursor}.
     * @param cursor New {@link Cursor} value for {@link #mediaStoreCursor}
     * @return Old {@link Cursor} value to be closed
     */
    private Cursor swapCursor(Cursor cursor) {
        if (mediaStoreCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mediaStoreCursor;
        this.mediaStoreCursor = cursor;
        if (mediaStoreCursor != null) {
            this.notifyDataSetChanged();
            Log.i("AlbumGridAdapter", "My Cursor has been changed");
        }
        return oldCursor;
    }

    /**
     * Method for finalising the change of {@link Cursor} value in {@link #mediaStoreCursor}.
     * @param cursor Old {@link Cursor} that needs to be closed
     */
    public void changeCursor(Cursor cursor) {
        Cursor oldCursor = swapCursor(cursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }

    private String getAlbumID(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Albums._ID
                )
        );
    }

    /**
     * Method for getting the name for the album at the specified position in the {@link #mediaStoreCursor}.
     * @param position position for the {@link #mediaStoreCursor} to look in
     * @return Album name to be used for {@link AlbumViewHolder#textView}
     */
    private String getAlbumName(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Albums.ALBUM
                )
        );
    }

    private String getArtistName(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Albums.ARTIST
                )
        );
    }

    /**
     * Method for getting the album cover as a {@link Uri}.
     * @param position Position for the mediaStoreCursor to look in
     * @return Album cover to be used for ImageButton image
     */
    private Uri getAlbumCover(int position) {
        Log.d("AlbumGridAdapter", "Starting method... ");
        mediaStoreCursor.moveToPosition(position);
        String albumID = mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Albums._ID
                )
        );
        Uri albumUri = Uri.parse(String.format("content://media/external/audio/albumart/%s", albumID));
        Log.i("AlbumGridAdapter", String.format("Cover art for album %d: %s", position, albumUri));
        return albumUri;
    }

    /**
     * Method for getting the first year value from the mediaStoreCursor.
     * @param position Position for the mediaStoreCursor to look in
     * @return first year of release value as a String
     */
    private String getFirstYear(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Albums.FIRST_YEAR
                )
        );
    }

    /**
     * Method for getting the number of tracks in this album.
     * @param position Position for the mediaStoreCursor to look in
     * @return Number of tracks as a String
     */
    private String getNumOfTracks(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Albums.NUMBER_OF_SONGS
                )
        );
    }

}
