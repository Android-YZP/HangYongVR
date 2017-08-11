package com.hy.vrfrog.main.adapter;


import android.support.v4.app.Fragment;

import com.hy.vrfrog.http.responsebean.VideoTypeBean;
import com.hy.vrfrog.main.home.fragments.EnterpriseLiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.PersonalLiveHomeFragment;
import com.hy.vrfrog.main.home.fragments.RecommendFragment;
import com.hy.vrfrog.main.home.fragments.VideoFragment;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 姚中平 on 2017/8/11.
 */

public class FragmentFactory {
    private static HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();
    private static Fragment fragment;

    public static Fragment createFragment(int position, List<VideoTypeBean.ResultBean> mTitle) {
        if (fragmentHashMap.get(position) != null) {
            return fragmentHashMap.get(position);
        } else {
            if (position == 0) {
                fragment = new RecommendFragment();
                fragmentHashMap.put(position, fragment);
                return fragment;
            } else if (position == 1) {
                fragment = new PersonalLiveHomeFragment();
                fragmentHashMap.put(position, fragment);
                return fragment;
            } else if (position == 2) {
                fragment = new EnterpriseLiveHomeFragment();
                fragmentHashMap.put(position, fragment);
                return fragment;
            } else {
                fragment = new VideoFragment(mTitle.get(position - 3).getId());
                fragmentHashMap.put(position, fragment);
                return fragment;
            }

        }
    }

}
