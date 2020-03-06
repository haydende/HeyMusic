package com.haydende.heymusic;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Artist.class, Album.class, Song.class}, version = 1, exportSchema = false)
public abstract class MusicDatabase extends RoomDatabase {

    private static final String DBNAME = "MusicDB";
    private static MusicDatabase instance;
    private static final int NUM_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    public abstract ArtistDAO artistDao();

    public abstract AlbumDAO albumDao();

    public abstract SongDAO songDao();

    public static synchronized MusicDatabase getInstance(Context context) {
        if (instance == null) {
              instance = Room.databaseBuilder(context.getApplicationContext(),
                        MusicDatabase.class,
                        DBNAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            System.out.println("Here");
            databaseWriteExecutor.execute(() -> {
               AlbumDAO albumDAO = instance.albumDao();
               albumDAO.insert(new Album(1, "title", 1299));
            });
        }
    };
}