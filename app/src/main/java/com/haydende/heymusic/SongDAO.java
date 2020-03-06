package com.haydende.heymusic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import java.util.List;

/**
 * Data Access Object for the Song table.
 * <p>Room generates classes for implementing the methods below.</p>
 */
@Dao
public interface SongDAO {

    /**
     * Method for inserting a new Song entry.
     * @param s Song object to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Song s);

    /**
     * Method for executing <code>DELETE</code> query.
     */
    @Query("DELETE FROM SongTable")
    void deleteAll();

    /**
     * Method for getting all Song entries.
     * @return <code>List</code> of <code>Song</code> objects.
     */
    @Query("SELECT * FROM SongTable")
    LiveData<List<Song>> getAll();

    /**
     * Method for getting all Song entries (Ascending order for Title field).
     * @return <code>List</code> of <code>Song</code> objects.
     */
    @Query("SELECT * FROM SongTable ORDER BY title ASC")
    LiveData<List<Song>> getAllByNameAsc();

    /**
     * Method for getting all Song entries (Descending order for Title field).
     * @return <code>List</code> of <code>Song</code> objects.
     */
    @Query("SELECT * FROM SongTable ORDER BY title DESC")
    LiveData<List<Song>> getAllByNameDesc();

    /**
     * Method for getting a certain Song by matching Title.
     * @param title Title to search for
     * @return <code>Song</code> object
     */
    @Query("SELECT * FROM SongTable WHERE Title = :title")
    Song getSong(String title);

    /**
     * Method for getting a certain Song by matching SongID.
     * @param songID SongID to search for
     * @return <code>Song</code> object
     */
    @Query("SELECT * FROM SongTable WHERE SongID = :songID")
    Song getSong(Integer songID);

}
