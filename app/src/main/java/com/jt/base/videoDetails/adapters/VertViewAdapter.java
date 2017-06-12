package com.jt.base.videoDetails.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jt.base.videoDetails.VideoDetailsFragment;

/**
 * Created by m1762 on 2017/5/25.
 */

public class VertViewAdapter extends FragmentStatePagerAdapter {
    String Data;

    public VertViewAdapter(FragmentManager fm, String Data) {
        super(fm);
        this.Data = Data;
    }

    @Override
    public Fragment getItem(int position) {
        VideoDetailsFragment videoDetailsFragment = new VideoDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Data",Data);
        videoDetailsFragment.setArguments(bundle);
        return videoDetailsFragment;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
