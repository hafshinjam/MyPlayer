package com.example.myplayer.controller.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.myplayer.R;

public class playActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return null;
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