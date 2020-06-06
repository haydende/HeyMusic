package com.haydende.heymusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaFormat;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.TrackInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class NowPlayingActivity extends AppCompatActivity {

    private static Uri contentUri;

    /**
     * {@link HashMap} for the String values for track attributes that have been passed along by the
     * {@link SongGridAdapter}
     */
    private static HashMap<String, String> trackAttributes;

    private static Bitmap coverArt;

    private static MediaPlayer player;
    private static TrackInfo trackInfo[];
    private static MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
    private RecyclerView recyclerView;

    private ImageButton shuffle;
    private ImageButton rewind;
    private ImageButton playPauseButton;
    private ImageButton forward;
    private ImageButton repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        new Thread(() -> {
            player = MediaPlayer.create(this, contentUri);
            trackInfo = player.getTrackInfo();
            Log.d("player.getTrackInfo()", trackInfo[0].toString());
        }).start();

        recyclerView = findViewById(R.id.nowPlayingRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new NowPlayingAdapter(trackAttributes, coverArt));

        shuffle = findViewById(R.id.nowPlayingButtons_shuffle);
        rewind = findViewById(R.id.nowPlayingButtons_rewind);
        playPauseButton = findViewById(R.id.nowPlayingButtons_playPause);
        forward = findViewById(R.id.nowPlayingButtons_forward);
        repeat = findViewById(R.id.nowPlayingButtons_repeat);

        shuffle.setOnClickListener((View v) -> {
            // TODO
        });

        rewind.setOnClickListener((View v) -> {
            player.seekTo(0);
        });

        playPauseButton.setOnClickListener((View v) -> {
            if (player.isPlaying()) {
                player.pause();
                playPauseButton.setForeground(
                        getResources().getDrawable(
                                R.drawable.ic_play_arrow_black_24dp,
                                null
                        )
                );
            } else {
                player.start();
                playPauseButton.setForeground(
                        getResources().getDrawable(
                                R.drawable.ic_pause_black_24dp,
                                null
                        )
                );
            }
        });

        forward.setOnClickListener((View v) -> {
            player.seekTo(player.getDuration());
        });

        repeat.setOnClickListener((View v) -> {
            player.setLooping(player.isLooping() ? false : true);
        });
    }

    /**
     * Mutator method for the trackAttributes member.
     * @param newTrackAttributes {@link HashMap} of {@link String} Keys and {@link String} Values
     *                           for passing the attributes of the selected track.
     */
    public static void setTrackAttributes(HashMap<String, String> newTrackAttributes) {
        trackAttributes = newTrackAttributes;
    }

    /**
     * Mutator for the {@link this#coverArt}.
     * @param newCoverArt now value for {@link this#coverArt}
     */
    public static void setAlbumCover(Bitmap newCoverArt) { coverArt = newCoverArt; }

    /**
     * Mutator for {@link this#contentUri}.
     * @param newContentUri new value for {@link this#contentUri}
     */
    public static void setContentUri(Uri newContentUri) { contentUri = newContentUri; }
}

