package com.haydende.heymusic.NowPlaying;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder> {

    /**
     * HashMap containing the String attributes to be displayed in the now_playing_item.xml layout.
     */
    private final HashMap<String, String> attributes;

    /**
     * Bitmap image for the ImageButton in the now_playing_item.xml layout.
     */
    private final Bitmap coverArt;

    /**
     * Default constructor for this class.
     * @param attributes HashMap value for the attributes member
     * @param coverArt Bitmap value for the coverArt member
     */
    public NowPlayingAdapter(HashMap attributes, Bitmap coverArt) {
        this.attributes = attributes;
        this.coverArt = coverArt;
    }

    @NonNull
    @Override
    public NowPlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.now_playing_item, parent, false);
        return new NowPlayingAdapter.NowPlayingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingViewHolder holder, int position) {
        holder.albumCover.setImageBitmap(coverArt);
        holder.trackName.setText(attributes.get("Title"));
        holder.albumName.setText(attributes.get("Album"));
        holder.artistName.setText(attributes.get("Artist"));

        // get the metadata using the AudioFileIO API
        String format = "format";
        int bitDepth = 16;
        String bitRate = "BitRate";
        String sampleRate = "Sample Rate";

        try {
            File track = new File(attributes.get("Data"));
            AudioFile audioFile = AudioFileIO.read(track);
            AudioHeader header = audioFile.getAudioHeader();

            format = header.getFormat();
            bitDepth = header.getBitsPerSample();
            bitRate = header.getBitRate();
            sampleRate = header.getSampleRate();

            Log.d("Bitrate" , header.getBitRate());
            Log.d("Sample Rate", header.getSampleRate());
            Log.d("Bit Depth", "" + header.getBitsPerSample());
            Log.d("Format", header.getFormat());
        } catch (IOException ioE) {

        } catch (CannotReadException cnrE) {

        } catch (TagException tE) {

        } catch (ReadOnlyFileException rofE) {

        } catch (InvalidAudioFrameException iafE) {

        }
        holder.fileFormat.setText(format);
        holder.bitRate.setText(bitRate + "kb/s");
        holder.bitDepth.setText(bitDepth + " bits");
        holder.sampleRate.setText(sampleRate + "KHz");
    }

    @Override
    public int getItemCount() {
        return attributes == null ? 0 : 1;
    }

    public static class NowPlayingViewHolder extends RecyclerView.ViewHolder {

        /**
         * Instance member to represent {@code nowPlayingItem.coverImage} in
         * {@code now_playing_item.xml}.
         */
        public final ImageButton albumCover;

        /**
         * Instance member to represent {@code nowPlayingItem.seekBar} in
         * {@code now_playing_item.xml}.
         */
        public final SeekBar seekBar;

        /**
         * Instance member to represent {@code nowPlayingItem.trackName} in
         * {@code now_playing_item.xml}.
         */
        public final TextView trackName;

        /**
         * Instance member to represent {@code nowPlayingItem.albumName} in
         * {@code now_playing_item.xml}.
         */
        public final TextView albumName;

        /**
         * Instance member to represent {@code nowPlayingItem.artistName} in
         * {@code now_playing_item.xml}.
         */
        public final TextView artistName;

        /**
         * Instance member to represent {@code nowPlayingItem.fileFormat} in
         * {@code now_playing_item.xml}.
         */
        public final TextView fileFormat;

        /**
         * Instance member to represent {@code nowPlayingItem.bitRate} in
         * {@code now_playing_item.xml}.
         */
        public final TextView bitRate;

        /**
         * Instance member to represent {@code nowPlayingItem.bitDepth} in
         * {@code now_playing_item.xml}.
         */
        public final TextView bitDepth;

        /**
         * Instance member to represent {@code nowPlayingItem.sampleRate} in
         * {@code now_playing_item.xml}.
         */
        public final TextView sampleRate;

        /**
         * Default constructor for this class.
         * @param itemView View created from an xml layout in
         *                 {@link NowPlayingAdapter#onBindViewHolder(NowPlayingViewHolder, int)}.
         *                 In this case the layout is now_playing_item.xml
         */
        public NowPlayingViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.nowPlayingItem_coverImage);
            seekBar = itemView.findViewById(R.id.nowPlayingItem_seekBar);
            trackName = itemView.findViewById(R.id.nowPlayingItem_trackName);
            albumName = itemView.findViewById(R.id.nowPlayingItem_albumName);
            artistName = itemView.findViewById(R.id.nowPlayingItem_artistName);
            fileFormat = itemView.findViewById(R.id.nowPlayingItem_format);
            bitRate = itemView.findViewById(R.id.nowPlayingItem_bitRate);
            bitDepth = itemView.findViewById(R.id.nowPlayingItem_bitDepth);
            sampleRate = itemView.findViewById(R.id.nowPlayingItem_sampleRate);
        }
    }
}
