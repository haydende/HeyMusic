package com.haydende.heymusic.Adapter;

import android.app.Activity;
import android.database.Cursor;
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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Subclass of <code>RecyclerView</code>.<code>com.haydende.heymusic.Adapter</code> that adapts the <code>artist</code>
 * data for use in UI components.
 */
public class ArtistGridAdapter extends RecyclerView.Adapter<ArtistGridAdapter.ArtistViewHolder>
implements GridAdapter {

    private Cursor mediaStoreCursor;

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);

    private final Activity activity;

    public ArtistGridAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistGridAdapter.ArtistViewHolder holder, int position) {
        String title = null;
        try {
            /*
            Glide.with(activity)
                    .load(threadPool.submit(() -> // Need to find way to get artist image).get())
                    .centerCrop()
                    .override(320,320)
                    .into(holder.getImageButton());
             */
            title = threadPool.submit(() -> getArtistName(position)).get();
        } catch (ExecutionException e) {
            // e.printStackTrace();
        } catch (InterruptedException e) {
            // e.printStackTrace();
        } catch (NullPointerException npe) {
            Log.i("AlbumGridAdapter", "No album art found");
        }
        holder.getTextView().setText((title == null) ? "Unknown" : title);
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
