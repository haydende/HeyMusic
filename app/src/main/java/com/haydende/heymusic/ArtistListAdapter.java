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
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>artist</code>
 * data for use in UI components.
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder> {

    /**
     * Subclass of <code>RecyclerView</code>.<code>ViewHolder</code>
     */
    class ArtistViewHolder extends RecyclerView.ViewHolder {

        /**
         * Will be used for reference to the <code>ImageButton</code> in res/layout/artist_item.xml
         */
        private final ImageButton image;

        /**
         * Will be used for reference to the <code>TextView</code> in res/layout/artist_item.xml
         */
        private final TextView text;

        /**
         * Default constructor for this class. Assigns the UI components using their <code>id</code>
         * attributes.
         * @param itemView
         */
        private ArtistViewHolder(View itemView) {
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
     * List of <code>artist</code> objects from the Database.
     */
    private List<Artist> artists;

    /**
     * Default constructor for this class. Assigns a <code>LayoutInflater</code> to
     * <code>layoutInflater</code> using a <code>Context</code>.
     * @param context Used to get context for the UI components
     */
    ArtistListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Method for creating <code>ViewGroups</code> of artist_item.xml layout.
     * @param parent Parent layout for the <code>ViewGroup</code>
     * @param viewType ?
     * @return
     */
    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.artist_item, parent, false);
        return new ArtistViewHolder(itemView);
    }

    /**
     * Method for setting the values for UI components.
     * @param holder <code>artistViewHolder</code> instance containing the UI components to work on
     * @param position <code>Integer</code> index for <code>List</code> of <code>artist</code> objects.
     */
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        if (artists != null) {
            Artist current = artists.get(position);
            holder.text.setText(current.getName());
        } else {
            holder.text.setText("Nothing");
        }
    }

    /**
     * Method for updating the <code>List</code> of <code>Artist</code> objects.
     * @param artists <code>List</code> of <code>Artist</code> objects
     */
    void setArtists(List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }

    /**
     * Method for returning the number of <code>Artist</code> objects in the <code>List</code>.
     * @return <code>artists</code>.<code>size</code>()
     */
    @Override
    public int getItemCount() {
        if (artists != null) {
            return artists.size();
        } else {
            return 0;
        }
    }
}
