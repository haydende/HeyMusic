package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
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
import android.os.Build;
import android.os.Bundle;
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

    private static String[] artistProjection = {

    };

    private static String[] albumProjection = {

    };

    private static String[] songProjection = {

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArtistListAdapter adapter = new ArtistListAdapter(this);

        MusicViewModel mVM = ViewModelProviders.of(this).get(MusicViewModel.class);
        mVM.getAllArtists().observe(this, new Observer<List<Artist>>() {

            public void onChanged(@Nullable final List<Artist> artists) {
                // update cached copy of data
                adapter.setArtists(artists);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

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

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
