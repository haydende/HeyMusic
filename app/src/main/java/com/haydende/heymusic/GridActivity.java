package com.haydende.heymusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
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

        final AlbumListAdapter adapter = new AlbumListAdapter(this);

        MusicViewModel mVM = ViewModelProviders.of(this).get(MusicViewModel.class);
        mVM.getAllAlbums().observe(this, new Observer<List<Album>>() {

            public void onChanged(@Nullable final List<Album> albums) {
                // update cached copy of data
                adapter.setAlbums(albums);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

    }
}
