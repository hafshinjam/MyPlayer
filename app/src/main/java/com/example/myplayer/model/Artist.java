package com.example.myplayer.model;

import java.util.Objects;

public class Artist {
    private String Name;

    public Artist(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(Name, artist.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }
}
