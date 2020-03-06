package com.haydende.heymusic;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MusicRepository {

    private ArtistDAO artistDAO;
    private AlbumDAO albumDAO;
    private SongDAO songDAO;

    private LiveData<List<Album>> allAlbums;

    public MusicRepository(Application application) {
        MusicDatabase database = MusicDatabase.getInstance(application);
        artistDAO = database.artistDao();
        albumDAO = database.albumDao();
        songDAO = database.songDao();
        allAlbums = albumDAO.getAlbumList();
    }

    public void insert(Album a) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            albumDAO.insert(a);
        });
    }

    public void update(Album a) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            albumDAO.update(a);
        });
    }

    public void deleteAll() {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            albumDAO.deleteAll();
        });
    }

    public LiveData<List<Album>> getAllAlbums() {
        return allAlbums;
    }

}
