package com.haydende.heymusic.MediaPlayerManager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MediaPlayerManager {

    private static MediaPlayerManager instance = null;

    private static MediaPlayer mPlayer;

    public static int getDuration() {
        return (mPlayer == null) ? 0 : mPlayer.getDuration();
    }

    public static int getPosition() {
        return mPlayer.getCurrentPosition();
    }

    public static MediaPlayerManager getInstance() {
        if (instance == null)
            instance = new MediaPlayerManager();
        return instance;
    }

    public static void loadTrack(Context context, Uri contentUri) {
        if (mPlayer != null) {
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(context, contentUri);
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
