package com.hy.vrfrog.videos.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;
import com.hy.vrfrog.videos.adapters.LiveHomeAdapter;

import java.util.ArrayList;

/**
 * Created by qwe on 2017/8/3.
 */

public class LiveHomeActivity extends AppCompatActivity {

    private LinearLayout mEmptyll;
    private VerticalSwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private LiveHomeAdapter mAdapter;
    private ArrayList<GetLiveHomeBean.ResultBean> mList  ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_home);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        mEmptyll = (LinearLayout)findViewById(R.id.ll_live_home_no_data);
        mSwipeRefresh = (VerticalSwipeRefreshLayout)findViewById(R.id.vsr_live_home__refresh);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_live_home_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(LiveHomeActivity.this,2));
        mList = new ArrayList();
        mAdapter = new LiveHomeAdapter(LiveHomeActivity.this,mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {

    }

    private void initListener() {

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

    }
}
