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
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

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
     * Defines the query for what data should be collected using MediaStore.
     * <p>Used for creating the <code>Cursor</code> mediaStoreCursor.</p>
     */
    private String [] projection = {MediaStore.Files.FileColumns._ID,
                                    MediaStore.Files.FileColumns.DATE_ADDED,
                                    MediaStore.Files.FileColumns.DURATION};

    /**
     * This is used to obtain the data from <code>MediaStore</code>.
     */
    private Cursor mediaStoreCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point);
        checkReadExternalStoragePermission();
        mediaStoreCursor = getCursor(this);
        getArtists(0);
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

    private Artist getArtists(int position) {
        try {
            int idIndex = mediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            int mediaTypeIndex = mediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
            mediaStoreCursor.moveToPosition(position);
            switch (mediaStoreCursor.getInt(mediaTypeIndex)) {
                case MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO:
                    String artistName = MediaStore.Audio.Artists.ARTIST;
                    Log.d("Artist Name", "ArtistName: " + artistName);
                    String albumName = MediaStore.Audio.Albums.ALBUM;
                    Log.d("Album Name", "AlbumName: " + albumName);
                default:
                    return null;
            }
        } catch (CursorIndexOutOfBoundsException e) {
            Log.d("Cursor Exception", e.toString());
        }
        return null;
    }

    /**
     * Method is called once the permission request has been acknowledged.
     * @param requestCode Passed result code to identify which permission is being checked
     * @param permissions <code>String</code> array of permissions that are request by application
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

    private Cursor getCursor(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                         + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
        return context.getContentResolver().query(uri,
                                                  projection,
                                                  selection,
                                                 null,
                                                 MediaStore.Files.FileColumns.TITLE + " ASC");
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Set the projection statement

        // Set the selection statement
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                         + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
        return new CursorLoader (this,
                                Uri.parse("content://*"),
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
