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
     * <code<LiveData</code> of a <code>List</code> of <code>Artist</code> objects.
     */
    private LiveData<List<Artist>> allArtists;

    /**
     * <code>LiveData</code> of a <code>List</code> of <code>Album</code> objects.
     */
    private LiveData<List<Album>> allAlbums;

    /**
     * <code>LiveData</code> of a <code>List</code> of <code>Song</code> objects.
     */
    private LiveData<List<Song>> allSongs;

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
        allArtists = artistDAO.getAll();
        allAlbums = albumDAO.getAll();
        allSongs = songDAO.getAll();

    }

    /**
     * Method for calling <code>insert</code> for the <code>ArtistDAO</code>.
     * <p>Does this on another thread.</p>
     * @param a The <code>Artist</code> to insert
     */
    public void insert(Artist a) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            artistDAO.insert(a);
        });
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
     * Method for calling <code>insert</code> for the <code>SongDAO</code>.
     * <p>Does this on another thread.</p>
     * @param s The <code>Song</code> to insert
     */
    public void insert(Song s) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            songDAO.insert(s);
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
     * Method for calling <code>deleteAll</code> for the <code>ArtistDAO</code>.
     * <p>Does this on another thread.</p>
     */
    public void deleteAllArtists() {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            artistDAO.deleteAll();
        });
    }

    /**
     * Method for calling <code>deleteAll</code> for the <code>AlbumDAO</code>.
     * <p>Does this on another thread.</p>
     */
    public void deleteAllAlbums() {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            albumDAO.deleteAll();
        });
    }

    /**
     * Method for calling <code>deleteaAll</code> for the <code>SongDAO</code>.
     * <p>Does this on another thread.</p>
     */
    public void deletAllSongs() {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            songDAO.deleteAll();
        });
    }

    /**
     * Method for returning the <code>List</code> of <code>Artist</code> objects.
     * @return <code>List</code> of <code>Artist</code> objects
     */
    public LiveData<List<Artist>> getAllArtists() {
        return allArtists;
    }

    /**
     * Method for returning the <code>List</code> of <code>Album</code> objects.
     * @return <code>List</code> of <code>Albums</code> objects
     */
    public LiveData<List<Album>> getAllAlbums() {
        return allAlbums;
    }

    /**
     * Method for returning the <code>List</code> of <code>Song</code> objects.
     * @return <code>List</code> of <code>Song</code> objects
     */
    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

}
