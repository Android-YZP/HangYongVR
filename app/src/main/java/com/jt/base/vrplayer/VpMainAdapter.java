package com.jt.base.vrplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jt.base.mainfragments.MainFragment;
import com.jt.base.mainfragments.MyFragment;
import com.jt.base.mainfragments.SearchFragment;


/**
 * Created by m1762 on 2017/6/2.
 */

public class VpMainAdapter extends FragmentPagerAdapter{
    public VpMainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0){
            return new SearchFragment();
        }else if (position == 1){
            return new MainFragment();
        }else if (position == 2){
            return new MyFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
