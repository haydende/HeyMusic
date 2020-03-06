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

/**
 * Class for modelling Room Database.
 * <p>Builds Room Database of Artist, Album and Song tables.</p>
 */
@Database(entities = {Artist.class, Album.class, Song.class}, version = 1, exportSchema = false)
public abstract class MusicDatabase extends RoomDatabase {

    /**
     * String containing the name of the database.
     */
    private static final String DBNAME = "MusicDB";

    /**
     * Instance of this class.
     * <p>Used to pass along to main application. Made static to ensure only one instance.</p>
     */
    private static MusicDatabase instance;

    /**
     * Integer defining how many threads can be used by <code>databaseWriteExecutor</code>.
     */
    private static final int NUM_THREADS = 4;

    /**
     * Creates an Executor so that database operations can be executed on a non-UI thread.
     */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    /**
     * Method for getting the ArtistDAO.
     * @return
     */
    public abstract ArtistDAO artistDao();

    /**
     * Method for getting the AlbumDAO.
     * @return
     */
    public abstract AlbumDAO albumDao();

    /**
     * Method for getting the SongDAO.
     * @return
     */
    public abstract SongDAO songDao();

    /**
     * Method for returning an instance of this database.
     * <p><code>synchronized</code> keyword ensures that this method can't be
     * accessed by multiple threads at once</p>
     * @param context Provides application context
     * @return <code>instance</code>, which is an instance of this class
     */
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

    /**
     * Defines a new instance fo RoomDatabase.Callback().
     * <p>Defines what should happen when the database is opened.</p>
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        /**
         * Defines what happens when the database is opened.
         * @param db Underlying SQLite Database that the Room abstraction layer runs on
         */
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            System.out.println("Here");
            databaseWriteExecutor.execute(() -> {
                AlbumDAO albumDAO = instance.albumDao();
                albumDAO.deleteAll();
                albumDAO.insert(new Album(1, "title", 1299));
                albumDAO.insert(new Album(2, "Another Title", 2020));
            });
        }
    };
}
