package com.hy.vrfrog.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.hy.vrfrog.http.responsebean.VideoTypeBean;
import com.hy.vrfrog.main.home.fragments.LiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.MainFragment;
import com.hy.vrfrog.main.home.fragments.VideoFragment;

import java.util.List;


/**
 * Created by m1762 on 2017/6/2.
 */

public class HomeAdapter extends FragmentPagerAdapter {
    private List<VideoTypeBean.ResultBean> mTitle;

    public HomeAdapter(FragmentManager fm, List<VideoTypeBean.ResultBean> mTitle) {
        super(fm);
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return new VideoFragment(mTitle.get(position).getId());
    }

    @Override
    public int getCount() {
        return mTitle.size();
    }
}
