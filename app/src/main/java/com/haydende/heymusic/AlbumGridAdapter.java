package com.haydende.heymusic;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>Album</code>
 * data for use in UI components.
 */
public class AlbumGridAdapter extends RecyclerView.Adapter<AlbumGridAdapter.AlbumViewHolder> {

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
        // Bitmap bitmap = getAlbumCover(position);
        //if (bitmap != null) {
            // holder.getImageButton().setImageBitmap(bitmap);
            holder.getTextView().setText("Hello world");
        //}
    }

    @Override
    public int getItemCount() {
        return (mediaStoreCursor == null) ? 0 : mediaStoreCursor.getCount();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

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
        public AlbumViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.albumLayout_imageButton);
            textView = itemView.findViewById(R.id.albumLayout_text);
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

    private Bitmap getAlbumCover(int position) {
        int idIndex = mediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        int mediaTypeIndex = mediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
        try {
            mediaStoreCursor.moveToPosition(position);
            return MediaStore.Images.Media.getBitmap(
                    activity.getContentResolver(),
                    Uri.parse(
                            mediaStoreCursor.getString(
                                    mediaStoreCursor.getColumnIndex(
                                            MediaStore.Audio.Albums.ALBUM_ART
                                    )
                            )
                    )
            );
        } catch (IOException ioE) {
            return null;
        }

    }
}
