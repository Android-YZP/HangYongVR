package com.jt.base.personal;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jt.base.R;
import com.jt.base.ui.SwipeBackActivity;
import com.jt.base.videos.adapters.MyPayAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by wzq930102 on 2017/7/31.
 */
public class HistoryPayActivity extends SwipeBackActivity {
    private ImageView mBack;
    private MyPayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pay);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.my_pay_list);
        List<String> list = new ArrayList<>();
//        list.add();
//        adapter = new MyPayAdapter(this,list);
        recycler.setAdapter(adapter);
        initView();
        initListener();
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mBack = ((ImageView) findViewById(R.id.tv_history_pay_return));
    }

}
