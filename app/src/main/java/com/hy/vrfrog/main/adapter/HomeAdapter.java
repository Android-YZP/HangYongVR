package com.hy.vrfrog.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hy.vrfrog.http.responsebean.VideoTypeBean;
import com.hy.vrfrog.main.home.fragments.EnterpriseLiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.PersonalLiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.RecommendFragment;
import com.hy.vrfrog.main.home.fragments.VideoFragment;
import com.hy.vrfrog.utils.UIUtils;

import java.util.List;


/**
 * Created by m1762 on 2017/6/2.
 */

public class HomeAdapter extends FragmentStatePagerAdapter {
    private List<VideoTypeBean.ResultBean> mTitle;

    public HomeAdapter(FragmentManager fm, List<VideoTypeBean.ResultBean> mTitle) {
        super(fm);
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {

        return FragmentFactory.createFragment(position, mTitle);

    }

    @Override
    public int getCount() {
        return mTitle.size() + 3;
    }
}
