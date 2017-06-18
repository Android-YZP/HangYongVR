package com.jt.base.videos;

import android.content.Intent;
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
import com.jt.base.personal.ForgetActivity;
import com.jt.base.personal.LoginActivity;
import com.jt.base.videoDetails.MainActivity;
import com.jt.base.vrplayer.VpMainAdapter;


public class VideosFragment extends Fragment {

    private ViewPager mVpMain;
    private BottomNavigationBar mBottomNavigationBar;

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
        mBottomNavigationBar = (BottomNavigationBar) view.findViewById(R.id.main_bottom_navigation_bar);
        mVpMain.setAdapter(new VpMainAdapter(getActivity().getSupportFragmentManager()));
    }


    private void setNavigationBar() {

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.search_press, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(getContext(), R.mipmap.search)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.home_press, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(getContext(), R.mipmap.home)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.personal_press, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(getContext(), R.mipmap.personal)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mVpMain.setCurrentItem(position, false);
                if (position == 3) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else if (position == 4) {
                    startActivity(new Intent(getActivity(), ForgetActivity.class));
                } else if (position == 5) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

}
