package com.haydende.heymusic.GridAdapter;

import android.app.Activity;
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

import com.haydende.heymusic.R;

import java.io.IOException;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>Album</code>
 * data for use in UI components.
 */
public class AlbumGridAdapter extends RecyclerView.Adapter<AlbumGridAdapter.AlbumViewHolder>
implements GridAdapter {

    private Cursor mediaStoreCursor;

    private final Activity activity;

    public AlbumGridAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Bitmap bitmap = getAlbumCover(position);
        if (bitmap != null) {
            holder.getImageButton().setImageBitmap(bitmap);
        }
        holder.getTextView().setText(getAlbumName(position));
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
            imageButton = itemView.findViewById(R.id.albumLayout_imageButton);
            textView = itemView.findViewById(R.id.albumLayout_text);
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

    /**
     * Method for getting the album cover as a {@link Bitmap} image.
     * @param position Position for the mediaStoreCursor to look in
     * @return Album cover to be used for ImageButton image
     */
    private Bitmap getAlbumCover(int position) {
        int idIndex = mediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        int mediaTypeIndex = mediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
        try {
            Log.d("getAlbumCover", "Starting method... ");
            mediaStoreCursor.moveToPosition(position);
            Bitmap cover = MediaStore.Images.Media.getBitmap(
                    activity.getContentResolver(),
                    getAlbumUri(
                            mediaStoreCursor.getString(
                                    mediaStoreCursor.getColumnIndex(
                                        MediaStore.Audio.Albums._ID
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
}
