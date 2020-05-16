package com.haydende.heymusic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Subclass of <code>AndroidViewModel</code> responsible for holding the data.
 * <p>This class acts as a middle-man because it is expected to live longer than UI components.</p>
 */
public class MusicViewModel extends AndroidViewModel {

    /**
     * Instance of the <code>MusicRepository</code>.
     */
    private MusicRepository mRepo;

    /**
     * <code>LiveData</code> of a <code>List</code> of <code>Album</code> objects.
     */
    private LiveData<List<Album>> allAlbums;

    private LiveData<List<Artist>> allArtists;

    /**
     * Default constructor for this class.
     * @param application Used to get context for the Database.
     */
    public MusicViewModel(@NonNull Application application) {
        super(application);
        mRepo = new MusicRepository(application);
        allAlbums = mRepo.getAllAlbums();
        allArtists = mRepo.getAllArtists();
    }

    /**
     * Method for calling the <code>insert</code> method for <code>mRepo</code>.
     * @param a <code>Artist</code> object to insert
     */
    public void insert(Artist a) { mRepo.insert(a); }

    /**
     * Method for calling the <code>insert</code> method for <code>mRepo</code>.
     * @param a <code>Album</code> object to insert
     */
    public void insert(Album a) {
        mRepo.insert(a);
    }

    /**
     * Method for calling the <code>insert</code> method for <code>mRepo</code>.
     * @param s <code>Song</code> object to insert
     */
    public void insert(Song s) { mRepo.insert(s); }

    /**
     * Method for calling the <code>update</code> method for <code>mRepo</code>.
     * @param a <code>Album</code> object to update.
     */
    public void update(Album a) {
        mRepo.update(a);
    }

    /**
     * Method for returning the <code>List</code> of <code>Album</code> objects.
     * @return <code>List</code> of <code>Album</code> objects.
     */
    public LiveData<List<Album>> getAllAlbums() {
        return allAlbums;
    }

    public LiveData<List<Artist>> getAllArtists() { return allArtists; }
}
