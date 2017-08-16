package com.hy.vrfrog.main.activitys;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hy.vrfrog.R;
import com.hy.vrfrog.base.BaseActivity;
import com.hy.vrfrog.main.adapter.MainAdapter;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.main.living.push.PushActivity;
import com.hy.vrfrog.main.living.push.PushSettingActivity;
import com.hy.vrfrog.main.personal.AuthenticationActivity;
import com.hy.vrfrog.ui.BottomBar;

import org.xutils.common.util.LogUtil;

import java.lang.reflect.Field;

import static com.hy.vrfrog.application.VrApplication.getContext;


public class Main2Activity extends BaseActivity {
    private ViewPager mVpMain;
    private BottomBar mBottomBar;
    private ImageButton mIvLivingPush;
    private RelativeLayout linearLayout;

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
        mIvLivingPush = (ImageButton) findViewById(R.id.ib_living_push);
        mVpMain.setAdapter(new MainAdapter(getSupportFragmentManager()));
        mVpMain.setCurrentItem(1);
        linearLayout = (RelativeLayout) findViewById(R.id.ll_height);

    }


    private void initData() {
        mBottomBar.init(mVpMain);
        mIvLivingPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, AuthenticationActivity.class);
                startActivity(intent);
            }
        });
    }



}
