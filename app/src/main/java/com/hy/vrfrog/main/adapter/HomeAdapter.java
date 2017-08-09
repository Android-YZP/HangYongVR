package com.hy.vrfrog.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hy.vrfrog.http.responsebean.VideoTypeBean;
import com.hy.vrfrog.main.home.fragments.PersonalLiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.RecommendFragment;
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
        if (position == 0){
            return new RecommendFragment();
        }else if (position == 1){
            return new PersonalLiveHomeFragment();
        }else if (position == 2){
            return new PersonalLiveHomeFragment();
        }else {
            return new VideoFragment(mTitle.get(position - 3 ).getId());
        }

    }

    @Override
    public int getCount() {
        return mTitle.size() + 3;
    }
}
