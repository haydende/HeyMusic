package com.haydende.heymusic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import java.util.List;

/**
 * Data Access Object for the Artist Table.
 * <p>Room generates classes for implementing the methods below.</p>
 */
@Dao
public interface ArtistDAO {

    /**
     * Method for inserting a new Artist entry.
     * @param a Artist entry to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Artist a);

    /**
     * Method for executing <code>DELETE</code> query.
     */
    @Query("DELETE FROM ArtistTable")
    void deleteAll();

    /**
     * Method for getting all Artist entries.
     * @return <code>List</code> of <code>Artist</code> objects
     */
    @Query("SELECT * FROM ArtistTable")
    LiveData<List<Artist>> getAll();

    /**
     * Method for getting all Artist entries (Ascending order for Name field).
     * @return <code>List</code> of <code>Artist</code> objects
     */
    @Query("SELECT * FROM ArtistTable ORDER BY name ASC")
    LiveData<List<Artist>> getAllByNameAsc();

    /**
     * Method for getting all Artist entries (Descending order for Name field).
     * @return <code>List</code> of <code>Artist</code> objects
     */
    @Query("SELECT * FROM ArtistTable ORDER BY name DESC")
    LiveData<List<Artist>> getAllByNameDesc();

    /**
     * Method for getting a certain Artist by matching Name.
     * @param name Name to search for
     * @return <code>Artist</code> object
     */
    @Query("SELECT * FROM ArtistTable WHERE Name = :name")
    Artist getSong(String name);

    /**
     * Method for getting a certain Artist by matching ArtistID.
     * @param artistID ArtistID to search for.
     * @return <code>Artist</code> object
     */
    @Query("SELECT * FROM ArtistTable WHERE ArtistID = :artistID")
    Artist getSong(Integer artistID);

}
