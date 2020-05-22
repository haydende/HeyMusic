package com.haydende.heymusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>Song</code>
 * data for use in UI components.
 */
public class SongGridAdapter extends RecyclerView.Adapter<SongGridAdapter.SongViewHolder> {

    private Cursor mediaStoreCursor;

    private final Activity activity;

    public SongGridAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public SongGridAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);
        return new SongGridAdapter.SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongGridAdapter.SongViewHolder holder, int position) {
        Bitmap bitmap = getAlbumCover(position);
        if (bitmap != null) {
            holder.getImageButton().setImageBitmap(bitmap);
        }
        holder.getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            ArrayList<String> extraData = new ArrayList<>();
            Intent nowPlaying = new Intent(activity, NowPlayingActivity.class);

            // add track name to new activity
            nowPlaying.putExtra(
                    "TRACK_NAME",
                    mediaStoreCursor.getString(
                            mediaStoreCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                    )
            );

            // add album name
            nowPlaying.putExtra(
                    "ALBUM_NAME",
                    mediaStoreCursor.getString(
                            mediaStoreCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                    )
            );

            // add artist name
            nowPlaying.putExtra(
                    "ARTIST_NAME",
                    mediaStoreCursor.getString(
                            mediaStoreCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                    )
            );

            // add Album ID (used to get cover art)
            nowPlaying.putExtra(
                    "ALBUM_ID",
                    mediaStoreCursor.getString(
                            mediaStoreCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
                    )
            );

            // start the activity
            activity.startActivity(nowPlaying);
            }
        });
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
            imageButton = itemView.findViewById(R.id.songLayout_imageButton);
            textView = itemView.findViewById(R.id.songLayout_text);
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

    private String getSongName(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Media.TITLE
                )
        );
    }

    /**
     * Method for getting the album cover as a {@link Bitmap} image.
     * @param position Position for the mediaStoreCursor to look in
     * @return Album cover to be used for ImageButton image
     */
    private Bitmap getAlbumCover(int position) {
        try {
            Log.d("getAlbumCover", "Starting method... ");
            mediaStoreCursor.moveToPosition(position);
            Bitmap cover = MediaStore.Images.Media.getBitmap(
                    activity.getContentResolver(),
                    getAlbumUri(
                            mediaStoreCursor.getString(
                                    mediaStoreCursor.getColumnIndex(
                                            MediaStore.Audio.Media.ALBUM_ID
                                    )))
            );
            Log.d("getAlbumCover", "Returning bitmap");
            return cover;
        } catch (IOException ioE) {
            Log.d("getAlbumCover", "Returning null");
            return null;
        }
    }

    /**
     * Method for getting the album cover {@link Uri} for the album retrieved from {@code mediaStoreCursor}.
     * @param albumID Album ID value taken from mediaStoreCursor
     * @return Album cover Uri for the image corresponding to albumID
     */
    private Uri getAlbumUri(String albumID) {
        Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri imageUri = Uri.withAppendedPath(artworkUri, String.valueOf(albumID));
        return imageUri;
    }

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

    public void changeCursor(Cursor cursor) {
        Cursor oldCursor = swapCursor(cursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }
}
