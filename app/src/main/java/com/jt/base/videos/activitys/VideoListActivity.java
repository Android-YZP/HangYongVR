package com.jt.base.videos.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jt.base.R;
import com.jt.base.videos.adapters.DetailVideoAdapter;
import com.jt.base.videos.ui.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzq930102 on 2017/7/12.
 */

public class VideoListActivity extends SwipeBackActivity {
    private ImageView mImback;


//    private DetailVideoAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_list_activity);
        initView();
//        RecyclerView recycler = (RecyclerView) findViewById(R.id.rl_vedio_details);
//        List<String> list = new ArrayList<>();
//        for (int i = 0;i<10;i++){
//            list.add("1");
//        }
//        adapter = new DetailVideoAdapter(this, list);
//        recycler.setAdapter(adapter);
    }

    private void initView() {
        mImback = ((ImageView) findViewById(R.id.im_video_list_return));
        mImback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

