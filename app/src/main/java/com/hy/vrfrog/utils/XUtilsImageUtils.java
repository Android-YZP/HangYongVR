package com.hy.vrfrog.utils;

/**
 * Created by Smith on 2017/6/30.
 */


import android.widget.ImageView;

import com.hy.vrfrog.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Chen on 2016/4/5.
 */
public class XUtilsImageUtils {


    /**
     * 显示圆角图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     * @param radius    圆角半径，
     */
    public static void display(ImageView imageView, String iconUrl, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)//是否对图片进行裁剪
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setIgnoreGif(false)
                .setSquare(true)
                .setRadius(DensityUtil.dip2px(radius))
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    /**
     * 显示圆形头像，第三个参数为true
     *
     * @param imageView  图像控件
     * @param iconUrl    图片地址
     * @param isCircluar 是否显示圆形
     */
    public static void display(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(isCircluar)
                .setCrop(true)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }
}





















