package com.jt.base.videos.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import com.jt.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzq930102 on 2017/7/12.
 */

public class VideoListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_list_activity);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.rl_vedio_details);
        List<String> list = new ArrayList<>();
        for (int i = 0;i<10;i++){
            list.add("1");
        }

    }
}

