package com.haydende.heymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        // get this Activity's Intent
        Intent thisIntent = getIntent();
        // get the extras from this Intent
        Bundle extras = thisIntent.getExtras();

        // get the String values passed in from the SongGridAdapter
        String trackName = extras.getString("TRACK_NAME");
        String artistName = thisIntent.getStringExtra("ARTIST_NAME");
        String albumName = thisIntent.getStringExtra("ALBUM_NAME");
        String albumID = thisIntent.getStringExtra("ALBUM_ID");

        // print the values to see what they are
        Log.d("Track Name", trackName);
        Log.d("Album Name", albumName);
        Log.d("Artist Name", artistName);
        Log.d("AlbumID", albumID);
    }
}
