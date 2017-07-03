package com.jt.base.activitys;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.fragments.VideoDetailFragment;
import com.jt.base.videos.VideosFragment;

public class MainActivity extends AppCompatActivity {

    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private ViewPager mViewpager;
    private ImageView mIvTwoDBg;
    private DrawerLayout mDlLayout;
    private ListView mLvDrawerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPanorama();
        initViewPager();
        initListenter();
    }

    private void initView() {
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view_main);
        mIvTwoDBg = (ImageView) findViewById(R.id.iv_two_bg);
        mDlLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        mLvDrawerItem = (ListView) findViewById(R.id.left_drawer_item);
    }

    private void initListenter() {
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    panoWidgetView.setVisibility(View.VISIBLE);//显示全景图
                    mIvTwoDBg.setVisibility(View.VISIBLE);//显示全景图
                } else {
                    panoWidgetView.setVisibility(View.GONE);
                    mIvTwoDBg.setVisibility(View.GONE);
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


    /**
     * 初始化全景图播放器
     */
    private void initPanorama() {
        loadImageSuccessful = false;//初始化图片状态
        panoWidgetView.setEventListener(new ActivityEventListener());
        //影藏三個界面的按鈕
        panoWidgetView.setFullscreenButtonEnabled(false);
        panoWidgetView.setInfoButtonEnabled(false);
        panoWidgetView.setStereoModeButtonEnabled(false);
        panoWidgetView.setOnTouchListener(null);//禁用手势滑动
        panoOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
    }


    //
    private void initViewPager() {
        mViewpager = (ViewPager) findViewById(R.id.vp_viewpager);
        mViewpager.setAdapter(new pagerAdapter(getSupportFragmentManager(), panoWidgetView, mIvTwoDBg));
    }


    @Override
    protected void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        panoWidgetView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        panoWidgetView.shutdown();
        super.onDestroy();
    }


    /**
     * Listen to the important events from widget.
     */
    private class ActivityEventListener extends VrPanoramaEventListener {
        /**
         * Called by pano widget on the UI thread when it's done loading the image.
         */
        @Override
        public void onLoadSuccess() {
            loadImageSuccessful = true;
            Log.e("dflefseofjsdopfj", "Could not decode default bitmap: 2");
            panoWidgetView.setVisibility(View.VISIBLE);
        }

        /**
         * Called by pano widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            Log.e("dflefseofjsdopfj", "Could not decode default bitmap: 3");
            loadImageSuccessful = false;
            UIUtils.showTip("图片加载错误");
        }
    }


    class pagerAdapter extends FragmentPagerAdapter {
        VrPanoramaView panoWidgetView;
        ImageView mIvTwoDBg;

        public pagerAdapter(FragmentManager fm, VrPanoramaView panoWidgetView, ImageView mIvTwoDBg) {
            super(fm);
            this.panoWidgetView = panoWidgetView;
            this.mIvTwoDBg = mIvTwoDBg;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            if (position == 0) {
                fragment = new VideosFragment(mDlLayout,mLvDrawerItem);
            } else if (position == 1) {
                fragment = new VideoDetailFragment(panoWidgetView, panoOptions,mIvTwoDBg);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
