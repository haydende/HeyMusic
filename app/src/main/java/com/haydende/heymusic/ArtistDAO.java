package com.haydende.heymusic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArtistDAO {

    @Query("SELECT * FROM Artist")
    List<Artist> getArtistList();

    @Insert
    void insertArtist(Artist a);

    @Update
    void updateArtist(Artist a);

}
