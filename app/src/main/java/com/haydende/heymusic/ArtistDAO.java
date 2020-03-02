package com.haydende.heymusic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface ArtistDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Artist a);

    @Query("DELETE FROM ArtistTable")
    public void deleteAll();

    @Query("SELECT * FROM ArtistTable")
    public ArrayList<Artist> getAll();

    @Query("SELECT * FROM ArtistTable ORDER BY name ASC")
    public ArrayList<Artist> getAllByNameAsc();

    @Query("SELECT * FROM ArtistTable ORDER BY name DESC")
    public ArrayList<Artist> getAllByNameDesc();

    @Query("SELECT * FROM ArtistTable WHERE Name = :name")
    public Artist getSong(String name);

    @Query("SELECT * FROM ArtistTable WHERE ArtistID = :artistID")
    public Artist getSong(Integer artistID);
}
