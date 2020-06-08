package com.haydende.heymusic.GridAdapter;

import android.database.Cursor;

import androidx.recyclerview.widget.RecyclerView;

public interface GridAdapter {

    void changeCursor(Cursor cursor);

}
