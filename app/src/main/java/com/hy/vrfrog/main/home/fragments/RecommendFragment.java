package com.hy.vrfrog.main.home.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.main.home.adapters.RecommendAdapterList;
import com.hy.vrfrog.main.home.bean.RecommendBean;
import com.hy.vrfrog.ui.LoopViewPager;

import com.hy.vrfrog.ui.RoundImageView;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;

import java.util.ArrayList;


/**
 * Created by qwe on 2017/8/8.
 */
@SuppressLint("ValidFragment")
public class RecommendFragment extends Fragment {

    private LinearLayout mEmptyViewLl;
    private ListView mRecycler;
    private VerticalSwipeRefreshLayout mRecyclerfreshLayout;
    private LoopViewPager mLoopViewPager;
    private RecommendAdapterList mAdapter;
    private ArrayList<RecommendBean>mList;

    int[] resIds = new int[]{R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4, R.mipmap.img5, R.mipmap.img6};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend,container,false);

        initData();
        initView(view);

        return view;
    }

    private void initView(View view) {

        mEmptyViewLl = (LinearLayout) view.findViewById(R.id.iv_recommend_fragment_bg);
        mRecycler = (ListView)view.findViewById(R.id.re_recommend_recycler);
        mAdapter = new RecommendAdapterList(getActivity(),mList);
        mRecyclerfreshLayout = (VerticalSwipeRefreshLayout)view.findViewById(R.id.srl_recommend_swipe_refresh);
        mRecyclerfreshLayout.setEnabled(false);
        View headView = View.inflate(getActivity(),R.layout.item_head, null);
        mLoopViewPager = (LoopViewPager)headView.findViewById(R.id.looviewpager);
        mRecycler.addHeaderView(headView);
        mLoopViewPager.setAdapter(new MyAdapter());
        mRecycler.setAdapter(mAdapter);
        mLoopViewPager.setOffscreenPageLimit(3);
        mLoopViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            float scale = 0.9f;

            @Override
            public void transformPage(View page, float position) {
                if (position >= 0 && position <= 1) {
                    page.setScaleY(scale + (1 - scale) * (1 - position));
                } else if (position > -1 && position < 0) {
                    page.setScaleY(1 + (1 - scale) * position);
                } else {
                    page.setScaleY(scale);
                }
            }
        });
        mLoopViewPager.autoLoop(true);

    }

    private void initData() {

        mList = new ArrayList<>();
        for(int i = 0 ; i < 30 ; i ++){
            RecommendBean bean = new RecommendBean();
            bean.setTitle(String.valueOf(i) );
            mList.add(bean);
        }

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
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getActivity(), R.layout.item_viewpager, null);
            RoundImageView itemImage = (RoundImageView) view.findViewById(R.id.item_image);
            itemImage.setImageResource(resIds[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
