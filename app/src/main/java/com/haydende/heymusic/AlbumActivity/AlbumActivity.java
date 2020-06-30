package com.haydende.heymusic.AlbumActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static android.provider.MediaStore.Audio.Albums;
import static android.provider.MediaStore.Audio.Media;

import com.bumptech.glide.Glide;
import com.haydende.heymusic.GridView.AlbumGridAdapter;
import com.haydende.heymusic.R;

import static com.haydende.heymusic.CursorManagement.MediaStoreCursorLoader.getCursor;

public class AlbumActivity extends AppCompatActivity {

    private ImageView coverArt;
    private TextView album;
    private TextView artist;
    private TextView songsHeader;
    private TextView otherAlbumsHeader;


    private RecyclerView songItemsRV;
    private AlbumActivityAdapter albumActivityAdapter;

    private String[] albumProjection = new String[] {
            Albums._ID,
            Albums.ARTIST,
            Albums.NUMBER_OF_SONGS,
            Albums.FIRST_YEAR
    };

    private String[] songsProjection = new String[] {
            Media.ARTIST_ID,
            Media.ALBUM_ID,
            Media.ARTIST,
            Media.TRACK,
            Media.TITLE,
            Media.DURATION
    };

    private String[] otherAlbumsProjection = new String[] {
            Albums._ID,
            Albums.ALBUM,
            Albums.ARTIST,
            Albums.NUMBER_OF_SONGS,
            Albums.FIRST_YEAR
    };


    private RecyclerView otherAlbumsRV;
    private OtherAlbumAdapter otherAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity);
        Bundle extras = getIntent().getExtras();

        String albumID = extras.getString("album_id");
        Uri albumCover = (Uri) extras.get("album_cover");
        String albumName = extras.getString("album_name");
        String artistName = extras.getString("artist_name");
        String numSongs = extras.getString("num_songs");
        String year = extras.getString("first_year");

        // ImageView
        coverArt = findViewById(R.id.AlbumView_coverImage);

        // TextViews
        album = findViewById(R.id.AlbumView_albumName);
        artist = findViewById(R.id.AlbumView_artistName);
        songsHeader = findViewById(R.id.AlbumView_SongsHeader);

        // Song Items RecyclerView
        songItemsRV = findViewById(R.id.AlbumView_SongItemRV);
        AlbumActivityAdapter songItemAdapter = new AlbumActivityAdapter(this);
        songItemsRV.setAdapter(songItemAdapter);
        songItemsRV.setLayoutManager(new GridLayoutManager(this, 1));

        // Other Albums RecyclerView
        otherAlbumsHeader = findViewById(R.id.AlbumView_OtherAlbumsHeader);
        otherAlbumsRV = findViewById(R.id.AlbumView_OtherAlbumsRV);
        AlbumGridAdapter albumGridAdapter = new AlbumGridAdapter(this);
        otherAlbumsRV.setAdapter(albumGridAdapter);
        otherAlbumsRV.setLayoutManager(new LinearLayoutManager(
                this,
                RecyclerView.HORIZONTAL,
                false
        ));

        Glide.with(this)
                .load(albumCover)
                .override(300,300)
                .into(coverArt);

        album.setText(albumName);
        artist.setText(artistName);
        songsHeader.setText(
                String.format(
                        getString(R.string.songs_header),
                        numSongs
                )
        );

        otherAlbumsHeader.setText(
                String.format(
                        getString(R.string.other_albums_header),
                        artistName
                )
        );

        // Create Cursor to be used by the songItemAdapter
        Cursor songItemsCursor = getCursor(
                this,
                Media.EXTERNAL_CONTENT_URI,
                songsProjection,
                Media.ALBUM_ID + " = " + albumID,
                null,
                null
        );
        songItemAdapter.changeCursor(songItemsCursor);

        Cursor otherAlbumsCursor = getCursor(
                this,
                Albums.EXTERNAL_CONTENT_URI,
                otherAlbumsProjection,
                Albums.ARTIST + " = " + "\"" + artistName + "\"",
                null,
                null
        );

        /*Log.i("AlbumActivity",
                String.format("Cursor row count: %d", otherAlbumsCursor.getCount())
        );*/

        albumGridAdapter.changeCursor(otherAlbumsCursor);


    }
}