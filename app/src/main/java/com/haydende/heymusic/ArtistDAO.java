package com.haydende.heymusic;

import androidx.room.Dao;
import androidx.room.Insert;
<<<<<<< HEAD
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
=======
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
>>>>>>> add503aed975bb61a30525bd7f1ece1c551e9a01

@Dao
public interface ArtistDAO {

<<<<<<< HEAD
    @Query("SELECT * FROM Artist")
    List<Artist> getArtistList();

    @Insert
    void insertArtist(Artist a);

    @Update
    void updateArtist(Artist a);

=======
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
>>>>>>> add503aed975bb61a30525bd7f1ece1c551e9a01
}
