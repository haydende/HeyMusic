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

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder> {

   class AlbumViewHolder extends RecyclerView.ViewHolder {
       private final ImageButton image;
       private final TextView text;

       private AlbumViewHolder(View itemView) {
           super(itemView);
           image = itemView.findViewById(R.id.image);
           text = itemView.findViewById(R.id.text);
       }
   }

   private final LayoutInflater layoutInflater;
   private List<Album> albums;

   AlbumListAdapter(Context context) {
       layoutInflater = LayoutInflater.from(context);
   }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(itemView);
    }

    public void onBindViewHolder(AlbumViewHolder holder, int position) {
       if (albums != null) {
           Album current = albums.get(position);
           holder.text.setText(current.getTitle());
       } else {
           holder.text.setText("Nothing");
       }
    }

    void setAlbums(List<Album> albums) {
       this.albums = albums;
       notifyDataSetChanged();
    }

    public int getItemCount() {
       if (albums != null) {
           return albums.size();
       } else {
           return 0;
       }
    }

}
