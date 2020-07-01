package com.haydende.heymusic.NowPlaying;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haydende.heymusic.MediaPlayerManager.MediaPlayerManager;
import com.haydende.heymusic.GridView.SongGridAdapter;
import com.haydende.heymusic.R;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class NowPlayingActivity extends AppCompatActivity {

    private Uri contentUri;

    private String trackName;

    private String albumID;

    private String albumName;

    private String artistName;

    /**
     * {@link HashMap} for the String values for track attributes that have been passed along by the
     * {@link SongGridAdapter}
     */
    private static HashMap<String, String> trackAttributes;

    private ImageButton shuffle;
    private ImageButton rewind;
    private ImageButton playPauseButton;
    private ImageButton forward;
    private ImageButton repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_activity);
        Bundle extras = getIntent().getExtras();

        contentUri = (Uri) extras.get("uri");
        trackName = extras.getString("name");
        albumID = extras.getString("album_id");
        albumName = extras.getString("album_name");
        artistName = extras.getString("artist_name");

        MediaPlayerManager.loadTrack(this, contentUri);
        fillLayout();

        shuffle = findViewById(R.id.nowPlayingButtons_shuffle);
        rewind = findViewById(R.id.nowPlayingButtons_rewind);
        playPauseButton = findViewById(R.id.nowPlayingButtons_playPause);
        forward = findViewById(R.id.nowPlayingButtons_forward);
        repeat = findViewById(R.id.nowPlayingButtons_repeat);

        MediaPlayerManager.togglePlayback();
        togglePlayButton();

        /* Set the onClickListeners for the playback control buttons */

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
        TextView trackTitle = findViewById(R.id.nowPlayingData_trackName);
        TextView albumTitle = findViewById(R.id.nowPlayingData_albumName);
        TextView artistName = findViewById(R.id.nowPlayingData_artistName);

        TextView fileFormat = findViewById(R.id.nowPlayingData_format);
        TextView bitRate = findViewById(R.id.nowPlayingData_bitRate);
        TextView bitDepth = findViewById(R.id.nowPlayingData_bitDepth);
        TextView sampleRate = findViewById(R.id.nowPlayingData_sampleRate);

        Glide.with(this)
                .load(Uri.parse("content://media/external/audio/albumart" + "/" + albumID))
                .override(600,600)
                .into(albumCover);
        trackTitle.setText(trackName);
        albumTitle.setText(albumName);
        artistName.setText(this.artistName);

        // get the metadata using the AudioFileIO API
        String formatString = "format";
        int bitDepthInt = 16;
        String bitRateString = "BitRate";
        String sampleRateString = "Sample Rate";

        try {
            ParcelFileDescriptor file = getContentResolver().openFileDescriptor(
                    contentUri,
                    "r"
            );
            FileDescriptor fd = file.getFileDescriptor();
            Log.i("NowPlayingActivity", "FileDescriptor: " + fd.toString());
            String path = contentUri.toString();
            AudioFile audioFile = AudioFileIO.read(new File(path));
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

