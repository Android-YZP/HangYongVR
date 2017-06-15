package com.jt.base.videos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jt.base.R;
import com.jt.base.login.ForgetActivity;
import com.jt.base.login.LoginActivity;
import com.jt.base.login.PersonalActivity;
import com.jt.base.login.RegisterActivity;
import com.jt.base.videoDetails.MainActivity;
import com.jt.base.vrplayer.VpMainAdapter;

public class VideoActivity extends AppCompatActivity {

    private BottomNavigationBar mBottomNavigationBar;
    private ViewPager mVpMain;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_item);
        initView();
        initViewPager();
        setNavigationBar();
    }

    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.setScrimColor(Color.TRANSPARENT);
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
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.search_press, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.search)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.home_press, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.home)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.personal_press, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.personal)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.clip_title).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)).setActiveColorResource(R.color.circle_select_color).setInActiveColorResource(R.color.circle_unselect_color))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mVpMain.setCurrentItem(position, false);
                if (position == 3) {
                    startActivity(new Intent(VideoActivity.this, LoginActivity.class));
                } else if (position == 4) {
                    startActivity(new Intent(VideoActivity.this, ForgetActivity.class));
                } else if (position == 5) {
                    startActivity(new Intent(VideoActivity.this, PersonalActivity.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_detial_in, R.anim.anim_detial_out);
    }
}
