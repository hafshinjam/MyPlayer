package com.example.myplayer.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.myplayer.controller.fragment.ListOfAlbumsFragment;
import com.example.myplayer.controller.fragment.ListOfArtistsFragment;
import com.example.myplayer.R;
import com.example.myplayer.controller.fragment.ListOfSongsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mMusicPager;
    private TabLayout mMusicTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
    }

    private void findViews() {
        mMusicPager = findViewById(R.id.MusicPager);
        mMusicTabs = findViewById(R.id.MusicTabs);
    }

    private void initViews() {
        FragmentStateAdapter adapter = new MusicPagerAdapter(this);
        mMusicPager.setAdapter(adapter);
        new TabLayoutMediator(mMusicTabs, mMusicPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Songs");
                        break;
                    case 1:
                        tab.setText("Albums");
                        break;
                    case 2:
                        tab.setText("Artists");
                        break;
                }
            }
        }).attach();
    }

    public class MusicPagerAdapter extends FragmentStateAdapter {

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }


        public MusicPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return ListOfSongsFragment.newInstance();
            if (position == 1)
                return ListOfAlbumsFragment.newInstance();
            else
                return ListOfArtistsFragment.newInstance();

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}