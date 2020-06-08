package com.haydende.heymusic.GridAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.haydende.heymusic.CursorManagement.MediaStoreCursorLoader;
import com.haydende.heymusic.R;

import static com.haydende.heymusic.GridAdapter.ItemType.ARTIST;
import static com.haydende.heymusic.GridAdapter.ItemType.ALBUM;
import static com.haydende.heymusic.GridAdapter.ItemType.SONG;

public class GridActivity extends AppCompatActivity implements MediaStoreCursorLoader.NeedsCursor {

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
    private GridAdapter gridAdapter;

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
    private static String[] artistProjection = {
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST
    };

    /**
     * {@link String} array of MediaStore column headers for collecting Album data.
     */
    private static String[] albumProjection = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_ART
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
        setContentView(R.layout.activity_main);

        artistButton = findViewById(R.id.gridActivityButtons_artistButton);
        artistButton.setOnClickListener((View v) -> {
            if (itemType != ARTIST) {
                itemType = ARTIST;
                recyclerView.removeAllViews();
                setGridAdapter();
                checkReadExternalStoragePermission();
            }
        });

        albumButton = findViewById(R.id.gridActivityButtons_albumButton);
        albumButton.setOnClickListener((View v) -> {
            if (itemType != ALBUM) {
                itemType = ALBUM;
                recyclerView.removeAllViews();
                setGridAdapter();
                checkReadExternalStoragePermission();
            }
        });

        songButton = findViewById(R.id.gridActivityButtons_songButton);
        songButton.setOnClickListener((View v) -> {
            if (itemType != SONG) {
                itemType = SONG;
                recyclerView.removeAllViews();
                setGridAdapter();
                checkReadExternalStoragePermission();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
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
                createCursor();
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

    public void createCursor() {
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = albumProjection;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
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
        MediaStoreCursorLoader.getInstance().setCursor(
           this,
            contentUri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        );
    }

    public void setCursor(Cursor cursor) {
        gridAdapter.changeCursor(cursor);
    }

    private void setGridAdapter() {
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
    }

}
