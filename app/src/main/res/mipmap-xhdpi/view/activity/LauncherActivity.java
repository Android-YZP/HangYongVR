package com.mytv365.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动界面
 *
 * @author ZhangGuoHao
 * @date 2016年5月13日 下午3:58:06
 */
public class LauncherActivity extends BaseActivity {
    private ImageView img;
    private View view;
    /**
     * 跳过
     */
    private Button skip;
    /**
     * 添加动画效果
     */
    private AlphaAnimation animation;
    /**
     * 动画执行时间
     */
    private int animationTime = 2000;


    @Override
    public int bindLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        this.view = view;
        hiddeTitleBar();
        //添加动画效果
        animation = new AlphaAnimation(0.8f, 1.0f);
        animation.setDuration(animationTime);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //跳转界面
                getOperation().forward(MainActivity.class);
                finish();
            }
        });
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            start();
        } else {
            initPermission();
        }

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private void start() {
        view.setAnimation(animation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("permisson", "" + requestCode);
        if (requestCode == 0) {
            getOperation().forward(MainActivity.class);
            finish();
        } else {
            initPermission();
        }
    }

    private void initPermission() {
        List<String> permissionsNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS)) {
            permissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE)) {
            permissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int size = permissionsNeeded.size();
        if (permissionsNeeded.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), --size);
            }
        } else {
            start();
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                return false;
        }
        return true;
    }
}