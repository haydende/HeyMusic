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
public interface SongDAO {

<<<<<<< HEAD
    @Query("SELECT * FROM Song")
    List<Song> getSongList();

    @Insert
    void insertSong(Song s);

    @Update
    void updateSong(Song s);

=======
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Song s);

    @Query("DELETE FROM SongTable")
    public void deleteAll();

    @Query("SELECT * FROM SongTable")
    public ArrayList<Song> getAll();

    @Query("SELECT * FROM SongTable ORDER BY title ASC")
    public ArrayList<Song> getAllByNameAsc();

    @Query("SELECT * FROM SongTable ORDER BY title DESC")
    public ArrayList<Song> getAllByNameDesc();

    @Query("SELECT * FROM SongTable WHERE Title = :title")
    public Song getSong(String title);

    @Query("SELECT * FROM SongTable WHERE SongID = :songID")
    public Song getSong(Integer songID);
>>>>>>> add503aed975bb61a30525bd7f1ece1c551e9a01
}
