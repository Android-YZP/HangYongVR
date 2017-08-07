package com.hy.vrfrog.main.home.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;
import com.hy.vrfrog.main.home.adapters.LiveHomeAdapter;

import java.util.ArrayList;

/**
 * Created by qwe on 2017/8/4.
 */
@SuppressLint("ValidFragment")
public class LiveHomeFragment extends Fragment {

    private LinearLayout mEmptyll;
    private VerticalSwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private LiveHomeAdapter mAdapter;
    private ArrayList<GetLiveHomeBean> mList  ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_live_home, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        mList = new ArrayList();
        for (int i = 0 ; i < 20 ; i ++){
            GetLiveHomeBean bean = new GetLiveHomeBean();
            bean.setMsg("message");
            bean.setName("name");
            bean.setNumber("23323");
            bean.setState("state");
            bean.setPlayState("直播");
            bean.setTitle("春天的故事");
            mList.add(bean);
        }
    }

    private void initView(View view) {

        mEmptyll = (LinearLayout)view.findViewById(R.id.ll_live_home_no_data);
        mSwipeRefresh = (VerticalSwipeRefreshLayout)view.findViewById(R.id.vsr_live_home__refresh);
        mSwipeRefresh.setColorScheme(android.R.color.black, android.R.color.holo_green_light, android.R.color.holo_blue_light, android.R.color.holo_red_light);
        mSwipeRefresh.setEnabled(false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_live_home_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mAdapter = new LiveHomeAdapter(getActivity(),mList);
        mRecyclerView.setAdapter(mAdapter);

    }
}
