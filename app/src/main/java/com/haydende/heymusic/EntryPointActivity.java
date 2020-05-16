package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity will be the first to start once application has started.
 * <p>This is where the user will be prompted to accept Read/Write permissions.</p>
 */
public class EntryPointActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point);
        checkReadExternalStoragePermission();
        mediaStoreCursor = getCursor(this);
        // getArtists(0);
        // getAlbums(0);
        // getSongs(0);
        Intent gridView = new Intent(this, GridActivity.class);
        this.startActivity(gridView);
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

    public static List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        String[] projection = {MediaStore.Audio.Artists.ARTIST};
        String selection = MediaStore.Audio.Artists.ARTIST;
        for (int i = 0; i < mediaStoreCursor.getCount(); i++) {
            mediaStoreCursor.moveToPosition(i);
            String name = mediaStoreCursor.getString(mediaStoreCursor.getColumnIndex("Artist"));
            Log.d("Artist Name", name);
            artists.add(new Artist(name));
        }
        return artists;
    }

    public List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        String[] projection = {MediaStore.Audio.Albums.ALBUM,
                               MediaStore.Audio.Albums.};
        String[] selection = {MediaStore.Audio.Albums}
        mediaStoreCursor = getCursor(this, projection, selection);
        String albumName = mediaStoreCursor.getString(mediaStoreCursor.getColumnIndex("Album"));
        Log.d("Album Name", albumName);
        return null;
    }

    public Song getSongs(int position) {
        mediaStoreCursor.moveToPosition(position);
        String songName = mediaStoreCursor.getString(mediaStoreCursor.getColumnIndex("Title"));
        Log.d("Song Name", songName);
        return null;
    }

    /**
     * Method is called once the permission request has been acknowledged.
     * @param requestCode Passed result code to identify which permission is being checked
     * @param permissions {@link String} array of permissions that are request by application
     * @param grantResults Array of results for permissions - used to determine if accepted or denied
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String [] permissions,
                                           @NonNull int[] grantResults) {
        // Using switch/case makes it easier to add more if necessary
        switch (requestCode) {
            // in the case that the permission to check is READ_EXTERNAL_STORAGE_PERMISSION
            case READ_EXTERNAL_STORAGE_PERMISSION:
                // if the permission was granted...
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Thank you!", Toast.LENGTH_LONG);
                    // call cursor loader
                // if the permission was not granted
                } else {
                    // TODO: Warns user about permission requirements
                }
                break;
            // do this if no other case is accepted
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Method for getting an instance of the Cursor.
     * @return {@link Cursor} object for a {@link MediaStore} query searching for Audio files in external storage
     */
    private Cursor getCursor(Context context, String projection, String selection) {
        return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                                  projection,
                                                  selection,
                                                 null,
                                                 MediaStore.Files.FileColumns.TITLE + " ASC");
    }

    @NonNull
    @Override
    /**
     * Creates a new Cursor Loader
     */
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Set the projection statement

        // Set the selection statement
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                         + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
        return new CursorLoader (this,
                                Uri.parse("external"),
                                projection,
                                selection,
                                null,
                                MediaStore.Files.FileColumns.TITLE + " ASC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
