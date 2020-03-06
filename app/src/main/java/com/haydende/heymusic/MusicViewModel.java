package com.haydende.heymusic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class MusicViewModel extends AndroidViewModel {

    private MusicRepository mRepo;
    private LiveData<List<Album>> allAlbums;

    public MusicViewModel(@NonNull Application application) {
        super(application);
        mRepo = new MusicRepository(application);
        allAlbums = mRepo.getAllAlbums();
    }

    public void insert(Album a) {
        mRepo.insert(a);
    }

    public void update(Album a) {
        mRepo.update(a);
    }

    public LiveData<List<Album>> getAllAlbums() {
        System.out.println("ViewModel: " + allAlbums.getValue());
        return allAlbums;
    }
}
