package com.hy.vrfrog.main.home.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.TopicBean;
import com.hy.vrfrog.http.responsebean.VideoTypeBean;
import com.hy.vrfrog.main.home.activitys.SearchActivity;
import com.hy.vrfrog.main.home.adapters.MainAdapter;
import com.hy.vrfrog.ui.MainRecycleView;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment {

    private static final int HTTP_SUCCESS = 0;
    private LinearLayoutManager mLayoutManager;
    private MainRecycleView mRecycler;
    private MainAdapter mMainAdapter;
    private VerticalSwipeRefreshLayout mRecyclerfreshLayout;
    private ImageButton mIbMenu;
    private TextView mMainTitle;
    private List<VideoTypeBean.ResultBean> mDatas;
    private TopicBean mTopicBean;
    private int mPager = 1;
    private int mTopicId;
    private List<TopicBean.ResultBeanX> results = new ArrayList<>();
    private LinearLayout mLlNoNetBg;
    private boolean islodingMore = false;

    public VideoFragment() {
    }

    public VideoFragment(int mTopicId) {
        this.mTopicId = mTopicId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
        initListener();
        initDatas();
    }

    private void initView(View view) {
        mRecycler = (MainRecycleView) view.findViewById(R.id.re_main_recycler);
        mRecyclerfreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.srl_main_swipe_refresh);
        mMainTitle = (TextView) view.findViewById(R.id.tv_main_title);
        mLlNoNetBg = (LinearLayout) view.findViewById(R.id.iv_main_fragment_bg);

    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        mMainAdapter = new MainAdapter(getActivity(), mRecyclerfreshLayout, mRecycler, new ViewPager(getActivity()), results);
        HttpGetVideoTopic(mTopicId + "", HttpURL.SourceNum, islodingMore);
    }


    private void initListener() {
        mRecyclerfreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mLlNoNetBg.getVisibility() == View.VISIBLE) {//没有数据
                    initDatas();
                } else if (mLlNoNetBg.getVisibility() == View.GONE) {//有数据的情况下刷新
                    mPager = 1;
                    islodingMore = false;
                    HttpGetVideoTopic(mTopicId + "", HttpURL.SourceNum, islodingMore);
                    mRecyclerfreshLayout.setRefreshing(false);
                }
            }
        });


    }


    private void initRecycleView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        setHeaderView(mRecycler);
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean visBottom = UIUtils.isVisBottom(mRecycler);
                if (visBottom) {
                    if (mMainAdapter.getFooterView() == null) {
                        ++mPager;
                        islodingMore = true;
                        HttpGetVideoTopic(mTopicId + "", HttpURL.SourceNum, islodingMore);
                    } else {
                        return;
                    }

                    if (mTopicBean.getPage().getTotalPage() <= mPager) {
                        View v = View.inflate(getContext(), R.layout.main_list_no_datas, null);//main_list_item_foot_view
                        mMainAdapter.setFooterView(v);
                        mMainAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 请求话题列表
     */
    private void HttpGetVideoTopic(String typeid, String sourceNum, final boolean islodingMore) {
        mRecyclerfreshLayout.setRefreshing(true);
        LogUtil.e("请求话题列表");
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            mLlNoNetBg.setVisibility(View.VISIBLE);
            mRecyclerfreshLayout.setRefreshing(false);
            results.clear();
            mMainAdapter.notifyDataSetChanged();
            if (mMainAdapter.getFooterView() != null)
                mMainAdapter.getFooterView().setVisibility(View.GONE);
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.VidByType);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("typeId", typeid);
        requestParams.addBodyParameter("sourceNum", sourceNum);
        requestParams.addBodyParameter("page", mPager + "");//
        requestParams.addBodyParameter("count", 7 + "");//
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("-----------", result);
                mTopicBean = new Gson().fromJson(result, TopicBean.class);

                if (mTopicBean.getCode() == 0) {//数据获取成功/code话题ID，msg话题名称
                    mLlNoNetBg.setVisibility(View.GONE);
                    if (islodingMore) {
                        results.addAll(mTopicBean.getResult());
                        mMainAdapter.notifyDataSetChanged();
                    } else {
                        results = mTopicBean.getResult();
                        mMainAdapter = new MainAdapter(getActivity(), mRecyclerfreshLayout, mRecycler, new ViewPager(getActivity()), results);
                        if (mTopicBean.getPage().getTotal() <= 7) {
                            View v = View.inflate(getContext(), R.layout.main_list_no_datas, null);//main_list_item_foot_view
                            mMainAdapter.setFooterView(v);
                        }
                        mRecycler.setAdapter(mMainAdapter);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mRecyclerfreshLayout.setRefreshing(false);
            }
        });
    }


    /**
     * 设置头布局
     */
    private void setHeaderView(RecyclerView view) {
//        View header = LayoutInflater.from(getActivity()).inflate(R.layout.my_head_view, view, false);
//        mHeadPicRecycler = (LoopRecyclerViewPager) header.findViewById(R.id.lrvp_viewpager);
//        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        mHeadPicRecycler.setLayoutManager(layout);
//        mHeadPicRecycler.setAdapter(new MainPicListAdapter(getActivity()));
//        mHeadPicRecycler.setHasFixedSize(true);
//        mHeadPicRecycler.setLongClickable(true);
//
//        mHeadPicRecycler.setTriggerOffset(0.15f);
//        mHeadPicRecycler.setFlingFactor(0.25f);
//        mHeadPicRecycler.addItemDecoration(new SpacesItemDecoration(0, mHeadPicRecycler.getAdapter().getItemCount()));
//        //修复滑动事件的冲突
//        mHeadPicRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                //
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {//滑动停止后
//                    mRecyclerfreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
//                } else {
//                    mRecyclerfreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
//                }
//            }
//        });
//        //设置动画效果
//        mHeadPicRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                //                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
//                int childCount = mHeadPicRecycler.getChildCount();
//                int width = mHeadPicRecycler.getChildAt(0).getWidth();
//                int padding = (mHeadPicRecycler.getWidth() - width) / 2;
//
//                for (int j = 0; j < childCount; j++) {
//                    View v = recyclerView.getChildAt(j);
//                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
//                    float rate = 0;
//                    if (v.getLeft() <= padding) {
//                        if (v.getLeft() >= padding - v.getWidth()) {
//                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
//                        } else {
//                            rate = 1;
//                        }
//                        v.setScaleY(1 - rate * 0.1f);
//                    } else {
//                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
//                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
//                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
//                        }
//                        v.setScaleY(0.9f + rate * 0.1f);
//                    }
//                }
//            }
//        });
//
//        mHeadPicRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (mHeadPicRecycler.getChildCount() < 3) {
//                    if (mHeadPicRecycler.getChildAt(1) != null) {
//                        View v1 = mHeadPicRecycler.getChildAt(1);
//                        v1.setScaleY(0.9f);
//                    }
//                } else {
//                    if (mHeadPicRecycler.getChildAt(0) != null) {
//                        View v0 = mHeadPicRecycler.getChildAt(0);
//                        v0.setScaleY(0.9f);
//                    }
//                    if (mHeadPicRecycler.getChildAt(2) != null) {
//                        View v2 = mHeadPicRecycler.getChildAt(2);
//                        v2.setScaleY(0.9f);
//                    }
//                }
//
//            }
//        });

//        TextView EmptyView = new TextView(getActivity());//修复下拉刷新不能出来的BUG
//        EmptyView.setVisibility(View.GONE);
//        mMainAdapter.setHeaderView(EmptyView);//影藏轮播图
//        mMainAdapter.setFooterView(EmptyView);

    }
}
