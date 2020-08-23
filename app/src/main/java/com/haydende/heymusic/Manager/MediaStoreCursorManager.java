package com.haydende.heymusic.Manager;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaStoreCursorManager {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public static Cursor getCursor(AppCompatActivity activity, @NonNull Uri contentUri,
                                   @Nullable String[] projection, @Nullable String selection,
                                   @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i("MediaStoreCursorLoader",
                String.format(
                        "I have been tasked to complete the following query: \n" +
                        "URI: %s\n" +
                        "Projection: %s\n" +
                        "Selection: %s\n" +
                        "Selection Args: %s\n" +
                        "Sort Order: %s\n",
                        contentUri,
                        (projection == null) ? "null" : Arrays.toString(projection),
                        selection,
                        (selectionArgs == null) ? "null" : Arrays.toString(selectionArgs),
                        sortOrder
                )
        );
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