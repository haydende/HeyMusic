package com.haydende.heymusic.NowPlaying;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.haydende.heymusic.MediaPlayerManager.MediaPlayerManager;
import com.haydende.heymusic.GridView.SongGridAdapter;
import com.haydende.heymusic.R;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NowPlayingActivity extends AppCompatActivity {

    private static Uri contentUri;

    /**
     * {@link HashMap} for the String values for track attributes that have been passed along by the
     * {@link SongGridAdapter}
     */
    private static HashMap<String, String> trackAttributes;

    private static Uri coverArt;

    private ScheduledExecutorService mExecutor;
    private Runnable mRunnable;

    private NowPlayingAdapter adapter;

    private SeekBar seekBar;
    private TextView position;
    private TextView duration;
    private ImageButton shuffle;
    private ImageButton rewind;
    private ImageButton playPauseButton;
    private ImageButton forward;
    private ImageButton repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_activity);

        seekBar = findViewById(R.id.nowPlayingData_seekBar);
        position = findViewById(R.id.nowPlayingData_currentTime);
        duration = findViewById(R.id.nowPlayingData_duration);
        shuffle = findViewById(R.id.nowPlayingButtons_shuffle);
        rewind = findViewById(R.id.nowPlayingButtons_rewind);
        playPauseButton = findViewById(R.id.nowPlayingButtons_playPause);
        forward = findViewById(R.id.nowPlayingButtons_forward);
        repeat = findViewById(R.id.nowPlayingButtons_repeat);


        mExecutor = Executors.newSingleThreadScheduledExecutor();
        adapter = new NowPlayingAdapter(this, trackAttributes, coverArt);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(MediaPlayerManager.getPosition());
                /*long milli = MediaPlayerManager.getPosition();
                int minutes = (int)((milli / 1000) / 60);
                int seconds = (int)((milli / 1000) % 60);
                Log.i("NowPlayingAdapter", "Duration: " + minutes + ":" + seconds);
                position.setText(minutes + ":" + seconds);*/
            }
        };

        MediaPlayerManager.loadTrack(this, contentUri);
        adapter.fillLayout();

        mExecutor.scheduleAtFixedRate(
                mRunnable,
                0,
                50,
                TimeUnit.MILLISECONDS
        );

        MediaPlayerManager.togglePlayback();

        int milli = MediaPlayerManager.getDuration();
        int minutes = (int)((milli / 1000) / 60);
        int seconds = (int)((milli / 1000) % 60);
        duration.setText(String.format("%1$02d:%2$02d", minutes, seconds));
        togglePlayButton();

        /* Set the onClickListeners for the playback control buttons */
        seekBar.setMax(MediaPlayerManager.getDuration() - 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MediaPlayerManager.seekTo(progress);
                    Log.i("NowPlayingActivity", "User set SeekBar progress");
                } else {
                    // Log.i("NowPlayingActivity", "Progress changed");
                }
                int minutes = ((progress / 1000) / 60);
                int seconds = ((progress / 1000) % 60);
                position.setText(String.format("%1$02d:%2$02d", minutes, seconds));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        shuffle.setOnClickListener((View v) -> {
            // TODO
        });

        rewind.setOnClickListener((View v) -> {
            MediaPlayerManager.skipToBeginning();
        });

        playPauseButton.setOnClickListener((View v) -> {
            MediaPlayerManager.togglePlayback();
            togglePlayButton();
        });

        forward.setOnClickListener((View v) -> {
            MediaPlayerManager.skipToEnd();
        });

        repeat.setOnClickListener((View v) -> {
            MediaPlayerManager.toggleLooping();
        });
    }

    private void togglePlayButton() {
        if (MediaPlayerManager.isPlaying()) {
            playPauseButton.setForeground(
                    getResources().getDrawable(
                            R.drawable.ic_pause_black_24dp
                    )
            );
        } else {
            playPauseButton.setForeground(
                    getResources().getDrawable(
                            R.drawable.ic_play_arrow_black_24dp
                    )
            );
        }
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
    public static void setAlbumCover(Uri newCoverArt) { coverArt = newCoverArt; }

    /**
     * Mutator for {@link this#contentUri}.
     * @param newContentUri new value for {@link this#contentUri}
     */
    public static void setContentUri(Uri newContentUri) { contentUri = newContentUri; }
}

