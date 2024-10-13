package com.example.gallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_MEDIA = 1;
    private RecyclerView recyclerView;
    private ArrayList<Uri> imageUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 3 columns
        imageUris = new ArrayList<>();

        // Check and request media read permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkMediaPermission()) {
                loadImagesFromExternalStorage();
            } else {
                requestMediaPermission();
            }
        } else {
            loadImagesFromExternalStorage();
        }
    }

    // Check if permission is granted
    private boolean checkMediaPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    // Request permission to access images
    private void requestMediaPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_READ_MEDIA);
    }

    // Load images from external storage using MediaStore
    private void loadImagesFromExternalStorage() {
        String[] projection = {MediaStore.Images.Media._ID};
        Uri imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(imagesUri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(cursor.getLong(id)));
                imageUris.add(imageUri);
            }
            cursor.close();
        }

        ImageAdapter adapter = new ImageAdapter(this, imageUris);
        recyclerView.setAdapter(adapter);
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_MEDIA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImagesFromExternalStorage();
            } else {
                Toast.makeText(this, "Permission denied. Cannot load images.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}