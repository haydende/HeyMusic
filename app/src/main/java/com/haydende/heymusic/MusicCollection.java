package com.haydende.heymusic;

import android.database.Cursor;
import android.provider.MediaStore;

import java.util.List;

public class MusicCollection {

    private static Cursor mediaStoreCursor;
    private static MediaStore mediaStore = new MediaStore();

    public static List<Song> getSongs() {
        return null;
    }

}
