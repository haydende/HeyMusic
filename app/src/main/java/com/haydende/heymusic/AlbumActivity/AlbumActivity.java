package com.haydende.heymusic.AlbumActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import static android.provider.MediaStore.Audio.Albums;
import static android.provider.MediaStore.Audio.Media;

import com.bumptech.glide.Glide;
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
            Media.TITLE,
            Media.DURATION
    };

    private String[] otherAlbumsProjection = new String[] {
            Albums.ARTIST_ID,
            Albums.ALBUM_ID,
            Albums.ALBUM
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

        coverArt = findViewById(R.id.AlbumView_coverImage);
        album = findViewById(R.id.AlbumView_albumName);
        artist = findViewById(R.id.AlbumView_artistName);
        songsHeader = findViewById(R.id.AlbumView_SongsHeader);
        songItemsRV = findViewById(R.id.AlbumView_SongItemRV);
        otherAlbumsHeader = findViewById(R.id.AlbumView_OtherAlbumsHeader);
        otherAlbumsRV = findViewById(R.id.AbumView_OtherAlbumsRV);

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

        Cursor songItemsCursor = getCursor(
                this,
                Media.EXTERNAL_CONTENT_URI,
                songsProjection,
                Media.ALBUM_ID + " = " + albumID,
                null,
                null
        );


        // Cursor otherAlbumsCursor = getCursor();


    }
}