package com.jt.base.videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jt.base.R;
import com.jt.base.videos.ui.BottomBar;
import com.jt.base.vrplayer.VpMainAdapter;


public class VideosFragment extends Fragment {

    private ViewPager mVpMain;
    private BottomBar mBottomBar;
    private DrawerLayout mDlLayout;
    private ListView mLvDrawerItem;

    public VideosFragment() {
    }

    public VideosFragment(DrawerLayout mDlLayout, ListView mLvDrawerItem) {
        this.mDlLayout = mDlLayout;
        this.mLvDrawerItem = mLvDrawerItem;
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
        mVpMain.setAdapter(new VpMainAdapter(getActivity().getSupportFragmentManager(),mLvDrawerItem,mDlLayout));
        mVpMain.setCurrentItem(1, false);
    }


    private void setNavigationBar() {
        mBottomBar.init(mVpMain);//初始化
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    mDlLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else {
                    mDlLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
