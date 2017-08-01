package com.jt.base.videos.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.activitys.SearchActivity;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.TopicBean;
import com.jt.base.http.responsebean.VideoTypeBean;
import com.jt.base.ui.MainRecycleView;
import com.jt.base.ui.VerticalSwipeRefreshLayout;
import com.jt.base.utils.LongLogUtil;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videos.adapters.DrawerAdapter;
import com.jt.base.videos.adapters.MainAdapter;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    private static final int HTTP_SUCCESS = 0;
    private LinearLayoutManager mLayoutManager;
    private MainRecycleView mRecycler;
    private MainAdapter mMainAdapter;
    private VerticalSwipeRefreshLayout mRecyclerfreshLayout;
    private DrawerLayout mDlLayout;
    private ListView mLvDrawerItem;
    private HashMap<Integer, Boolean> mItemPress = new HashMap<>();
    private DrawerAdapter mDrawerAdapter;
    private int mPressPostion = 0;
    private ImageButton mIbMenu;
    private TextView mMainTitle;
    private ViewPager mViewpager;
    private List<VideoTypeBean.ResultBean> mDatas;
    private TopicBean mTopicBean;
    private int mPager = 1;
    private int mTopicId;
    private List<TopicBean.ResultBeanX> results = new ArrayList<>();
    private LinearLayout mLlNoNetBg;
    private boolean islodingMore = false;
    private LinearLayout mSearchLl;

    public MainFragment() {
    }

    public MainFragment(ListView mLvDrawerItem, DrawerLayout mDlLayout, ViewPager mViewpager) {
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecycler = (MainRecycleView) view.findViewById(R.id.re_main_recycler);
        mRecyclerfreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.srl_main_swipe_refresh);
        mIbMenu = (ImageButton) view.findViewById(R.id.ib_menu);
        mMainTitle = (TextView) view.findViewById(R.id.tv_main_title);
        mLlNoNetBg = (LinearLayout) view.findViewById(R.id.iv_main_fragment_bg);
        mSearchLl = (LinearLayout)view.findViewById(R.id.ll_search_topic);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
        initListener();
        initDatas();
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
                    HttpGetVideoTopic(mTopicId + "", HttpURL.SourceNum,islodingMore);
                    mRecyclerfreshLayout.setRefreshing(false);
                }
            }
        });

        //侧边栏点击事件
        if (mLvDrawerItem != null)
            mLvDrawerItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!NetUtil.isOpenNetwork()) {
                        UIUtils.showTip("请打开网络");
                        return;
                    }
                    if (position != mPressPostion) mItemPress.put(mPressPostion, true);
                    mPressPostion = position;
                    //按下状态
                    mItemPress.put(position, false);
                    mDrawerAdapter.notifyDataSetChanged();
                    TextView tvTitle = (TextView) view.findViewById(R.id.tv_drawer_item_title);
                    mMainTitle.setText(tvTitle.getText());
                    mTopicId = mDatas.get(position).getId();
                    results.clear();
                    mPager = 1;
                    islodingMore = false;
                    HttpGetVideoTopic(mTopicId + "", HttpURL.SourceNum, islodingMore);
                    mDlLayout.closeDrawer(GravityCompat.START, true);
                }
            });

        mIbMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDlLayout.openDrawer(GravityCompat.START, true);
            }
        });

        mSearchLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
            }
        });
    }

    private void initRecycleView() {
        mLayoutManager = new LinearLayoutManager(getActivity()) ;
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
     * 初始化数据
     */
    private void initDatas() {
        mMainAdapter = new MainAdapter(getActivity(), mRecyclerfreshLayout, mRecycler, mViewpager, results);
        mRecycler.setAdapter(mMainAdapter);
        HttpVideoType();
    }

    /**
     * 请求侧边栏列表
     */
    private void HttpVideoType() {
        LogUtil.e("请求侧边栏列表");
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            mLlNoNetBg.setVisibility(View.VISIBLE);
            mRecyclerfreshLayout.setRefreshing(false);
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
                if (videoTypeBean.getCode() == HTTP_SUCCESS) {//获取数据成功
                    mLlNoNetBg.setVisibility(View.GONE);
                    mDatas = videoTypeBean.getResult();
                    //初始化HashMap数据
                    mItemPress.clear();
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (i == 0) {
                            mItemPress.put(i, false);
                        } else {
                            mItemPress.put(i, true);
                        }
                    }
                    mDrawerAdapter = new DrawerAdapter(mDatas, getContext(), mItemPress);
                    mLvDrawerItem.setAdapter(mDrawerAdapter);
                    mTopicId = mDatas.get(0).getId();
                    islodingMore = false;
                    mMainTitle.setText(mDatas.get(0).getName());
                    HttpGetVideoTopic(mTopicId + "", HttpURL.SourceNum, islodingMore);
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
                        mMainAdapter = new MainAdapter(getActivity(), mRecyclerfreshLayout, mRecycler, mViewpager, results);
                        if (mTopicBean.getPage().getTotal() <= 7){
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
