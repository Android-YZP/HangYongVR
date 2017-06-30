package com.jt.base.videos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jt.base.R;
import com.jt.base.videos.ui.BottomBar;
import com.jt.base.vrplayer.VpMainAdapter;


public class VideosFragment extends Fragment {

    private ViewPager mVpMain;
    private BottomBar mBottomBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        initView(view);
        setNavigationBar();
        return view;
    }

    private void initView(View view) {
        mVpMain = (ViewPager) view.findViewById(R.id.vp_main);
        mBottomBar = (BottomBar) view.findViewById(R.id.main_bottom_bar);
        mVpMain.setAdapter(new VpMainAdapter(getActivity().getSupportFragmentManager()));
        mVpMain.setCurrentItem(1,false);
    }


    private void setNavigationBar() {
        mBottomBar.init(mVpMain);//初始化
    }

}
