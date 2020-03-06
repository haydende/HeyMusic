package com.haydende.heymusic;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Class for holding cached data from the Music Database.
 */
public class MusicRepository {

    /**
     * Instance of the <code>ArtistDAO</code>.
     */
    private ArtistDAO artistDAO;

    /**
     * Instance of the <code>AlbumDAO</code>.
     */
    private AlbumDAO albumDAO;

    /**
     * Instance of the <code>SongDAO</code>.
     */
    private SongDAO songDAO;

    /**
     * <code>LiveData</code> of a <code>List</code> of <code>Album</code> objects.
     */
    private LiveData<List<Album>> allAlbums;

    /**
     * Default constructor for this class. Assigns all class members.
     * @param application Used to get context for the Database.
     *                    See <code>MusicDatabase</code>.<code>getInstance</code>()
     */
    public MusicRepository(Application application) {
        MusicDatabase database = MusicDatabase.getInstance(application);
        artistDAO = database.artistDao();
        albumDAO = database.albumDao();
        songDAO = database.songDao();
        allAlbums = albumDAO.getAll();
    }

    /**
     * Method for calling <code>insert</code> for the <code>AlbumDAO</code>.
     * <p>Does this on another thread.</p>
     * @param a The <code>Album</code> to insert
     */
    public void insert(Album a) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            albumDAO.insert(a);
        });
    }

    /**
     * Method for calling <code>update</code> for the <code>AlbumDAO</code>.
     * <p>Does this on another thread.</p>
     * @param a The <code>Album</code> to update
     */
    public void update(Album a) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            // albumDAO.update(a);
        });
    }

    /**
     * Method for calling <code>deleteAll</code> for the <code>AlbumDAO</code>.
     * <p>Does this on another thread.</p>
     */
    public void deleteAll() {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            albumDAO.deleteAll();
        });
    }

    /**
     * Method for returning the <code>List</code> of <code>Album</code> objects.
     * @return <code>List</code> of <code>Albums</code> objects
     */
    public LiveData<List<Album>> getAllAlbums() {
        return allAlbums;
    }

}
