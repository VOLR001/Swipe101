package com.example.swipe101_manchester;
public class Video {
    private String videoURL;
    private String title;
    private String description;

    public Video() {}

    public Video(String videoURL, String title, String description) {
        this.videoURL = videoURL;
        this.title = title;
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}