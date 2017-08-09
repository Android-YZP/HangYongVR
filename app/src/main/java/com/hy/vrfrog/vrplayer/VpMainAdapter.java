package com.hy.vrfrog.vrplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.hy.vrfrog.main.home.HomeFragment;
import com.hy.vrfrog.main.home.fragments.PersonalLiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.MainFragment;


/**
 * Created by m1762 on 2017/6/2.
 */

public class VpMainAdapter extends FragmentPagerAdapter {
    private DrawerLayout mDlLayout;
    private ListView mLvDrawerItem;
    private ViewPager mViewpager;

    public VpMainAdapter(FragmentManager fm, ListView mLvDrawerItem, DrawerLayout mDlLayout) {
        super(fm);
    }

    public VpMainAdapter(FragmentManager fm, ListView mLvDrawerItem, DrawerLayout mDlLayout, ViewPager mViewpager) {
        super(fm);
        this.mDlLayout = mDlLayout;
        this.mLvDrawerItem = mLvDrawerItem;
        this.mViewpager = mViewpager;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            return new PersonalLiveHomeFragment() ;
        } else if (position == 1) {
            return new MainFragment(mLvDrawerItem,  mDlLayout,mViewpager);
        } else if (position == 2) {
            return new HomeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
