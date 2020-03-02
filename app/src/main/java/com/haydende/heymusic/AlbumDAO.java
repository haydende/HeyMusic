package com.haydende.heymusic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface AlbumDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Album a);

    @Query("DELETE FROM AlbumTable")
    public void deleteAll();

    @Query("SELECT * FROM AlbumTable")
    public ArrayList<Album> getAll();

    @Query("SELECT * FROM AlbumTable ORDER BY title ASC")
    public ArrayList<Album> getAllByNameAsc();

    @Query("SELECT * FROM AlbumTable ORDER BY title DESC")
    public ArrayList<Album> getAllByNameDesc();

    @Query("SELECT * FROM AlbumTable WHERE Title = :title")
    public Song getSong(String title);

    @Query("SELECT * FROM AlbumTable WHERE AlbumID = :albumID")
    public Song getSong(Integer albumID);
}
