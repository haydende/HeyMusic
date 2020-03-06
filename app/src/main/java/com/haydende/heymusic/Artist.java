package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

/**
 * Class for modelling Artists.
 * <p>Uses @Database annotation so it can be established as a Room Persistence Library Database.</p>
 */
@Entity(tableName = "ArtistTable")
public class Artist {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ArtistID")
    private Integer artistID;

    @ColumnInfo(name = "name")
    private String name;

    /**
     * Default constructor for this class. Assigns the following...
     * @param name Name of the artist
     */
    public Artist (String name) {
        this.name = name;
        // TODO: Implement artistID generation
    }

    /**
     * Setter for ArtistID.
     * @param id ID for this album entry
     */
    public void setArtistID(Integer id) {
        artistID = id;
    }

    /**
     * Getter for ArtistID field.
     * @return <code>artistID</code>
     */
    public Integer getArtistID() {
        return artistID;
    }

    /**
     * Getter for Name field.
     * @return <code>name</code>
     */
    public String getName() {
        return name;
    }
}
