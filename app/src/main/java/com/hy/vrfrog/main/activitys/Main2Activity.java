package com.hy.vrfrog.main.activitys;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hy.vrfrog.R;
import com.hy.vrfrog.base.BaseActivity;
import com.hy.vrfrog.main.adapter.MainAdapter;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.push.PushActivity;
import com.hy.vrfrog.ui.BottomBar;



public class Main2Activity extends BaseActivity {
    private ViewPager mVpMain;
    private BottomBar mBottomBar;
    private ImageButton mIvLivingPush;

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

    }

    private void initData() {
        mBottomBar.init(mVpMain);
        mIvLivingPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, PushActivity.class);
                String PushUrl = "rtmp://9250.livepush.myqcloud.com/live/9250_erte?bizid=9250&txSecret=1f728c3719bc7a51cd38de46bcbff49c&txTime=598B317F";
                intent.putExtra(TCConstants.PUBLISH_URL,PushUrl);
                startActivity(intent);
            }
        });
    }

}
