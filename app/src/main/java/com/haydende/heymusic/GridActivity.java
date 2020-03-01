package com.haydende.heymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class GridActivity extends AppCompatActivity {

    private ImageButton [] imgBtnArr = new ImageButton[200];
    public GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a variable for the GridLayout created in XML
        GridLayout grid = findViewById(R.id.grid);
        // set the column count to 5
        grid.setColumnCount(4);

        // iterate through all elements of ImageButton array
        for (int i = 0; i < imgBtnArr.length; i++) {
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
            grid.addView(imgBtnArr[i]);
        }
    }
}
