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
import com.example.myplayer.model.Album;
import com.example.myplayer.model.Song;
import com.example.myplayer.repository.MusicDBRepository;

import java.util.ArrayList;

public class ListOfAlbumsFragment extends Fragment {
    private RecyclerView mAlbumView;
    private AlbumAdapter mAdapter;
    private ArrayList<Album> mAlbums;
    private MusicDBRepository mRepository;


    public ListOfAlbumsFragment() {
        // Required empty public constructor
    }


    public static ListOfAlbumsFragment newInstance() {
        return new ListOfAlbumsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicDBRepository.getInstance(getActivity());
        mAlbums = mRepository.getAlbums();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        //todo
        mAlbumView.setLayoutManager(new LinearLayoutManager(getContext()));
        initView();
        return view;
    }

    private void findViews(View view) {
        mAlbumView = view.findViewById(R.id.List);
    }

    private void initView() {
        if (mAdapter == null)
            mAdapter = new AlbumAdapter(mAlbums);
        mAlbumView.setAdapter(mAdapter);
    }

    private class AlbumHolder extends RecyclerView.ViewHolder {
        private Album mAlbum;
        private TextView mAlbumNameText;

        public Album getAlbum() {
            return mAlbum;
        }

        public void setAlbum(Album album) {
            mAlbum = album;
        }

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            mAlbumNameText = itemView.findViewById(R.id.albumRowName);
            //todo implement to go to the play fragment
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRepository.setPlaySong(mAlbum);
                    Intent intent = PlayActivity.newIntent(getContext());
                    startActivity(intent);
                }
            });
        }

        public void bindAlbum(Album album) {
            mAlbum = album;
            mAlbumNameText.setText(mAlbum.getAlbumName());
        }
    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

        public AlbumAdapter(ArrayList<Album> albums) {
            mAlbums = albums;
        }

        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.album_row, parent, false);
            return new AlbumHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
            Album album = mAlbums.get(position);
            holder.bindAlbum(album);
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }
    }
}