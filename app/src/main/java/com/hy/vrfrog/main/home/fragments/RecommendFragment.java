package com.hy.vrfrog.main.home.fragments;

import com.hy.vrfrog.R;
import com.hy.vrfrog.application.VrApplication;
import com.hy.vrfrog.utils.LoadMoreListView;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.UIUtils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.main.home.activitys.VideoListActivity;
import com.hy.vrfrog.main.home.adapters.RecommendAdapterList;
import com.hy.vrfrog.http.responsebean.RecommendBean;
import com.hy.vrfrog.main.home.adapters.VideoDetialAdapter;
import com.hy.vrfrog.ui.CircleIndicator;
import com.hy.vrfrog.ui.LoopViewPager;
import com.hy.vrfrog.ui.RoundImageView;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.List;


/**
 * Created by qwe on 2017/8/8.
 */

public class RecommendFragment extends Fragment {

    private LinearLayout mEmptyViewLl;
    private LoadMoreListView mRecycler;
    private VerticalSwipeRefreshLayout mRecyclerfreshLayout;
    private LoopViewPager mLoopViewPager;
    private RecommendAdapterList mAdapter;
    private List<RecommendBean.ResultBeanX> mList;
    private int page = 1;

    int[] resIds = new int[]{R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4, R.mipmap.img5, R.mipmap.img6};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend,container,false);
        initView(view);
        HttpTopic(page);
        initListener();
        return view;
    }

    private void initListener() {
        mRecyclerfreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mList != null){
                    mList.clear();
                }
                HttpTopic(1);
            }
        });

        mRecycler.setLoadMoreListener(new LoadMoreListView.OnLoadMore() {
            @Override
            public void loadMore() {

                page ++;
                HttpTopic(page);
            }
        });

    }

    private void initView(View view) {

        mEmptyViewLl = (LinearLayout) view.findViewById(R.id.iv_recommend_fragment_bg);
        mRecycler = (LoadMoreListView)view.findViewById(R.id.re_recommend_recycler);

        mRecyclerfreshLayout = (VerticalSwipeRefreshLayout)view.findViewById(R.id.srl_recommend_swipe_refresh);
        View headView = View.inflate(getActivity(),R.layout.item_head, null);
//        View footView = View.inflate(getActivity(),R.layout.main_list_no_datas, null);

        mLoopViewPager = (LoopViewPager)headView.findViewById(R.id.looviewpager);
        CircleIndicator indicator = (CircleIndicator) headView.findViewById(R.id.indicator);

        mRecycler.addHeaderView(headView);
//        mRecycler.addFooterView(footView);
        mLoopViewPager.setAdapter(new MyAdapter());
        mLoopViewPager.setOffscreenPageLimit(3);
        mLoopViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            float scale = 0.9f;

            @Override
            public void transformPage(View page, float position) {
                page.setScaleY(scale);
            }
        });
        mLoopViewPager.setLooperPic(true);
        indicator.setViewPager( mLoopViewPager);

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return resIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = View.inflate(VrApplication.getContext(), R.layout.item_viewpager, null);
            RoundImageView itemImage = (RoundImageView) view.findViewById(R.id.item_image);
            itemImage.setImageResource(resIds[position]);
            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 获取话题
     */
    private void HttpTopic( int pager) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            mEmptyViewLl.setVisibility(View.VISIBLE);
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.VodByCommendTopic);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("sourceNum", "222");//
        requestParams.addBodyParameter("page", pager + "");//
        requestParams.addBodyParameter("count", 10+"");//
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {

                RecommendBean recommendBean = new Gson().fromJson(result, RecommendBean.class);
                LongLogUtil.e("---------------", result);
                if (recommendBean.getCode() == 0) {
                    mEmptyViewLl.setVisibility(View.GONE);
                    if (mList != null && mList.size() > 0) {//下拉加载
                        mList.addAll(recommendBean.getResult());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        LogUtil.e("推荐 =" + recommendBean.getResult());
                        mList = recommendBean.getResult();
                        mAdapter = new RecommendAdapterList(getActivity(),mList);
                        mRecycler.setAdapter(mAdapter);

                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
                mRecyclerfreshLayout.setRefreshing(false);
            }

            @Override
            public void onFinished() {
                mRecyclerfreshLayout.setRefreshing(false);
            }
        });
    }




}
