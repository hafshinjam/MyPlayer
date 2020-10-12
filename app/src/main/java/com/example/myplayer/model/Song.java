package com.example.myplayer.model;

import java.io.Serializable;

public class Song implements Serializable {
    private String songName;
    private String songAlbum;
    private String songArtist;
    private String songPath;

    public Song(String songName, String songAlbum, String songArtist, String songPath) {
        this.songName = songName;
        this.songAlbum = songAlbum;
        this.songArtist = songArtist;
        this.songPath = songPath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}
