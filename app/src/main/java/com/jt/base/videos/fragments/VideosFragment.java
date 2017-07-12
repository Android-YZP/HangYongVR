package com.jt.base.videos.fragments;

import android.annotation.SuppressLint;
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
import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.ResetPasswordBean;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videos.ui.BottomBar;
import com.jt.base.vrplayer.VpMainAdapter;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

@SuppressLint("ValidFragment")
public class VideosFragment extends Fragment {

    private ViewPager mVpMain;
    private BottomBar mBottomBar;
    private DrawerLayout mDlLayout;
    private ListView mLvDrawerItem;
    private ViewPager mViewpager;

    public VideosFragment() {
    }

    public VideosFragment(DrawerLayout mDlLayout, ListView mLvDrawerItem, ViewPager mViewpager) {
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
        initData("https://raw.githubusercontent.com/Android-YZP/youshi/dev/test.json");
        return view;
    }

    /**
     * 假数据
     */
    private void initData(String url) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(url);
        //包装请求参数
        //获取数据
        x.http().get(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }
        });

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
