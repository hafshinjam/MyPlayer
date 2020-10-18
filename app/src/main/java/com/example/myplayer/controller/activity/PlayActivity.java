package com.example.myplayer.controller.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myplayer.R;
import com.example.myplayer.controller.fragment.PlayFragment;
import com.example.myplayer.model.Song;

public class PlayActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context, Song song) {
        return new Intent(context,PlayActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return PlayFragment.newInstance();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}