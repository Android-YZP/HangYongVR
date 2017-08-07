package com.hy.vrfrog.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hy.vrfrog.main.home.fragments.LiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.MainFragment;
import com.hy.vrfrog.main.home.fragments.MyFragment;


/**
 * Created by m1762 on 2017/6/2.
 */

public class MainAdapter extends FragmentPagerAdapter {
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            return new LiveHomeFragment();
        } else if (position == 1) {
            return new MainFragment();
        } else if (position == 2) {
            return new MyFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
