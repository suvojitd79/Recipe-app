package com.example.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Step {

    @SerializedName("id")
    int id;
    @SerializedName("shortDescription")
    String shortDescription;
    @SerializedName("description")
    String description;
    @SerializedName("videoURL")
    String videoURL;
    @SerializedName("thumbnailURL")
    String thumbnailURL;

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
