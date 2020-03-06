package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Class for modelling Songs.
 * <p>Uses @Database annotation so it can be established as a Room Persistence Library Database.</p>
 */
@Entity(tableName = "SongTable")
public class Song {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "SongID")
    private Integer songID; // unique identifier for this song

    @ForeignKey(entity = Album.class, parentColumns = "AlbumID", childColumns = "AlbumID",
            onDelete = CASCADE)
    @ColumnInfo(name = "AlbumID")
    private Integer albumID; // unique identifier for the song's album

    @ColumnInfo(name = "TrackNo")
    private Integer trackNo; // track number within its album

    @ColumnInfo(name = "Title")
    private String title; // track title

    @ColumnInfo(name = "Genre")
    private String genre; // what genre is the track?

    @ColumnInfo(name = "File Format")
    private String fileFormat; // what file format is the audio file?

    @ColumnInfo(name = "Filepath")
    private String filepath; // where the file is located

    @ColumnInfo(name = "Duration")
    private Integer duration; // song duration in seconds

    /**
     * Default constructor for this class. Assigns the following...
     * @param albumID ID for corresponding Album entry
     * @param trackNo Track number for the song's album
     * @param title Title of this track
     * @param genre Genre of this track
     * @param fileFormat File format of this track
     * @param filepath Filepath for the file
     * @param duration Duration of the track (in seconds)
     */
    public Song(Integer albumID, int trackNo, String title, String genre, String fileFormat, String filepath,
                Integer duration) {
        this.albumID = albumID;
        this.trackNo = trackNo;
        this.title = title;
        this.genre = genre;
        this.fileFormat = fileFormat;
        this.filepath = filepath;
        this.duration = duration;
        // TODO: Implement albumName to albumID
        // TODO: Implement songID generation
    }

    /**
     * Getter for SongID field.
     * @return <code>songID</code>
     */
    @NonNull
    public Integer getSongID() {
        return songID;
    }

    /**
     * Getter for AlbumID field.
     * @return <code>albumID</code>
     */
    public Integer getAlbumID() {
        return albumID;
    }

    /**
     * Getter for Title field.
     * @return <code>title</code>
     */
    public String getTitle() { return title; }

    /**
     * Getter for TrackNo field.
     * @return <code>trackNo</code>
     */
    public Integer getTrackNo() {
        return trackNo;
    }

    /**
     * Getter for Genre field.
     * @return <code>genre</code>
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Getter for File Format field.
     * @return <code>fileFormat</code>
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * Getter for Filepath field.
     * @return <code>filepath</code>
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Getter for Duration field.
     * @return <code>duration</code>
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Setter for SongID field.
     * @param songID ID for this Song entry
     */
    public void setSongID(@NonNull Integer songID) {
        this.songID = songID;
    }
}
