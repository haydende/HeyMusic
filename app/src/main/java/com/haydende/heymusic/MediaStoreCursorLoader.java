package com.haydende.heymusic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

public class MediaStoreCursorLoader extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static int MEDIASTORE_LOADER_ID = 0;

    private static MediaStoreCursorLoader instance = new MediaStoreCursorLoader();

    private static Cursor cursor;

    private static AppCompatActivity activity;

    private static Uri contentUri;

    private static String [] projection;

    private static String selection;

    private static String[] selectionArgs;

    private static String sortOrder;

    public static synchronized MediaStoreCursorLoader getInstance() { return instance; }

    public void setCursor(AppCompatActivity activity, @NonNull Uri contentUri, @Nullable String [] projection,
                                              @Nullable String selection, @Nullable String[] selectionArgs,
                                              @Nullable String sortOrder) {
        instance.activity = activity;
        instance.contentUri = contentUri;
        instance.projection = projection;
        instance.selection = selection;
        instance.selectionArgs = selectionArgs;
        instance.sortOrder = sortOrder;
        LoaderManager loaderManager = LoaderManager.getInstance(activity);
        loaderManager.initLoader(MEDIASTORE_LOADER_ID, null, instance);
    }

    public Cursor getCursor() {
        return cursor;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                this.activity,
                this.contentUri,
                this.projection,
                this.selection,
                this.selectionArgs,
                this.sortOrder
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        this.cursor = data;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        this.cursor = null;
    }
}