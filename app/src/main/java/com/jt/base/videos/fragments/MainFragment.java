package com.jt.base.videos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.RegisterBean;
import com.jt.base.http.responsebean.VideoTypeBean;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videos.adapters.DrawerAdapter;
import com.jt.base.videos.adapters.MainAdapter;
import com.jt.base.videos.adapters.MainPicListAdapter;
import com.jt.base.videos.adapters.SpacesItemDecoration;
import com.lsjwzh.widget.recyclerviewpager.LoopRecyclerViewPager;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


//rtmp://9250.liveplay.myqcloud.com/live/9250_0601HK
public class MainFragment extends Fragment {

    private static final int HTTP_SUCCESS = 0;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecycler;
    private MainAdapter mMainAdapter;
    private SwipyRefreshLayout mRecyclerfreshLayout;
    private LoopRecyclerViewPager mHeadPicRecycler;
    private DrawerLayout mDlLayout;
    private ListView mLvDrawerItem;

    public MainFragment() {
    }

    public MainFragment(ListView mLvDrawerItem, DrawerLayout mDlLayout) {
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.re_main_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRecycler.setLayoutManager(mLayoutManager);
        mRecyclerfreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.srl_main_swipe_refresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
        initListener();
        initDrawer();
    }

    private void initListener() {
        mRecyclerfreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                LogUtil.i(direction + "");
                mRecyclerfreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRecycleView() {
        mMainAdapter = new MainAdapter(getActivity(), mRecyclerfreshLayout, mRecycler);
        mRecycler.setAdapter(mMainAdapter);
        setHeaderView(mRecycler);
    }

    /**
     * 初始化侧边栏
     */
    private void initDrawer() {
        HttpVideoType();
    }





    /**
     * 请求侧边栏列表
     */
    private void HttpVideoType() {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.VideoType);
        requestParams.addHeader("token", HttpURL.Token);
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                VideoTypeBean videoTypeBean = new Gson().fromJson(result, VideoTypeBean.class);
                if (videoTypeBean.getCode()==HTTP_SUCCESS){//获取数据成功
                    List<VideoTypeBean.ResultBean> mDatas = videoTypeBean.getResult();
                    mLvDrawerItem.setAdapter(new DrawerAdapter(mDatas,getContext()));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
        });
    }




    /**
     * 设置头布局
     */
    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.my_head_view, view, false);
        mHeadPicRecycler = (LoopRecyclerViewPager) header.findViewById(R.id.lrvp_viewpager);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHeadPicRecycler.setLayoutManager(layout);
        mHeadPicRecycler.setAdapter(new MainPicListAdapter(getActivity()));
        mHeadPicRecycler.setHasFixedSize(true);
        mHeadPicRecycler.setLongClickable(true);

        mHeadPicRecycler.setTriggerOffset(0.15f);
        mHeadPicRecycler.setFlingFactor(0.25f);
        mHeadPicRecycler.addItemDecoration(new SpacesItemDecoration(0, mHeadPicRecycler.getAdapter().getItemCount()));
        //修复滑动事件的冲突
        mHeadPicRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {//滑动停止后
                    mRecyclerfreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
                } else {
                    mRecyclerfreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
                }
            }
        });
        //设置动画效果
        mHeadPicRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                //                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = mHeadPicRecycler.getChildCount();
                int width = mHeadPicRecycler.getChildAt(0).getWidth();
                int padding = (mHeadPicRecycler.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mHeadPicRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mHeadPicRecycler.getChildCount() < 3) {
                    if (mHeadPicRecycler.getChildAt(1) != null) {
                        View v1 = mHeadPicRecycler.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mHeadPicRecycler.getChildAt(0) != null) {
                        View v0 = mHeadPicRecycler.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mHeadPicRecycler.getChildAt(2) != null) {
                        View v2 = mHeadPicRecycler.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });

        TextView EmptyView = new TextView(getActivity());//修复下拉刷新不能出来的BUG
        mMainAdapter.setHeaderView(EmptyView);//影藏轮播图
        mMainAdapter.setFooterView(EmptyView);

    }
}
