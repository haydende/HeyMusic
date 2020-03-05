package com.haydende.heymusic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlbumDAO {

    @Query("SELECT * FROM Album")
    LiveData<List<Album>> getAlbumList();

    @Insert
    void insert(Album a);

    @Update
    void update(Album a);

}
