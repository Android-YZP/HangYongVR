package com.jt.base.videos.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.PlayActivity;

public class VedioDeatilsActivity extends AppCompatActivity implements View.OnClickListener {
    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private ImageView mIvTwoDBg;
    private ImageButton mIbPlay;
    private Intent intent;
    private TextView mTouch;
    private float startx;
    private float starty;
    private float offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_vedio_deatils);
        initView();
        initPanorama();
        initListener();
        initMode();

    }

    private void initListener() {
        mIbPlay.setOnClickListener(this);
        mTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view_main);
        mIvTwoDBg = (ImageView) findViewById(R.id.iv_two_bg);
        mIbPlay = (ImageButton) findViewById(R.id.ib_play);
        mTouch = (TextView) findViewById(R.id.touch);
    }


    //初始化播放器模式
    private void initMode() {
        int vedioode = getIntent().getIntExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, 4);
        if (vedioode == VedioContants.TWO_D_VEDIO) {//2D
            mIvTwoDBg.setImageDrawable(UIUtils.getDrawable(R.drawable.a000));
            intent = new Intent(VedioDeatilsActivity.this, PlayActivity.class);
            intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
            intent.putExtra(com.jt.base.vrplayer.Definition.KEY_PLAY_URL, "http://1253520711.vod2.myqcloud.com/e45ccc42vodtransgzp1253520711/75cb47a59031868222953432256/f0.f40.mp4 ");
        } else if (vedioode == VedioContants.ALL_VIEW_VEDIO) {//全景
            getPanorama(R.drawable.a001);
            intent = new Intent(VedioDeatilsActivity.this, PlayActivity.class);
            intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
            intent.putExtra(com.jt.base.vrplayer.Definition.KEY_PLAY_URL, "http://1253520711.vod2.myqcloud.com/e45ccc42vodtransgzp1253520711/3ac4951a9031868222959099874/f0.f47.mp4 ");
        }
    }

    /**
     * 初始化全景图播放器
     */
    private void initPanorama() {
        loadImageSuccessful = false;//初始化图片状态
        panoWidgetView.setEventListener(new ActivityEventListener());
        //影藏三個界面的按鈕
        panoWidgetView.setFullscreenButtonEnabled(false);
        panoWidgetView.setInfoButtonEnabled(false);
        panoWidgetView.setStereoModeButtonEnabled(false);
        panoWidgetView.setOnTouchListener(null);//禁用手势滑动
        panoOptions.inputType = VrPanoramaView.Options.TYPE_MONO;

    }


    /**
     * 初始化全景图播放器
     */
    private void getPanorama(int url) {
        //加载背景图片
        Glide.with(VedioDeatilsActivity.this)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
                        panoWidgetView.loadImageFromBitmap(resource, panoOptions);
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_play:
                startActivity(intent);
//                Intent intent = new Intent(VedioDeatilsActivity.this, VedioDeatilsActivity.class);
//                //单数点击全景，双数点击2D
//                if (position % 2 == 0) {
//                    intent.putExtra(Definition.TYPE, 1);
//                } else {
//                    intent.putExtra(Definition.TYPE, 2);
//                }
//                context.startActivity(intent);


                UIUtils.showTip("进入播放器");
                break;
            default:
                break;
        }

    }

    @Override
    protected void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        panoWidgetView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        panoWidgetView.shutdown();
        super.onDestroy();
    }


    /**
     * Listen to the important events from widget.
     */
    private class ActivityEventListener extends VrPanoramaEventListener {
        /**
         * Called by pano widget on the UI thread when it's done loading the image.
         */
        @Override
        public void onLoadSuccess() {
            loadImageSuccessful = true;
            Log.e("dflefseofjsdopfj", "Could not decode default bitmap: 2");
        }

        /**
         * Called by pano widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            Log.e("dflefseofjsdopfj", "Could not decode default bitmap: 3");
            loadImageSuccessful = false;
            UIUtils.showTip("图片加载错误");
        }
    }

}
