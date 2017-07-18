package com.jt.base.personal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jt.base.HomeActivity;
import com.jt.base.R;
import com.jt.base.videos.adapters.HistoryListAdapter;
import com.jt.base.videos.adapters.SearchAdapter;
import com.jt.base.videos.ui.SwipeBackActivity;

/**
 * Created by wzq930102 on 2017/7/11.
 */
public class HistoryActivity extends SwipeBackActivity {
     private HistoryListAdapter adapter;
    private TextView mTvReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
        RecyclerView recycler = (RecyclerView)findViewById(R.id.history_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recycler.setLayoutManager(mLayoutManager);
        adapter = new HistoryListAdapter(HistoryActivity.this);
        recycler.setAdapter(adapter);
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
    }
}
