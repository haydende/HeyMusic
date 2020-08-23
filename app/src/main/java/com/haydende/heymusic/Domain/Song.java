package com.haydende.heymusic.Domain;

import android.net.Uri;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Song {

    /**
     * Uri for the song file this object represents.
     */
    private Uri uri;

    /**
     * The title of the song.
     */
    private String title;

    /**
     * Name of the album this song is a part of.
     */
    private String albumName;

    /**
     * The name of the artist that composed this song.
     */
    private String artistName;

    /**
     * File format information for this song.
     */
    private String format;

    /**
     * Bit rate for the file of this song.
     */
    private String bitrate;

    /**
     * The bit depth for the file of this song.
     */
    private String bitdepth;

    /**
     * The sample rate for the file of this song.
     */
    private String samplerate;
}
