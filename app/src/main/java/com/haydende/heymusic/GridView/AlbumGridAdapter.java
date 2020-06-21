package com.haydende.heymusic.GridView;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haydende.heymusic.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>Album</code>
 * data for use in UI components.
 */
public class AlbumGridAdapter extends RecyclerView.Adapter<AlbumGridAdapter.AlbumViewHolder>
implements GridAdapter {


    private Cursor mediaStoreCursor;

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);

    private final AppCompatActivity activity;

    public AlbumGridAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            Log.i("AlbumGridAdapter", "Returning on worker thread");
            return new AlbumViewHolder(threadPool.submit(() -> inflate(parent, viewType)).get());
        } catch (ExecutionException | InterruptedException e) {
            // e.printStackTrace();
        }
        Log.i("AlbumGridAdapter", "Returning on main thread");
        return new AlbumViewHolder(inflate(parent, viewType));
    }

    public View inflate(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        String title = null;
        try {
            // bitmap = threadPool.submit(() -> getAlbumCover(position)).get();
            Glide.with(activity)
                    .load(getAlbumCoverUri(position))
                    .centerCrop()
                    .override(320,320)
                    .into(holder.getImageButton());
            title = threadPool.submit(() -> getAlbumName(position)).get();
        } catch (ExecutionException e) {
            // e.printStackTrace();
        } catch (InterruptedException e) {
            // e.printStackTrace();
        } catch (NullPointerException npe) {
            Log.i("AlbumGridAdapter", "No album art found");
        }
        // if (bitmap != null) holder.getImageButton().setImageBitmap(bitmap);
        holder.getTextView().setText((title == null) ? "Unknown" : title);
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
    private Uri getAlbumCoverUri(int position) {
        // try {
            Log.d("AlbumGridAdapter", "Starting method... ");
            mediaStoreCursor.moveToPosition(position);
            String albumArt = mediaStoreCursor.getString(
                    mediaStoreCursor.getColumnIndex(
                            "album_art"
                    )
            );
            Log.i("AlbumGridAdapter", String.format("Cover art for album %d: %s", position, albumArt));
            // Bitmap cover = null;
            return Uri.fromFile(new File(albumArt));
            /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                Log.i("AlbumGridAdapter", "Recognised Android Q");
                cover = activity.getApplicationContext().getContentResolver().loadThumbnail(
                        getUri(position),
                        new Size(200, 200),
                        null
                );
            } else {
                cover = threadPool.submit(() -> BitmapFactory.decodeFile(albumArt)).get();
            }
            Log.i("AlbumGridAdapter", "Returning loaded: " +
                    ((cover == null) ? "null" : cover.toString()));
            return cover;
        } catch (IllegalStateException | IOException e) {
            // e.printStackTrace();
            Log.i("AlbumGridAdapter", "Returning null");
            return null;
        } catch (InterruptedException | ExecutionException e) {
            // e.printStackTrace();
            Log.i("AlbumGridAdapter", "Returning null");
            return null;
        }*/

    }

    /**
     * Method for getting the album cover {@link Uri} for the album retrieved from {@code mediaStoreCursor}.
     * @param position The position in the Cursor table for the album item
     * @return Album cover Uri for the image corresponding to albumID
     */
    private Uri getUri(int position) {
        mediaStoreCursor.moveToPosition(position);
        return Uri.parse(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI +
                        "/" +
                mediaStoreCursor.getString(
                        mediaStoreCursor.getColumnIndex(
                                MediaStore.Audio.Albums._ID
                        )
                )
        );
    }

    public String toString() {
        return "AlbumGridAdapter";
    }
}
