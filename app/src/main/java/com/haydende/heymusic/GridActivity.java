package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GridActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Used to capture user's permission response for READ_EXTERNAL_STORAGE_PERMISSION.
     */
    private final static int READ_EXTERNAL_STORAGE_PERMISSION = 0;

    /**
     * ID for the MediaStore loader used to gather audio files.
     */
    private final static int MEDIASTORE_LOADER_ID = 0;

    /**
     * This is used to obtain the data from <code>MediaStore</code>.
     */
    private static Cursor mediaStoreCursor;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter gridAdapter;

    private ImageButton artistButton;

    private ImageButton albumButton;

    private ImageButton songButton;

    /**
     * {@link Enum} value to represent whether this Grid Layout will be showing ARTIST, ALBUM or
     * SONG data.
     *
     * @see ItemType
     */
    private static ItemType itemType = ItemType.SONG;

    /**
     * {@link String} array of MediaStore column headers for collecting Artist data.
     */
    private static String[] artistProjection = {
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST
    };

    /**
     * {@link String} array of MediaStore column headers for collecting Album data.
     */
    private static String[] albumProjection = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM
    };

    /**
     * {@link String} array of MediaStore column headers for collecting Song data.
     */
    private final static String[] songProjection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artistButton = findViewById(R.id.gridActivityButtons_artistButton);
        artistButton.setOnClickListener((View v) -> {
            itemType = ItemType.ARTIST;
            recreate();
        });

        albumButton = findViewById(R.id.gridActivityButtons_albumButton);
        albumButton.setOnClickListener((View v) -> {
            itemType = ItemType.ALBUM;
            recreate();
        });

        songButton = findViewById(R.id.gridActivityButtons_songButton);
        songButton.setOnClickListener((View v) -> {
            itemType = ItemType.SONG;
            recreate();
        });

        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        switch (itemType) {
            case ARTIST:
                gridAdapter = new ArtistGridAdapter(this);
                break;
            case ALBUM:
                gridAdapter = new AlbumGridAdapter(this);
                break;
            case SONG:
            default:
                gridAdapter = new SongGridAdapter(this);
                break;
        }
        recyclerView.setAdapter(gridAdapter);

        checkReadExternalStoragePermission();
    }

    /**
     * Checks if the device is using Android Marshmallow or above.
     * <p>If so, the application needs to ensure that the user has given permission to access storage</p>
     */
    private void checkReadExternalStoragePermission() {
        // check if device is using Marshmallow or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // if the permission has been granted...
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                getSupportLoaderManager().initLoader(MEDIASTORE_LOADER_ID, null, this);
                // if permission has not been granted
            } else {
                // determine if the application should show a custom permission request
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // display toast notification to request for permission
                    Toast.makeText(this,
                            "App needs access to external storage to work",
                            Toast.LENGTH_SHORT).show();
                }
                // prompt for permission
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {
            getSupportLoaderManager().initLoader(MEDIASTORE_LOADER_ID, null ,this);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = albumProjection;
        String selection = null;
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        switch (itemType) {
            case ARTIST:
                projection = artistProjection;
                contentUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
                break;
            case ALBUM:
                projection = albumProjection;
                contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
                break;
            case SONG:
            default:
                projection = songProjection;
                break;
        }
        return new CursorLoader(
                this,
                contentUri,
                projection,
                selection,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        switch (itemType) {
            case ARTIST:
                ((ArtistGridAdapter) gridAdapter).changeCursor(data);
                break;
            case ALBUM:
                ((AlbumGridAdapter) gridAdapter).changeCursor(data);
                break;
            case SONG:
            default:
                ((SongGridAdapter) gridAdapter).changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        switch (itemType) {
            case ARTIST:
                ((ArtistGridAdapter) gridAdapter).changeCursor(null);
                break;
            case ALBUM:
                ((AlbumGridAdapter) gridAdapter).changeCursor(null);
                break;
            case SONG:
            default:
                ((SongGridAdapter) gridAdapter).changeCursor(null);
        }
    }
}
