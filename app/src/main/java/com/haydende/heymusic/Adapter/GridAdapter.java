package com.haydende.heymusic.Adapter;

import android.database.Cursor;

public interface GridAdapter {

    void changeCursor(Cursor cursor);

    String toString();

}
