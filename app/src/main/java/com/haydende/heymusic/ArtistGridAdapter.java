package com.haydende.heymusic;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>artist</code>
 * data for use in UI components.
 */
public class ArtistGridAdapter extends RecyclerView.Adapter<ArtistGridAdapter.ArtistViewHolder> {

    private Cursor mediaStoreCursor;

    private final Activity activity;

    public ArtistGridAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_item, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        // Bitmap bitmap = getAlbumCover(position);
        //if (bitmap != null) {
        // holder.getImageButton().setImageBitmap(bitmap);
        holder.getTextView().setText(getArtistName(position));
        //}
    }

    @Override
    public int getItemCount() {
        return (mediaStoreCursor == null) ? 0 : mediaStoreCursor.getCount();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {

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
        public ArtistViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.artistLayout_imageButton);
            textView = itemView.findViewById(R.id.artistLayout_textView);
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

    private String getArtistName(int position) {
        mediaStoreCursor.moveToPosition(position);
        return mediaStoreCursor.getString(
                mediaStoreCursor.getColumnIndex(
                        MediaStore.Audio.Artists.ARTIST
                )
        );
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
