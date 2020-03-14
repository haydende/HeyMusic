package com.haydende.heymusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>Song</code>
 * data for use in UI components.
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    /**
     * Subclass of <code>RecyclerView</code>.<code>ViewHolder</code>
     */
    class SongViewHolder extends RecyclerView.ViewHolder {

        /**
         * Will be used for reference to the <code>ImageButton</code> in res/layout/song_item.xml
         */
        private final ImageButton image;

        /**
         * Will be used for reference to the <code>TextView</code> in res/layout/song_item.xml
         */
        private final TextView text;

        /**
         * Default constructor for this class. Assigns the UI components using their <code>id</code>
         * attributes.
         * @param itemView
         */
        private SongViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
        }
    }

    /**
     * <code>LayoutInflater</code> object for adding layout(s) to the main UI.
     */
    private final LayoutInflater layoutInflater;

    /**
     * List of <code>Song</code> objects from the Database.
     */
    private List<Song> songs;

    /**
     * Default constructor for this class. Assigns a <code>LayoutInflater</code> to
     * <code>layoutInflater</code> using a <code>Context</code>.
     * @param context Used to get context for the UI components
     */
    SongListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Method for creating <code>ViewGroups</code> of Song_item.xml layout.
     * @param parent Parent layout for the <code>ViewGroup</code>
     * @param viewType ?
     * @return
     */
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(itemView);
    }

    /**
     * Method for setting the values for UI components.
     * @param holder <code>SongViewHolder</code> instance containing the UI components to work on
     * @param position <code>Integer</code> index for <code>List</code> of <code>Song</code> objects.
     */
    public void onBindViewHolder(SongViewHolder holder, int position) {
        if (songs != null) {
            Song current = songs.get(position);
            holder.text.setText(current.getTitle());
        } else {
            holder.text.setText("Nothing");
        }
    }

    /**
     * Method for updating the <code>List</code> of <code>Song</code> objects.
     * @param songs <code>List</code> of <code>Song</code> objects
     */
    void setSongs(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    /**
     * Method for returning the number of <code>Song</code> objects in the <code>List</code>.
     * @return <code>songs</code>.<code>size</code>()
     */
    @Override
    public int getItemCount() {
        if (songs != null) {
            return songs.size();
        } else {
            return 0;
        }
    }
}
