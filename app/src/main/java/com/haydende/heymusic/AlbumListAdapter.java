package com.haydende.heymusic;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of <code>RecyclerView</code>.<code>Adapter</code> that adapts the <code>Album</code>
 * data for use in UI components.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder> {

    /**
     * Subclass of <code>RecyclerView</code>.<code>ViewHolder</code>
     */
    class AlbumViewHolder extends RecyclerView.ViewHolder {

        /**
         * Will be used for reference to the <code>ImageButton</code> in res/layout/album_item.xml
         */
        private final ImageButton image;

        /**
         * Will be used for reference to the <code>TextView</code> in res/layout/album_item.xml
         */
        private final TextView text;

        /**
         * Default constructor for this class. Assigns the UI components using their <code>id</code>
         * attributes.
         * @param itemView
         */
        private AlbumViewHolder(View itemView) {
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
     * List of <code>Album</code> objects from the Database.
     */
    private List<Album> albums;

    /**
     * Default constructor for this class. Assigns a <code>LayoutInflater</code> to
     * <code>layoutInflater</code> using a <code>Context</code>.
     * @param context Used to get context for the UI components
     */
    AlbumListAdapter(Context context) {
       layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Method for creating <code>ViewGroups</code> of album_item.xml layout.
     * @param parent Parent layout for the <code>ViewGroup</code>
     * @param viewType ?
     * @return
     */
    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(itemView);
    }

    /**
     * Method for setting the values for UI components.
     * @param holder <code>AlbumViewHolder</code> instance containing the UI components to work on
     * @param position <code>Integer</code> index for <code>List</code> of <code>Album</code> objects.
     */
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
       if (albums != null) {
           Album current = albums.get(position);
           holder.text.setText(current.getTitle());
       } else {
           holder.text.setText("Nothing");
       }
    }

    /**
     * Method for updating the <code>List</code> of <code>Album</code> objects.
     * @param albums <code>List</code> of <code>Album</code> objects
     */
    void setAlbums(List<Album> albums) {
       this.albums = albums;
       notifyDataSetChanged();
    }

    /**
     * Method for returning the number of <code>Album</code> objects in the <code>List</code>.
     * @return <code>albums</code>.<code>size</code>()
     */
    @Override
    public int getItemCount() {
       if (albums != null) {
           return albums.size();
       } else {
           return 0;
       }
    }
}
