package com.haydende.heymusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.haydende.heymusic.Manager.MediaPlayerManager;

import com.haydende.heymusic.Adapter.NowPlayingAdapter;
import com.haydende.heymusic.Adapter.SongGridAdapter;
import com.haydende.heymusic.R;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.io.File;
import java.io.IOException;


public class NowPlayingActivity extends AppCompatActivity {

    private static Uri coverArt;

    private ScheduledExecutorService mExecutor;
    private Runnable mRunnable;

    private Bundle extras;

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
        extras = getIntent().getExtras();

        Uri contentUri = (Uri) extras.get("uri");

        MediaPlayerManager.loadTrack(this, contentUri);
        fillLayout();

        seekBar = findViewById(R.id.nowPlayingData_seekBar);
        position = findViewById(R.id.nowPlayingData_position);
        duration = findViewById(R.id.nowPlayingData_duration);
        shuffle = findViewById(R.id.nowPlayingButtons_shuffle);
        rewind = findViewById(R.id.nowPlayingButtons_rewind);
        playPauseButton = findViewById(R.id.nowPlayingButtons_playPause);
        forward = findViewById(R.id.nowPlayingButtons_forward);
        repeat = findViewById(R.id.nowPlayingButtons_repeat);

        // Create the new Executor (used for updating timer label and SeekBar)
        mExecutor = Executors.newSingleThreadScheduledExecutor();
        // Create the new Runnable (this will be executed by the Executor instance)
        mRunnable = () -> seekBar.setProgress(MediaPlayerManager.getPosition());
        // Load the track into the MediaPlayer
        MediaPlayerManager.loadTrack(this, contentUri);
        // Set the Executor to start the Runnable every 50ms
        mExecutor.scheduleAtFixedRate(mRunnable,0,50, TimeUnit.MILLISECONDS);
        // Start playback of the track
        MediaPlayerManager.togglePlayback();
        // Populate the rest of the layout
        fillLayout();

        // Get the duration of the track (ms)
        int milli = MediaPlayerManager.getDuration();
        // Get the minute value
        int minutes = (int)((milli / 1000) / 60);
        // Get the seconds value
        int seconds = (int)((milli / 1000) % 60);
        // Set the text for the duration TextView (Formatted as mm:ss)
        duration.setText(String.format("%1$02d:%2$02d", minutes, seconds));
        // Toggle the background image for the play button (this time it will become pause)
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
     * Method for adding the data for the current track to the widgets in the NowPlaying layout.
     */
    public void fillLayout() {
        ImageButton albumCover = findViewById(R.id.nowPlayingData_coverImage);
        TextView trackName = findViewById(R.id.nowPlayingData_trackName);
        TextView albumName = findViewById(R.id.nowPlayingData_albumName);
        TextView artistName = findViewById(R.id.nowPlayingData_artistName);

        TextView fileFormat = findViewById(R.id.nowPlayingData_format);
        TextView bitRate = findViewById(R.id.nowPlayingData_bitRate);
        TextView bitDepth = findViewById(R.id.nowPlayingData_bitDepth);
        TextView sampleRate = findViewById(R.id.nowPlayingData_sampleRate);

        Glide.with(this)
                .load(Uri.parse("content://media/external/audio/albumart/" +
                        extras.getString("album_id")))
                .override(600,600)
                .into(albumCover);
        trackName.setText(extras.getString("name"));
        albumName.setText(extras.getString("album_name"));
        artistName.setText(extras.getString("artist_name"));

        // get the metadata using the AudioFileIO API
        String formatString = "format";
        int bitDepthInt = 16;
        String bitRateString = "BitRate";
        String sampleRateString = "Sample Rate";

        try {
            AudioFile audioFile = AudioFileIO.read(new File(extras.getString("data")));
            AudioHeader header = audioFile.getAudioHeader();

            formatString = header.getFormat();
            bitDepthInt = header.getBitsPerSample();
            bitRateString = header.getBitRate();
            sampleRateString = header.getSampleRate();

            Log.d("Bitrate" , header.getBitRate());
            Log.d("Sample Rate", header.getSampleRate());
            Log.d("Bit Depth", "" + header.getBitsPerSample());
            Log.d("Format", header.getFormat());
        } catch (IOException ioE) {

        } catch (CannotReadException cnrE) {

        } catch (TagException tE) {

        } catch (ReadOnlyFileException rofE) {

        } catch (InvalidAudioFrameException iafE) {

        } catch (NullPointerException npE) {

        }
        fileFormat.setText(formatString);
        bitRate.setText(bitRateString + "kb/s");
        bitDepth.setText(bitDepthInt + " bits");
        sampleRate.setText(sampleRateString + "KHz");
    }

}
