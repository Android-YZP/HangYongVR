package com.jt.base;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jt.base.vrplayer.VpMainAdapter;

public class ItemActivity extends AppCompatActivity {

    private BottomNavigationBar mBottomNavigationBar;
    private ViewPager mVpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initViewPager();
        setNavigationBar();
    }


    /**
     *
     */
    private void initViewPager() {
        mVpMain = (ViewPager) findViewById(R.id.vp_main);
        mVpMain.setAdapter(new VpMainAdapter(getSupportFragmentManager()));
    }


    private void setNavigationBar() {
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_bottom_navigation_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        mBottomNavigationBar.setBarBackgroundColor(R.color.white);//set background color for navigation bar
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mVpMain.setCurrentItem(position, false);
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
