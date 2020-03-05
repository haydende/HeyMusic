package com.haydende.heymusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GridActivity extends AppCompatActivity {

    private ImageButton [] imgBtnArr = new ImageButton[20];
    private MusicViewModel musicViewModel;
    public GridLayout grid;

    // public declaration to make it accessible in all threads
    public static MusicDatabase mDB;
    public static AlbumDAO albumDAO;
    public static List<Album> albumList;
    public static int numButtons = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = MusicDatabase.getInstance(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);

        final AlbumAdapter adapter = new AlbumAdapter();
        recyclerView.setAdapter(new AlbumAdapter());

        musicViewModel = ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.getAllAlbums().observe(this, albums -> adapter.setAlbums(albums));

        // Using this to find out if any items *should* appear
        System.out.println(adapter.getItemCount());

        /*
        // iterate through all elements of ImageButton array
        for (int i = 0; i < 3; i++) {
            // Fill space with new instance of ImageButton
            imgBtnArr[i] = new ImageButton(this);
            // Set a Layout Parameter to the ImageButton
            imgBtnArr[i].setLayoutParams(new ViewGroup.LayoutParams(250, 250));
            // Add onClickListener to take user to AlbumView
            imgBtnArr[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent albumView = new Intent(((ImageButton)v).getContext(), AlbumActivity.class);
                    // startActivity(albumView);
                }
            });
            // Add the ImageButton to the GridLayout
            recyclerView.addView(imgBtnArr[i]);
            i++;
        }
        */
    }
}
