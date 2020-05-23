package com.haydende.heymusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class NowPlayingActivity extends AppCompatActivity {

    /**
     * {@link HashMap} for the String values for track attributes that have been passed along by the
     * {@link SongGridAdapter}
     */
    private static HashMap<String, String> trackAttributes;

    private static Bitmap coverArt;

    private RecyclerView recyclerView;

    private RecyclerView.Adapter nowPlayingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        recyclerView = findViewById(R.id.nowPlayingRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new NowPlayingAdapter(this, trackAttributes, coverArt));
    }

    /**
     * Method for getting the attributes for the current track.
     * <p>
     *     This method is executed upon the creation of this Activity
     * </p>
     * 
     * @see this#onCreate(Bundle)
     */
    private void loadTrack() {
        String trackName = trackAttributes.get("Title");
        String albumName = trackAttributes.get("Album");
        String artistName = trackAttributes.get("Artist");
        // String albumID;

        // print the values to see what they are
        Log.d("Track Name", trackName);
        Log.d("Album Name", albumName);
        Log.d("Artist Name", artistName);
        // Log.d("AlbumID", albumID);
    }

    /**
     * Mutator method for the trackAttributes member.
     * @param newTrackAttributes {@link HashMap} of {@link String} Keys and {@link String} Values
     *                           for passing the attributes of the selected track.
     */
    public static void setTrackAttributes(HashMap<String, String> newTrackAttributes) {
        trackAttributes = newTrackAttributes;
    }

    public static void setAlbumCover(Bitmap newCoverArt) {
        coverArt = newCoverArt;
    }
}
