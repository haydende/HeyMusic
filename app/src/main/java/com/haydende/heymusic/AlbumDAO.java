package com.haydende.heymusic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface AlbumDAO {

    @Query("SELECT * FROM AlbumTable")
    LiveData<List<Album>> getAlbumList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Album a);

    @Update
    void update(Album a);

    @Query("DELETE FROM AlbumTable")
    public void deleteAll();

    @Query("SELECT * FROM AlbumTable")
    public LiveData<List<Album>> getAll();

    @Query("SELECT * FROM AlbumTable ORDER BY title ASC")
    public LiveData<List<Album>> getAllByNameAsc();

    @Query("SELECT * FROM AlbumTable ORDER BY title DESC")
    public LiveData<List<Album>> getAllByNameDesc();

    @Query("SELECT * FROM AlbumTable WHERE AlbumID = :albumID")
    public Album getAlbum(Integer albumID);

    @Query("SELECT * FROM AlbumTable WHERE Title = :title")
    public Album getAlbum(String title);
}
