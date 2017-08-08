package com.hy.vrfrog.main.personal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.HistoryPayBean;
import com.hy.vrfrog.ui.SwipeBackActivity;
import com.hy.vrfrog.videos.adapters.HistoryPayAdapter;

import java.util.ArrayList;

/**
 * Created by wzq930102 on 2017/7/31.
 */
public class HistoryPayActivity extends SwipeBackActivity {
    private ImageView mBack;
    private HistoryPayAdapter adapter;
    private RecyclerView mRecycler;
    private ArrayList<HistoryPayBean>mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pay);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i ++){
            HistoryPayBean bean = new HistoryPayBean();
            bean.setDemand("demand");
            bean.setObject("object");
            bean.setPrice("price");
            bean.setTime("time");
            bean.setTitle("title");
            mList.add(bean);
        }
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });
    }

    private void initView() {
        mBack = ((ImageView) findViewById(R.id.tv_history_pay_return));
        mRecycler = (RecyclerView) findViewById(R.id.my_pay_list);
        mRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new HistoryPayAdapter(this,mList);
        mRecycler.setAdapter(adapter);
    }

}
