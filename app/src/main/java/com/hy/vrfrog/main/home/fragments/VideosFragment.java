package com.hy.vrfrog.main.home.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.ui.CustomViewPager;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.ui.BottomBar;
import com.hy.vrfrog.vrplayer.VpMainAdapter;

@SuppressLint("ValidFragment")
public class VideosFragment extends Fragment {

    private ViewPager mVpMain;
    private BottomBar mBottomBar;
    private DrawerLayout mDlLayout;
    private ListView mLvDrawerItem;
    private CustomViewPager mViewpager;

    public VideosFragment() {
    }

    public VideosFragment(DrawerLayout mDlLayout, ListView mLvDrawerItem, CustomViewPager mViewpager) {
        this.mDlLayout = mDlLayout;
        this.mLvDrawerItem = mLvDrawerItem;
        this.mViewpager = mViewpager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        initView(view);
        setNavigationBar();
        return view;
    }


    private void initView(View view) {
        mVpMain = (ViewPager) view.findViewById(R.id.vp_main);
        mBottomBar = (BottomBar) view.findViewById(R.id.main_bottom_bar);
        mVpMain.setAdapter(new VpMainAdapter(getActivity().getSupportFragmentManager(), mLvDrawerItem, mDlLayout, mViewpager));
        mVpMain.setCurrentItem(1, false);
    }


    private void setNavigationBar() {
        mBottomBar.init(mVpMain);//初始化
        if (mDlLayout != null)
            mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 1) {
                        mViewpager.setScanScroll(true);
                        mDlLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    } else {
                        mDlLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        mViewpager.setScanScroll(false);
                    }
                    UIUtils.hideKeyBoard(mVpMain);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


    }

}
