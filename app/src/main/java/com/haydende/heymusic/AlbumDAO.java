package com.haydende.heymusic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import java.util.List;

/**
 * Data Access Object for the Album table.
 * <p>Room generates classes for implementing the methods below.</p>
 */
@Dao
public interface AlbumDAO {

    /**
     * Method for inserting a new Album entry.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Album a);

    /**
     * Method for executing <code>DELETE</code> query.
     */
    @Query("DELETE FROM AlbumTable")
    void deleteAll();

    /**
     * Method for getting all Album entries.
     * @return <code>List</code> of <code>Album</code> objects.
     */
    @Query("SELECT * FROM AlbumTable")
    LiveData<List<Album>> getAll();

    /**
     * Method for getting all Album entries (Ascending order for Title field).
     * @return <code>List</code> of <code>Album</code> objects.
     */
    @Query("SELECT * FROM AlbumTable ORDER BY title ASC")
    LiveData<List<Album>> getAllByNameAsc();

    /**
     * Method for getting all Album entries (Descending order for Title field).
     * @return <code>List</code> of <code>Album</code> objects.
     */
    @Query("SELECT * FROM AlbumTable ORDER BY title DESC")
    LiveData<List<Album>> getAllByNameDesc();

    /**
     * Method for getting a certain Album by matching Title.
     * @param title Title to search for
     * @return <code>Album</code> object
     */
    @Query("SELECT * FROM AlbumTable WHERE Title = :title")
    Album getAlbum(String title);

    /**
     * Method for getting a certain Album by matching AlbumID.
     * @param albumID AlbumID to search for
     * @return <code>Album</code> object
     */
    @Query("SELECT * FROM AlbumTable WHERE AlbumID = :albumID")
    Album getAlbum(Integer albumID);
}
