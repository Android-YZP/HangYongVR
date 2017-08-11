package com.hy.vrfrog.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hy.vrfrog.base.BaseFragment;
import com.hy.vrfrog.main.home.HomeFragment;
import com.hy.vrfrog.main.home.fragments.PersonalLiveHomeFragment;
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
            return new HomeFragment();
        } else if (position == 1) {
           return new BaseFragment();
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
