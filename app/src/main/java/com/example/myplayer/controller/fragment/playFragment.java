package com.example.myplayer.controller.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myplayer.R;
import com.example.myplayer.model.Song;
import com.example.myplayer.repository.MusicDBRepository;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link playFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class playFragment extends Fragment {
    private MediaPlayer mPlayer;
    private ArrayList<Song> mSongs;
    private MusicDBRepository mRepository;

    public playFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static playFragment newInstance() {
        return new playFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicDBRepository.getInstance(getContext());
        mSongs = mRepository.getPlaySong();
        mPlayer = mRepository.getMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {

    }
}