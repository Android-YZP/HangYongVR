package com.jt.base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.jt.base.application.VrApplication;

/**
 * Created by aaa on 2016/7/13.
 */
public class UIUtils {

    public static Context getContext() {
        return VrApplication.getContext();
    }


    ///////////加载资源文件/////////////
    // 获取字符串
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    // 获取字符串数组
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    // 获取图片
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    // 获取颜色
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    //根据id获取颜色的状态选择器
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    // 获取尺寸
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
    }

    ///////dip和px转换////////
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    public static void showTip(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    ////////////加载布局文件////////

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }


    public static void LogUtils(String log) {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        Log.d("androidYZP==========>", log + "YZP");
    }




}
