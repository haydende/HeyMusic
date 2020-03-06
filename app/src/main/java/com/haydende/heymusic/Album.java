package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "AlbumTable")
public class Album {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "AlbumID")
    private Integer albumID;

    @ForeignKey(entity = Artist.class, parentColumns = "artistID", childColumns = "artistID",
                onDelete = CASCADE)
    @ColumnInfo(name = "ArtistID")
    private Integer artistID;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "YearOfRelease")
    private Integer yearOfRelease;

    public Album (Integer artistID, String title, Integer yearOfRelease) {
        this.artistID = artistID;
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        // TODO: Implement artistName to artistID
        // TODO: Implement albumID generation
    }

    public Integer getAlbumID() {
        return albumID;
    }

    public Integer getArtistID() {
        return artistID;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setAlbumID(@NonNull Integer albumID) {
        this.albumID = albumID;
    }
}
