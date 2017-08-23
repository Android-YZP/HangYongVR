package com.hy.vrfrog.main.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.base.BaseActivity;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.ChannelStatusBean;
import com.hy.vrfrog.main.adapter.MainAdapter;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.main.living.push.PushActivity;
import com.hy.vrfrog.main.living.push.PushSettingActivity;
import com.hy.vrfrog.main.personal.AuthenticationActivity;
import com.hy.vrfrog.main.personal.LoginActivity;
import com.hy.vrfrog.main.personal.ReleaseLiveActivity;
import com.hy.vrfrog.ui.BottomBar;
import com.hy.vrfrog.utils.BasePreferences;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.utils.UserInfoUtil;
import com.hy.vrfrog.utils.updateapk.CheckUpdate;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;

import static com.hy.vrfrog.application.VrApplication.getContext;


public class Main2Activity extends BaseActivity {
    private ViewPager mVpMain;
    private BottomBar mBottomBar;
    private ImageButton mIvLivingPush;
    private RelativeLayout linearLayout;
    private ChannelStatusBean channelStatusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        startActivity(new Intent(this, SplashActivity.class));
        initView();
        initData();

        if (SPUtil.getUser() != null) {
            String phone = (String) SPUtil.get(Main2Activity.this, "phone", "");
            String password = (String) SPUtil.get(Main2Activity.this, "password", "");
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                UserInfoUtil.getInstance().HttpLogin(this, phone, password);
            }
        }

        //检查版本更新
        CheckUpdate.getInstance().startCheck(Main2Activity.this, true);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAuditStatus();
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
                BasePreferences basePreferences = new BasePreferences(Main2Activity.this);
                if(basePreferences.getPrefBoolean("certificate")){
                    if (SPUtil.getUser() != null){

                    startActivity(new Intent(Main2Activity.this,ReleaseLiveActivity.class));
                    }else {
                    startActivity(new Intent(Main2Activity.this, LoginActivity.class));
                }
                }else {
                    Intent intent = new Intent(Main2Activity.this, AuthenticationActivity.class);
                    startActivity(intent);
                }



//                if (SPUtil.getUser() != null){
//                    if (channelStatusBean != null){
//                        if (channelStatusBean.getCode() == 0){
//                            startActivity(new Intent(Main2Activity.this,ReleaseLiveActivity.class));
//                        }else {
//                            Intent intent = new Intent(Main2Activity.this, AuthenticationActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//                }else {
//                    startActivity(new Intent(Main2Activity.this,LoginActivity.class));
//                }

            }
        });
    }

    private void initAuditStatus() {

        if (SPUtil.getUser() != null) {
            RequestParams requestParams = new RequestParams(HttpURL.ChannelStatus);
            requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid", SPUtil.getUser().getResult().getUser().getUid() + "");

            LogUtil.i("审核状态token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("审核状态uid = " + SPUtil.getUser().getResult().getUser().getUid());

            //获取数据
            x.http().get(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {
                    channelStatusBean = new Gson().fromJson(result, ChannelStatusBean.class);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    UIUtils.showTip("服务端连接失败");
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }
}
