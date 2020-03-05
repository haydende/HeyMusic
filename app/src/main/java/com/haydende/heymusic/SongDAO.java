package com.haydende.heymusic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongDAO {

    @Query("SELECT * FROM Song")
    List<Song> getSongList();

    @Insert
    void insertSong(Song s);

    @Update
    void updateSong(Song s);

}
