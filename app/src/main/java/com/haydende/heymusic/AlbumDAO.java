package com.haydende.heymusic;

<<<<<<< HEAD
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
=======
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
>>>>>>> add503aed975bb61a30525bd7f1ece1c551e9a01

@Dao
public interface AlbumDAO {

<<<<<<< HEAD
    @Query("SELECT * FROM Album")
    LiveData<List<Album>> getAlbumList();

    @Insert
    void insert(Album a);

    @Update
    void update(Album a);

=======
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
>>>>>>> add503aed975bb61a30525bd7f1ece1c551e9a01
}
