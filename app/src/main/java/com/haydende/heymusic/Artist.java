package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@Entity(tableName = "ArtistTable")
public class Artist {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ArtistID")
    private Integer artistID;

    @ColumnInfo(name = "name")
    private String name;

    public Artist (String name) {
        this.name = name;
        // TODO: Implement artistID generation
    }

    public void setArtistID(Integer id) {
        artistID = id;
    }

    public Integer getArtistID() {
        return artistID;
    }

    public String getName() {
        return name;
    }
}
