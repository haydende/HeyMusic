package com.haydende.heymusic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;

import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;


@Dao
public interface SongDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Song s);

    @Query("DELETE FROM SongTable")
    public void deleteAll();

    @Query("SELECT * FROM SongTable")
    public LiveData<List<Song>> getAll();

    @Query("SELECT * FROM SongTable ORDER BY title ASC")
    public LiveData<List<Song>> getAllByNameAsc();

    @Query("SELECT * FROM SongTable ORDER BY title DESC")
    public LiveData<List<Song>> getAllByNameDesc();

    @Query("SELECT * FROM SongTable WHERE Title = :title")
    public Song getSong(String title);

    @Query("SELECT * FROM SongTable WHERE SongID = :songID")
    public Song getSong(Integer songID);

}
