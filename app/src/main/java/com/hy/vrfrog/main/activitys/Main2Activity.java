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
import com.hy.vrfrog.main.living.push.PushActivity;
import com.hy.vrfrog.ui.BottomBar;

import org.xutils.common.util.LogUtil;

import java.lang.reflect.Field;

import static com.hy.vrfrog.application.VrApplication.getContext;


public class Main2Activity extends BaseActivity {
    private ViewPager mVpMain;
    private BottomBar mBottomBar;
    private ImageButton mIvLivingPush;
    private RelativeLayout linearLayout ;

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
        linearLayout = (RelativeLayout)findViewById(R.id.ll_height);
        int height = getStatusBarHeight();
        LogUtil.i("height = " + height);

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

    private int getStatusBarHeight() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = getContext().getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }

}
