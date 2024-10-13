package com.example.gallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FullImageAdapter extends RecyclerView.Adapter<FullImageAdapter.FullImageViewHolder> {

    private Context context;
    private ArrayList<Uri> imageUris;

    public FullImageAdapter(Context context, ArrayList<Uri> imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }

    @NonNull
    @Override
    public FullImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.full_image_item, parent, false);
        return new FullImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FullImageViewHolder holder, int position) {
        Uri imageUri = imageUris.get(position);
        Glide.with(context).load(imageUri).into(holder.imageView); // Load full image
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public static class FullImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public FullImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fullImageView);
        }
    }
}
