package com.haydende.heymusic.NowPlaying;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haydende.heymusic.R;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class NowPlayingAdapter {

    /**
     * {@link NowPlayingActivity} instance that created this {@link NowPlayingAdapter} instance.
     */
    private final Activity activity;

    /**
     * HashMap containing the String attributes to be displayed in the now_playing_item.xml layout.
     */
    private final HashMap<String, String> attributes;

    /**
     * Bitmap image for the ImageButton in the now_playing_item.xml layout.
     */
    private final Uri coverArt;

    /**
     * Default constructor for this class.
     * @param attributes HashMap value for the attributes member
     * @param coverArt Bitmap value for the coverArt member
     */
    public NowPlayingAdapter(Activity activity, HashMap<String, String> attributes, Uri coverArt) {
        this.activity = activity;
        this.attributes = attributes;
        this.coverArt = coverArt;
    }

    /**
     * Method for adding the data for the current track to the widgets in the NowPlaying layout.
     */
    public void fillLayout() {
        ImageButton albumCover = activity.findViewById(R.id.nowPlayingData_coverImage);
        TextView trackName = activity.findViewById(R.id.nowPlayingData_trackName);
        TextView albumName = activity.findViewById(R.id.nowPlayingData_albumName);
        TextView artistName = activity.findViewById(R.id.nowPlayingData_artistName);

        TextView fileFormat = activity.findViewById(R.id.nowPlayingData_format);
        TextView bitRate = activity.findViewById(R.id.nowPlayingData_bitRate);
        TextView bitDepth = activity.findViewById(R.id.nowPlayingData_bitDepth);
        TextView sampleRate = activity.findViewById(R.id.nowPlayingData_sampleRate);

        Glide.with(activity)
                .load(coverArt)
                .override(600,600)
                .into(albumCover);
        trackName.setText(attributes.get("Title"));
        albumName.setText(attributes.get("Album"));
        artistName.setText(attributes.get("Artist"));

        // get the metadata using the AudioFileIO API
        String formatString = "format";
        int bitDepthInt = 16;
        String bitRateString = "BitRate";
        String sampleRateString = "Sample Rate";

        try {
            File track = new File(attributes.get("Data"));
            AudioFile audioFile = AudioFileIO.read(track);
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
