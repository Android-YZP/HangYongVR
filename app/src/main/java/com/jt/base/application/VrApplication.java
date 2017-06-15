package com.jt.base.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1762 on 2017/6/12.
 */

public class VrApplication extends Application{
    private static Context context;
    public static List<AppCompatActivity> contexts = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);// 是否输出debug日志, 开启debug会影响性能.
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }



}
