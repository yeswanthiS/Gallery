package com.example.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class FullImageActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ArrayList<Uri> imageUris;
    private int selectedPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        viewPager = findViewById(R.id.viewPager);

        // Get data passed from the MainActivity
        Intent intent = getIntent();
        imageUris = intent.getParcelableArrayListExtra("imageUris");
        selectedPosition = intent.getIntExtra("position", 0);

        // Set up the adapter for ViewPager2
        FullImageAdapter adapter = new FullImageAdapter(this, imageUris);
        viewPager.setAdapter(adapter);

        // Set the currently selected image
        viewPager.setCurrentItem(selectedPosition, false);
    }
}

