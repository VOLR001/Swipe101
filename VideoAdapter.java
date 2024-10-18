package com.example.swipe101_manchester;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> videoList;

    public VideoAdapter(List<Video> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.setVideoData(video);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        VideoView videoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.videoTitle);
            descriptionTextView = itemView.findViewById(R.id.videoDescription);
            videoView = itemView.findViewById(R.id.videoView); // Initialize VideoView
        }

        public void setVideoData(Video videoItem) {
            titleTextView.setText(videoItem.getTitle());
            descriptionTextView.setText(videoItem.getDescription());
    //set values and URI
           Uri videoUri = Uri.parse(videoItem.getVideoURL());
            videoView.setVideoURI(videoUri);

            //MediaPlayer to control playback and forwth
            videoView.setOnPreparedListener(mp -> {
                mp.setOnVideoSizeChangedListener((mediaPlayer, width, height) -> {
                    videoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                });
                videoView.start(); //start playing
            });

            videoView.setOnCompletionListener(mp -> {
                //video completion loop or prepare for next video
                videoView.seekTo(0); //go back to start
            });
        }
    }
}