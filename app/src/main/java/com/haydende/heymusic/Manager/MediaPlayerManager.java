package com.haydende.heymusic.Manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.haydende.heymusic.Domain.Song;

import java.util.HashMap;

public abstract class MediaPlayerManager {

    private static MediaPlayerManager instance = null;

    private static MediaPlayer mPlayer;

    private static HashMap<Integer, Song> songs = new HashMap<>();

    private static Integer currentTrackNum = 0;

    public static Song getCurrentSong() {
        return songs.get(currentTrackNum);
    }

    public void addSong(Song song) {
        songs.put(songs.size(), song);
    }

    public static void loadCurrentTrack(Context context) {
        loadTrack(context, currentTrackNum);
    }

    public static void loadTrack(Context context, Integer num) {
        if (mPlayer != null) {
            mPlayer.release();
        }
        Song song = songs.get(num);
        mPlayer = MediaPlayer.create(
                context,
                song.getUri()
        );
    }

    public static int getDuration() {
        return (mPlayer == null) ? 0 : mPlayer.getDuration();
    }

    public static int getPosition() {
        return mPlayer.getCurrentPosition();
    }

    public static Boolean isPlaying() {
        return (mPlayer == null) ? false : mPlayer.isPlaying();
    }

    public static Boolean isLooping() {
        return (mPlayer == null) ? false : mPlayer.isLooping();
    }

    public static void togglePlayback() {
        if (mPlayer.isPlaying() == false) {
            mPlayer.start();
        } else {
            mPlayer.pause();
        }
    }

    public static void stopPlayback() {
        if (mPlayer.isPlaying())
            mPlayer.stop();
    }

    public static void skipToEnd() {
        mPlayer.seekTo(mPlayer.getDuration());
    }

    public static void skipToBeginning() {
        seekTo(0);
    }

    public static void seekTo(int msec) {
        if (msec < mPlayer.getDuration() && msec >= 0)
            mPlayer.seekTo(msec);
    }

    public static void toggleLooping() {
        if (mPlayer != null) {
            if (mPlayer.isLooping())
                mPlayer.setLooping(false);
            else
                mPlayer.setLooping(true);
        }
    }
}
