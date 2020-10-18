package com.example.myplayer.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.myplayer.model.Album;
import com.example.myplayer.model.Artist;
import com.example.myplayer.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicDBRepository {
    private Context mContext;
    private ArrayList<Song> mSongs;
    private ArrayList<Album> mAlbums;
    private ArrayList<Artist> mArtists;
    private ArrayList<Song> mPlaySong;
    private static MediaPlayer mediaPlayer;
    private Song mCurrentSongToPlay;

    private static MusicDBRepository sMusicDBRepository;

    public static MusicDBRepository getInstance(Context context) {
        if (sMusicDBRepository == null)
            sMusicDBRepository = new MusicDBRepository(context);
        return sMusicDBRepository;
    }

    public MusicDBRepository(Context context) {
        mContext = context.getApplicationContext();
        mSongs = new ArrayList<>();
        mAlbums = new ArrayList<>();
        mArtists = new ArrayList<>();
        mPlaySong = new ArrayList<>();
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = resolver.query(uri, null, selection, null, sortOrder);
        int count;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getCount();
            if (count > 0)
                while (!cursor.isAfterLast()) {
                    mSongs.add(new Song(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))));
                    cursor.moveToNext();
                }
            cursor.close();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA).build());
    }

    public Song getCurrentSongToPlay() {
        return mCurrentSongToPlay;
    }

    public void setCurrentSongToPlay(Song currentSongToPlay) {
        mCurrentSongToPlay = currentSongToPlay;
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MusicDBRepository.mediaPlayer = mediaPlayer;
    }

    public ArrayList<Artist> getArtists() {
        mArtists.clear();
        for (Song song : mSongs) {
            Artist artist = new Artist(song.getSongArtist());
            if (!mArtists.contains(artist))
                mArtists.add(artist);

        }
        return mArtists;
    }

    public ArrayList<Album> getAlbums() {
        mAlbums.clear();
        for (Song song : mSongs) {
            Album album = new Album(song.getSongAlbum(), song.getSongArtist());
            if (!mAlbums.contains(album))
                mAlbums.add(album);
        }
        return mAlbums;
    }

    public void setPlaySong(Song song) {
        mPlaySong = mSongs;
        setCurrentSongToPlay(song);
    }

    public void setPlaySong(Album album) {
        mPlaySong.clear();
        for (Song song : mSongs) {
            if (song.getSongAlbum().equals(album.getAlbumName()))
                mPlaySong.add(song);
        }
        setCurrentSongToPlay(mPlaySong.get(0));
    }

    public void setPlaySong(Artist artist) {
        mPlaySong.clear();
        for (Song song : mSongs) {
            if (song.getSongArtist().equals(artist.getName()))
                mPlaySong.add(song);
        }
        setCurrentSongToPlay(mPlaySong.get(0));
    }

    public ArrayList<Song> getPlaySong() {
        return mPlaySong;
    }
}
