package com.example.myplayer.controller.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myplayer.R;
import com.example.myplayer.model.Song;
import com.example.myplayer.repository.MusicDBRepository;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {

    private ImageView mAlbumArts;
    private TextView mSongNameText;
    private TextView mArtistNameText;
    private TextView mAlbumNameText;
    private SeekBar mMusicSeeker;
    private ImageButton mPreviousButton;
    private ImageButton mRepeatOneButton;
    private ImageButton mRepeatAllButton;
    private ImageButton mShuffleButton;
    private ImageButton mNextButton;

    private MediaPlayer mPlayer;
    private ArrayList<Song> mSongs;
    private Song mCurrentSong;
    private MusicDBRepository mRepository;
    private int mPlayIndex;
    private boolean mRepeatAll = false;
    private boolean mRepeatOne = false;
    private boolean mShuffle = false;

    public PlayFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance() {
        return new PlayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicDBRepository.getInstance(getContext());
        mSongs = mRepository.getPlaySong();
        mPlayer = mRepository.getMediaPlayer();
        mCurrentSong = mRepository.getCurrentSongToPlay();
        mPlayIndex = mSongs.indexOf(mCurrentSong);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        findViews(view);
        setListeners();
        setViews();
        playCurrentSong();
        return view;
    }

    private void findViews(View view) {
        mSongNameText = view.findViewById(R.id.musicName);
        mAlbumArts = view.findViewById(R.id.albumArt);
        mArtistNameText = view.findViewById(R.id.musicArtist);
        mAlbumNameText = view.findViewById(R.id.musicAlbum);
        mMusicSeeker = view.findViewById(R.id.musicSeeker);
        mPreviousButton = view.findViewById(R.id.previousButton);
        mNextButton = view.findViewById(R.id.nextButton);
        mShuffleButton = view.findViewById(R.id.shuffleButton);
        mRepeatAllButton = view.findViewById(R.id.repeatAllButton);
        mRepeatOneButton = view.findViewById(R.id.repeatOneButton);
    }

    private void setListeners() {
        mShuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShuffle = !mShuffle;
            }
        });
        mRepeatOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRepeatOne) {
                    mRepeatOne = false;
                    mRepeatOneButton.setBackgroundColor(getResources().
                            getColor(R.color.silver, null));
                } else if (mRepeatAll) {
                    mRepeatOne = true;
                    mRepeatAll = false;
                    mRepeatAllButton.setBackgroundColor(getResources().
                            getColor(R.color.silver, null));
                    mRepeatOneButton.setBackgroundColor(getResources().
                            getColor(R.color.grey, null));
                } else {
                    mRepeatOne = true;
                    mRepeatOneButton.setBackgroundColor(getResources().
                            getColor(R.color.grey, null));
                }
            }
        });
        mRepeatAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRepeatAll) {
                    mRepeatAll = false;
                    mRepeatAllButton.setBackgroundColor(getResources().
                            getColor(R.color.silver, null));
                } else if (mRepeatOne) {
                    mRepeatAll = true;
                    mRepeatOne = false;
                    mRepeatOneButton.setBackgroundColor(getResources().
                            getColor(R.color.silver, null));
                    mRepeatAllButton.setBackgroundColor(getResources().
                            getColor(R.color.grey, null));
                } else {
                    mRepeatAll = true;
                    mRepeatAllButton.setBackgroundColor(getResources().
                            getColor(R.color.grey, null));
                }
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayIndex == mSongs.size() - 1)
                    mPlayIndex = 0;
                else
                    mPlayIndex++;
                mCurrentSong = mSongs.get(mPlayIndex);
                setViews();
                playCurrentSong();
            }
        });
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayIndex == 0)
                    mPlayIndex = mSongs.size() - 1;
                else
                    mPlayIndex--;
                mCurrentSong = mSongs.get(mPlayIndex);
                setViews();
                playCurrentSong();
            }
        });
        mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
        mMusicSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mPlayer.seekTo(progress * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setViews() {
        mSongNameText.setText(mCurrentSong.getSongName());
        mArtistNameText.setText(mCurrentSong.getSongArtist());
        mAlbumNameText.setText(mCurrentSong.getSongAlbum());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mCurrentSong.getSongPath());
        byte[] data = mmr.getEmbeddedPicture();
        Bitmap bitmap;
        if (data != null) {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            mAlbumArts.setImageBitmap(bitmap);
        } else
            mAlbumArts.setImageDrawable(getResources().getDrawable(R.drawable.ic_album_art, null));
    }

    private void playCurrentSong() {
        mPlayer.reset();
        if (mPlayer.isPlaying())
            mPlayer.stop();
        try {
            mPlayer.setDataSource(mCurrentSong.getSongPath());
            mPlayer.prepare();
            mPlayer.start();
            setSeekBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSeekBar() {
        mMusicSeeker.setProgress(mPlayer.getCurrentPosition() / 1000);
        int total = mPlayer.getDuration();
        mMusicSeeker.setMax(total / 1000);
        final Handler mHandler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mPlayer != null) {
                    int mCurrentPosition = mPlayer.getCurrentPosition() / 1000;
                    mMusicSeeker.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });
    }

}