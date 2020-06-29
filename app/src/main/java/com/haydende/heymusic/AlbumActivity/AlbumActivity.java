package com.haydende.heymusic.AlbumActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import static android.provider.MediaStore.Audio.Albums;
import static android.provider.MediaStore.Audio.Media;

import com.haydende.heymusic.R;

import static com.haydende.heymusic.CursorManagement.MediaStoreCursorLoader.getCursor;

public class AlbumActivity extends AppCompatActivity {

    private RecyclerView songItemsRV;
    private AlbumActivityAdapter albumActivityAdapter;

    private String[] albumProjection = new String[] {
            Albums._ID,
            Albums.ALBUM,
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

        songItemsRV = findViewById(R.id.AlbumView_SongItemRV);
        otherAlbumsRV = findViewById(R.id.AbumView_OtherAlbumsRV);

        Cursor albumInfoCursor = getCursor(
                this,
                Albums.EXTERNAL_CONTENT_URI,
                albumProjection,
                null,
                null,
                null
        );
        // Cursor songItemsCursor = getCursor();
        // Cursor otherAlbumsCursor = getCursor();

    }
}