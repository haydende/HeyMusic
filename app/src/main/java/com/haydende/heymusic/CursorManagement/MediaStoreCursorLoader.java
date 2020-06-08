package com.haydende.heymusic.CursorManagement;

import android.database.Cursor;
import android.database.DataSetObserver;
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

    private static Cursor cursor;

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
        instance.contentUri = contentUri;
        instance.projection = projection;
        instance.selection = selection;
        instance.selectionArgs = selectionArgs;
        instance.sortOrder = sortOrder;
        LoaderManager loaderManager = LoaderManager.getInstance((AppCompatActivity) activity);
        loaderManager.initLoader(MEDIASTORE_LOADER_ID, null, instance);
    }

    public Cursor getCursor() {
        return cursor;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("Cursor Created?", "Cursor Created");
        return new CursorLoader(
                (AppCompatActivity) activity,
                this.contentUri,
                this.projection,
                this.selection,
                this.selectionArgs,
                this.sortOrder
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d("Cursor finished?", "Cursor finished");
        activity.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        this.cursor = null;
    }

    public static interface NeedsCursor {

        void setCursor(Cursor cursor);

    }
}