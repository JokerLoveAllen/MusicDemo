package com.joker.allenmp3.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/9/9.
 */
@DatabaseTable(tableName = "allenMusic")
public class Music implements Parcelable{
    @DatabaseField
    private String musicId;
    @DatabaseField(id = true)
    private String displayName;
    @DatabaseField
    private String artist;
    @DatabaseField
    private String duration;
    @DatabaseField
    private String album;
    @DatabaseField
    private String data;
    private String imageUrl;
    public Music() {
    }

    protected Music(Parcel in) {
        musicId = in.readString();
        displayName = in.readString();
        artist = in.readString();
        duration = in.readString();
        album = in.readString();
        data = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(musicId);
        parcel.writeString(displayName);
        parcel.writeString(artist);
        parcel.writeString(duration);
        parcel.writeString(album);
        parcel.writeString(data);
        parcel.writeString(imageUrl);
    }
}
