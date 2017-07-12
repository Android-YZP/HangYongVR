package com.jt.base.videos.activitys;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.activitys.MainActivity;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.GetRoomBean;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.videoDetails.adapters.RvAdapter;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import org.xutils.common.util.LogUtil;

import java.util.List;

public class VideoDetialActivity extends AppCompatActivity {
    private static final String ACTION = "com.jt.base.SENDBROADCAST";
    private static final int HTTP_SUCCESS = 0;
    private static final String COUNT = "3";//每次获取到的数据
    private RecyclerViewPager mRvVideoDetaillist;
    private VrPanoramaView panoWidgetView;
    private SwipyRefreshLayout mSwipyRefresh;
    private int mPager = 1;
    private List<GetRoomBean.ResultBean> mRoomLists;
    private RvAdapter mRvAdapter;
    private GetRoomBean mRoomListBean;
    private LinearLayout mIvDetialErrorBg;
    private ImageView mIvTwoDBg;
    private ViewPager mViewpager;
    private DrawerLayout mDlLayout;
    private Handler handler = new Handler();
    private int mCurrentPosition = 0;//判断这个界面的第一屏应该展示哪一个界面,默认第一页
    boolean isScroll = true;//是不是手指滑动过来的
    private RelativeLayout mRlRoot;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detials);
        initView();
        initPanorama();
        initRecyclerViewPager();

        //用二个结合起来拼装滑动手势
        mSwipyRefresh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.i("1");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtil.i("2");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.i("3");
                        break;
                }
                return false;
            }
        });
    }


    private void initView() {
        mSwipyRefresh = (SwipyRefreshLayout) findViewById(R.id.sf_detail_SwipeRefreshLayout);
        mRvVideoDetaillist = (RecyclerViewPager) findViewById(R.id.rv_video_detail_list);
        mIvDetialErrorBg = (LinearLayout) findViewById(R.id.iv_detial_bg);
        mRlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view_main);
        mIvTwoDBg = (ImageView) findViewById(R.id.iv_two_bg);
    }

    private void initRecyclerViewPager() {
        //初始化竖直的viewPager
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };


        mRvVideoDetaillist.setLayoutManager(layout);

        //控制全景图的显示和影藏
        mRvVideoDetaillist.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    panoWidgetView.setVisibility(View.GONE);
                    mIvTwoDBg.setVisibility(View.GONE);//显示2D图片
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //得到当前显示的位置，判别背景图显示那一张？
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        LogUtil.i(firstItemPosition + "------------------------");


//                        //判断是不是全景图片，来显示到底要不要显示全景图片
//                        int isall = mRoomLists.get(firstItemPosition).getIsall();
//                        if (isall == VedioContants.ALL_VIEW_VEDIO) {
//                            mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
//                            initPanorama(HttpURL.IV_HOST + mRoomLists.get(firstItemPosition).getImg());
//                        } else if (isall == VedioContants.TWO_D_VEDIO) {
//                            initTwoD(HttpURL.IV_HOST + mRoomLists.get(firstItemPosition).getImg(), mIvTwoDBg);
//                        }



                    }
                }
            }
        });
    }


    /**
     * 初始化全景图播放器
     */
    private void initPanorama(final String url) {
        LogUtil.i(url);
        //加载背景图片
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(VideoDetialActivity.this)
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
        }, 400);
    }

    /**
     * 初始化2D图播放器
     */
    private void initTwoD(final String url, final ImageView imageView) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(VideoDetialActivity.this)
                        .load(url)
                        .crossFade()
                        .into(imageView);
                panoWidgetView.setVisibility(View.GONE);
                mIvTwoDBg.setVisibility(View.VISIBLE);
            }
        }, 300);
        mIvTwoDBg.setImageBitmap(null);

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
            panoWidgetView.setVisibility(View.VISIBLE);
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
