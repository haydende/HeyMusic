package com.haydende.heymusic.CursorManagement;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

public class MediaStoreCursorLoader extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static int MEDIASTORE_LOADER_ID = 0;

    private static MediaStoreCursorLoader instance = new MediaStoreCursorLoader();

    private static Loader<Cursor> loader;

    private static NeedsCursor activity;

    private static Uri contentUri;

    private static String [] projection;

    private static String selection;

    private static String[] selectionArgs;

    private static String sortOrder;

    public static synchronized MediaStoreCursorLoader getInstance() {
        if (instance == null) {
            instance = new MediaStoreCursorLoader();
        }
        return instance;
    }

    public void setCursor(NeedsCursor activity, @NonNull Uri contentUri, @Nullable String [] projection,
                                              @Nullable String selection, @Nullable String[] selectionArgs,
                                              @Nullable String sortOrder) {

        MediaStoreCursorLoader.activity = activity;
        MediaStoreCursorLoader.contentUri = contentUri;
        MediaStoreCursorLoader.projection = projection;
        MediaStoreCursorLoader.selection = selection;
        MediaStoreCursorLoader.selectionArgs = selectionArgs;
        MediaStoreCursorLoader.sortOrder = sortOrder;
        LoaderManager loaderManager = LoaderManager.getInstance((AppCompatActivity) activity);
        if (loader != null) {
            loaderManager.restartLoader(MEDIASTORE_LOADER_ID, null, instance);
            Log.i("MediaStoreCursorLoader", "Existing loader; Started loading");
        } else {
            Log.i("MediaStoreCursorLoader", "No existing loader; Initialising...");
            loaderManager.initLoader(MEDIASTORE_LOADER_ID, null, instance);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("MediaStoreCursorLoader", "Creating CursorLoader");
        Log.i("MediaStoreCursorLoader", "Content URI: " + contentUri.toString());
        Log.i("MediaStoreCursorLoader", "Projection[1]:" + projection[1]);
        Log.i("MediaStoreCursorLoader", "Selection: " + selection);
        return new CursorLoader(
                (AppCompatActivity) MediaStoreCursorLoader.activity,
                MediaStoreCursorLoader.contentUri,
                MediaStoreCursorLoader.projection,
                MediaStoreCursorLoader.selection,
                MediaStoreCursorLoader.selectionArgs,
                MediaStoreCursorLoader.sortOrder
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.i("MediaStoreCursorLoader", "Cursor finished");
        MediaStoreCursorLoader.loader = loader;
        activity.setCursor(data);
        Log.i("MediaStoreCursorLoader", "Cursor has been sent");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.i("MediaStoreCursorLoader", "Loader has been reset");
        activity.setCursor(null);
    }

    public interface NeedsCursor {

        void setCursor(Cursor cursor);

    }
}