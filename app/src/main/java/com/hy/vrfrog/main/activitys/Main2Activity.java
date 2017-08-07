package com.hy.vrfrog.main.activitys;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.hy.vrfrog.R;
import com.hy.vrfrog.main.adapter.MainAdapter;
import com.hy.vrfrog.ui.BottomBar;


public class Main2Activity extends AppCompatActivity {
    private ViewPager mVpMain;
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
    }

    private void initView() {
        mVpMain = (ViewPager) findViewById(R.id.vp_main);
        mBottomBar = (BottomBar) findViewById(R.id.main_bottom_bar);
        mVpMain.setAdapter(new MainAdapter(getSupportFragmentManager()));
    }

    private void initData() {
        mBottomBar.init(mVpMain);
    }


}
