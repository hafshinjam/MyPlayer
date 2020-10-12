package com.example.myplayer.model;

import java.util.Objects;

public class Album {
    private String albumName;
    private String albumArtist;

    public Album(String albumName, String albumArtist) {
        this.albumName = albumName;
        this.albumArtist = albumArtist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(albumName, album.albumName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumName);
    }
}
