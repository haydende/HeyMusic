package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

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

    public Song(Integer albumID, int trackNo, String genre, String fileFormat, String filepath,
                Integer duration) {
        this.albumID = albumID;
        this.trackNo = trackNo;
        this.genre = genre;
        this.fileFormat = fileFormat;
        this.filepath = filepath;
        this.duration = duration;
        // TODO: Implement albumName to albumID
        // TODO: Implement songID generation
    }

    @NonNull
    public Integer getSongID() {
        return songID;
    }

    public Integer getAlbumID() {
        return albumID;
    }

    public Integer getTrackNo() {
        return trackNo;
    }

    public String getGenre() {
        return genre;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getFilepath() {
        return filepath;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setSongID(@NonNull Integer songID) {
        this.songID = songID;
    }
}
