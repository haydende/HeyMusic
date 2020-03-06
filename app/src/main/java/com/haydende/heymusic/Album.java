package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Class for modelling Albums.
 * <p>Uses @Database annotation so it can be established as a Room Persistence Library Database.</p>
 */
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

    /**
     * Default constructor for this class. Assigns the following...
     * @param artistID Unique identifier for database entry
     * @param title Title of the album
     * @param yearOfRelease Year of album's release
     */
    public Album (Integer artistID, String title, Integer yearOfRelease) {
        this.artistID = artistID;
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        // TODO: Implement artistName to artistID
        // TODO: Implement albumID generation
    }

    /**
     * Getter for AlbumID field.
     * @return <code>albumID</code>
     */
    public Integer getAlbumID() {
        return albumID;
    }

    /**
     * Getter for ArtistID field.
     * @return <code>artistID</code>
     */
    public Integer getArtistID() {
        return artistID;
    }

    /**
     * Getter for Title field.
     * @return <code>title</code>
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for YearOfRelease field.
     * @return <code>yearOfRelease</code>
     */
    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    /**
     * Setter for AlbumID field.
     * @param albumID ID for this Album entry
     */
    public void setAlbumID(@NonNull Integer albumID) {
        this.albumID = albumID;
    }
}
