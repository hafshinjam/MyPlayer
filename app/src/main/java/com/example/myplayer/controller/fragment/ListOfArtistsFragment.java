package com.example.myplayer.controller.fragment;

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
import com.example.myplayer.model.Artist;
import com.example.myplayer.repository.MusicDBRepository;

import java.util.ArrayList;


public class ListOfArtistsFragment extends Fragment {
    private RecyclerView mArtistListView;
    private ArrayList<Artist> mArtists;
    private MusicDBRepository mRepository;
    private ArtistAdapter mAdapter;


    public ListOfArtistsFragment() {
        // Required empty public constructor
    }


    public static ListOfArtistsFragment newInstance() {
        return new ListOfArtistsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicDBRepository.getInstance(getActivity());
        mArtists = mRepository.getArtists();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initView();
        mArtistListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void findViews(View view) {
        mArtistListView = view.findViewById(R.id.List);
    }

    private void initView() {
        if (mAdapter == null)
            mAdapter = new ArtistAdapter(mArtists);
        mArtistListView.setAdapter(mAdapter);
    }

    private class ArtistHolder extends RecyclerView.ViewHolder {
        private Artist mArtist;
        private TextView mArtistNameText;

        public Artist getArtist() {
            return mArtist;
        }

        public void setArtist(Artist artist) {
            mArtist = artist;
        }

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            mArtistNameText = itemView.findViewById(R.id.albumRowName);
            //Todo set click listener to go to play fragment
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bindArtist(Artist artist) {
            mArtist = artist;
            mArtistNameText.setText(mArtist.getName());
        }
    }

    private class ArtistAdapter extends RecyclerView.Adapter<ArtistHolder> {

        public ArtistAdapter(ArrayList<Artist> artists) {
            mArtists = artists;
        }

        @NonNull
        @Override
        public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.artist_row, parent, false);
            return new ArtistHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
            Artist artist = mArtists.get(position);
            holder.bindArtist(artist);
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }
    }
}