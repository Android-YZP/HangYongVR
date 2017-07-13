package com.jt.base.personal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.videos.ui.SwipeBackActivity;

/**
 * Created by wzq930102 on 2017/7/11.
 */
public class HistoryActivity extends SwipeBackActivity {
    private TextView mTvHiReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
    }

    private void initView() {
        mTvHiReturn = (TextView)findViewById(R.id.tv_history_return);
    }
    public void initListener() {
        mTvHiReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.base_slide_right_in,R.anim.base_slide_right_out);
            }
        });
    }
}
