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
    }

    public void insert(Album a) {
        new InsertAlbumAsyncTask(albumDAO).execute(a);
    }

    public void update(Album a) {
        new UpdateAlbumAsyncTask(albumDAO).execute(a);
    }

    public void delete(Album a) {

    }

    public void deleteAllAlbums() {

    }

    public LiveData<List<Album>> getAllAlbums() {
        return albumDAO.getAlbumList();
    }

    private static class InsertAlbumAsyncTask extends AsyncTask<Album, Void, Void> {
        private AlbumDAO albumDAO;

        private InsertAlbumAsyncTask(AlbumDAO albumDAO) {
            this.albumDAO = albumDAO;
        }

        @Override
        protected Void doInBackground(Album... albums) {
            albumDAO.insert(albums[0]);
            return null;
        }
    }

    private static class UpdateAlbumAsyncTask extends AsyncTask<Album, Void, Void> {
        private AlbumDAO albumDAO;

        private UpdateAlbumAsyncTask(AlbumDAO albumDAO) {
            this.albumDAO = albumDAO;
        }

        @Override
        protected Void doInBackground(Album... albums) {
            albumDAO.update(albums[0]);
            return null;
        }
    }

    private static class AlbumListAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlbumDAO albumDAO;

        private AlbumListAsyncTask(AlbumDAO albumDAO) {
            this.albumDAO = albumDAO;
        }

        protected Void doInBackground(Void... voids) {
            albumDAO.getAlbumList();
            return null;
        }
    }

}
