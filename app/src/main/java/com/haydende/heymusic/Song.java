package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Song")
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

    @ColumnInfo(name = "Genre")
    private String genre; // what genre is the track?

    @ColumnInfo(name = "File Format")
    private String fileFormat; // what file format is the audio file?

    @ColumnInfo(name = "Filepath")
    private String filepath; // where the file is located

    @ColumnInfo(name = "Duration")
    private Integer duration; // song duration in seconds

    public Song(String albumName, int trackNo, String genre, String fileFormat, String filepath,
                Integer duration) {
        this.trackNo = trackNo;
        this.genre = genre;
        this.fileFormat = fileFormat;
        this.filepath = filepath;
        this.duration = duration;
        // TODO: Implement albumName to albumID
        // TODO: Implement songID generation
    }


}
