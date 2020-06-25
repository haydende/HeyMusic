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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MediaStoreCursorLoader {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public static Cursor getCursor(AppCompatActivity activity, @NonNull Uri contentUri,
                                   @Nullable String[] projection, @Nullable String selection,
                                   @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor data = null;
        try {
            data = threadPool.submit(() -> activity.getContentResolver().query(
                    contentUri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            )).get();
        } catch (ExecutionException | InterruptedException e) {

        }
        return data;
    }
}