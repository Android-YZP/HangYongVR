package com.jt.base.personal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.http.responsebean.SeeHistory;
import com.jt.base.utils.LocalUtils;
import com.jt.base.videos.adapters.HistoryListAdapter;

import java.util.List;

/**
 * Created by wzq930102 on 2017/7/11.
 */
public class HistoryActivity extends AppCompatActivity {
    private HistoryListAdapter adapter;
    private TextView mTvReturn;
    private RelativeLayout mReDialog;
    private List<SeeHistory> seeHistory;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
        mRecycler = (RecyclerView) findViewById(R.id.history_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRecycler.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        seeHistory = LocalUtils.getSeeHistory(this);
        adapter = new HistoryListAdapter(HistoryActivity.this, seeHistory);
        mRecycler.setAdapter(adapter);
    }




    private void initListener() {
        mTvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTvReturn = ((TextView) findViewById(R.id.tv_history_return));
//        mReDialog = ((RelativeLayout) findViewById(R.id.rl_history_list));
    }
}
