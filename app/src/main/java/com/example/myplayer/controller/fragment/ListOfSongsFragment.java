package com.example.myplayer.controller.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplayer.R;
import com.example.myplayer.controller.activity.PlayActivity;
import com.example.myplayer.model.Song;
import com.example.myplayer.repository.MusicDBRepository;

import java.util.ArrayList;


public class ListOfSongsFragment extends Fragment {
    private RecyclerView mList;
    private MusicDBRepository mMusicDBRepository;
    private ArrayList<Song> mSongs;
    private SongAdapter mAdapter;

    public ListOfSongsFragment() {
        // Required empty public constructor
    }


    public static ListOfSongsFragment newInstance() {
        return new ListOfSongsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusicDBRepository = MusicDBRepository.getInstance(getActivity());
        mSongs = (ArrayList<Song>) mMusicDBRepository.getSongs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initView();
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void initView() {
        if (mAdapter == null)
            mAdapter = new SongAdapter(mSongs);
        mList.setAdapter(mAdapter);
    }

    private void findViews(View view) {
        mList = view.findViewById(R.id.List);
    }

    private class SongHolder extends RecyclerView.ViewHolder {
        private Song mSong;
        private TextView mSongName;
        private TextView mArtistName;
        private TextView mAlbumName;

        public Song getSong() {
            return mSong;
        }

        public void setSong(Song song) {
            mSong = song;
        }

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            mSongName = itemView.findViewById(R.id.songName);
            mArtistName = itemView.findViewById(R.id.artistName);
            mAlbumName = itemView.findViewById(R.id.albumName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMusicDBRepository.setPlaySong(mSong);
                    Intent intent = PlayActivity.newIntent(getContext());
                    startActivity(intent);
                }
            });
        }

        public void bindSong(Song song) {
            mSong = song;
            mSongName.setText(mSong.getSongName());
            mArtistName.setText(mSong.getSongName());
            mAlbumName.setText(mSong.getSongAlbum());
        }
    }

    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {
        public SongAdapter(ArrayList<Song> songs) {
            mSongs = songs;
        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.song_row_layout, parent, false);
            return new SongHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder holder, int position) {
            Song song = mSongs.get(position);
            holder.bindSong(song);
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }
    }
}