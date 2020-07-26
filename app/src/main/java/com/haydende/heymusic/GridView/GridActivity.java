package com.haydende.heymusic.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.haydende.heymusic.CursorManagement.MediaStoreCursorLoader;
import com.haydende.heymusic.R;

import static com.haydende.heymusic.GridView.ItemType.ARTIST;
import static com.haydende.heymusic.GridView.ItemType.ALBUM;
import static com.haydende.heymusic.GridView.ItemType.SONG;

public class GridActivity extends AppCompatActivity {

    /**
     * Used to capture user's permission response for READ_EXTERNAL_STORAGE_PERMISSION.
     */
    private final static int READ_EXTERNAL_STORAGE_PERMISSION = 0;

    private RecyclerView recyclerView;

    private static GridAdapter gridAdapter;

    private ImageButton artistButton;

    private ImageButton albumButton;

    private ImageButton songButton;

    /**
     * {@link Enum} value to represent whether this Grid Layout will be showing ARTIST, ALBUM or
     * SONG data.
     *
     * @see ItemType
     */
    private static ItemType itemType = SONG;

    /**
     * {@link String} array of MediaStore column headers for collecting Artist data.
     */
    private final static String[] artistProjection = {
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST
    };

    /**
     * {@link String} array of MediaStore column headers for collecting Album data.
     */
    private final static String[] albumProjection = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.FIRST_YEAR
    };

    /**
     * {@link String} array of MediaStore column headers for collecting Song data.
     */
    private final static String[] songProjection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_activity);
        if (gridAdapter == null) {
            gridAdapter = new SongGridAdapter(this);
        }

        Log.d("onCreate() method", "GridAdapter: " + gridAdapter.toString());

        recyclerView = findViewById(R.id.recycler_view);

        artistButton = findViewById(R.id.gridActivityButtons_artistButton);
        artistButton.setOnClickListener((View v) -> {
            if (itemType != ARTIST) {
                itemType = ARTIST;
                recyclerView.removeAllViews();
                gridAdapter = new ArtistGridAdapter(this);
                checkReadExternalStoragePermission();
            }
        });

        albumButton = findViewById(R.id.gridActivityButtons_albumButton);
        albumButton.setOnClickListener((View v) -> {
            if (itemType != ALBUM) {
                itemType = ALBUM;
                recyclerView.removeAllViews();
                gridAdapter = new AlbumGridAdapter(this);
                checkReadExternalStoragePermission();
            }
        });

        songButton = findViewById(R.id.gridActivityButtons_songButton);
        songButton.setOnClickListener((View v) -> {
            if (itemType != SONG) {
                itemType = SONG;
                recyclerView.removeAllViews();
                gridAdapter = new SongGridAdapter(this);
                checkReadExternalStoragePermission();
            }
        });

        setGridAdapter();

        checkReadExternalStoragePermission();
    }

    /**
     * Checks if the device is using Android Marshmallow or above.
     * <p>If so, the application needs to ensure that the user has given permission to access storage</p>
     */
    private void checkReadExternalStoragePermission() {
        // check if device is using Marshmallow (API Level 23) or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // if the permission has been granted...
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                setCursor(createCursor());
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
            // getSupportLoaderManager().initLoader(MEDIASTORE_LOADER_ID, null ,this);
            createCursor();
        }
    }

    public Cursor createCursor() {
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        setGridAdapter();
        Log.d("createCursor() method", "URI: " + getContentUri().toString());
        Log.d("createCursor() method", "Adapter Type: " + recyclerView.getAdapter());
        Log.i("GridActivity", "Calling for new Cursor");
        return MediaStoreCursorLoader.getCursor(
           this,
            getContentUri(),
            getProjection(),
            selection,
            selectionArgs,
            sortOrder
        );
    }

    private void setCursor(Cursor cursor) {
        Log.i("GridAdapter", "New Cursor has been loaded");
        // Log.i("GridActivity", "New Cursor column 1: " + cursor.getColumnName(2));
        setGridAdapter();
        gridAdapter.changeCursor(cursor);
    }

    private void setGridAdapter() {
        Log.d("GridActivity", "Setting Adapter");
        switch (itemType) {
            case SONG:
                gridAdapter = new SongGridAdapter(this);
                break;
            case ALBUM:
                gridAdapter = new AlbumGridAdapter(this);
                break;
            case ARTIST:
                gridAdapter = new ArtistGridAdapter(this);
                break;
        }
        recyclerView.setAdapter((RecyclerView.Adapter) gridAdapter);
        Log.i("GridActivity", "Using " + recyclerView.getAdapter());
    }

    private Uri getContentUri() {
        switch (itemType) {
            case ARTIST:
                Log.i("GridActivity", "Using Artist URI");
                return MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
            case ALBUM:
                Log.i("GridActivity", "Using Album URI");
                return MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            case SONG:
            default:
                Log.i("GridActivity", "Using Song URI");
                return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
    }

    private String[] getProjection() {
        switch (itemType) {
            case ARTIST:
                Log.i("GridActivity", "Using Artist data");
                return artistProjection;
            case ALBUM:
                Log.i("GridActivity", "Using Album data");
                return albumProjection;
            case SONG:
            default:
                Log.i("GridActivity", "Using Song data");
                return songProjection;
        }
    }

}
