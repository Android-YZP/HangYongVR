package com.hy.vrfrog.main.home.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.main.activitys.Guide1Activity;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.main.home.adapters.VideoDetialAdapter;
import com.hy.vrfrog.ui.SwipeBackActivity;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class VideoDetialActivity extends SwipeBackActivity {
    private static final String COUNT = "10";//每次获取到的数据
    private RecyclerViewPager mRvVideoDetaillist;
    private VrPanoramaView panoWidgetView;
    private SwipyRefreshLayout mSwipyRefresh;
    private int mPager = 1;
    private LinearLayout mIvDetialErrorBg;
    private ImageView mIvTwoDBg;
    private ImageView mIvupgif;
    private ImageView mIvdowngif;
    private RelativeLayout mRlRoot;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private int mPosition;
    private int mTopicId;
    private List<VodbyTopicBean.ResultBean> mData;
    private VideoDetialAdapter mVideoDetialAdapter;
    private int mTotalpage;
    private boolean isFling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_detials);
        initView();
        initPanorama();
        initData();
        initListenter();
        initRecyclerViewPager();
    }

    private void initView() {
        mSwipyRefresh = (SwipyRefreshLayout) findViewById(R.id.sf_detail_SwipeRefreshLayout);
        mRvVideoDetaillist = (RecyclerViewPager) findViewById(R.id.rv_video_detail_list);
        mIvDetialErrorBg = (LinearLayout) findViewById(R.id.iv_detial_bg);
        mRlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view_main);
        mIvTwoDBg = (ImageView) findViewById(R.id.iv_two_bg);
        mIvupgif = (ImageView) findViewById(R.id.iv_up_gif);
        mIvdowngif = (ImageView) findViewById(R.id.iv_down_gif);
        Glide.with(VideoDetialActivity.this)
                .load(R.mipmap.video_up)
                .into(mIvupgif);
        Glide.with(VideoDetialActivity.this)
                .load(R.mipmap.video_down)
                .into(mIvdowngif);
    }


    private void initData() {
        Intent intent = getIntent();
        mPosition = intent.getIntExtra(VedioContants.Position, 0);
        mTopicId = intent.getIntExtra(VedioContants.TopicId, 0);
        HttpTopic(mTopicId, mPager);
    }


    private void initListenter() {
        //刷新和下拉加载的监听
        mSwipyRefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                LogUtil.i(direction + "");
                if (direction.equals(SwipyRefreshLayoutDirection.TOP)) {
                    //刷新列表
                    mPosition = 0;
                    mPager = 1;
                    if (mData != null) mData.clear();
                    HttpTopic(mTopicId, mPager);
                } else if (direction.equals(SwipyRefreshLayoutDirection.BOTTOM)) {
                    if (mPager >= mTotalpage) {
                        UIUtils.showTip("没有更多数据了");
                        mSwipyRefresh.setRefreshing(false);
                    } else {
                        HttpTopic(mTopicId, ++mPager);
                    }
                }
            }
        });

    }

    private void initRecyclerViewPager() {
        //初始化竖直的viewPager
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvVideoDetaillist.setLayoutManager(layout);
        //控制全景图的显示和影藏
        mRvVideoDetaillist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    LogUtil.e("SCROLL_STATE_TOUCH_SCROLL");
                    isFling = true;
                    mSwipyRefresh.setEnabled(false);
                    panoWidgetView.setVisibility(View.GONE);
                    mIvTwoDBg.setVisibility(View.GONE);
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    LogUtil.e("SCROLL_STATE_IDLE");
                    mSwipyRefresh.setEnabled(true);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();

                        if (firstItemPosition == 0) {
                            showBg(firstItemPosition);
                        }


                        if (!isFling)
                            showBg(firstItemPosition);

                    }
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    LogUtil.e("SCROLL_STATE_FLING");
                    isFling = false;
                }
            }
        });

        //滑动到指定位置
        mRvVideoDetaillist.scrollToPosition(mPosition);
    }


    /**
     * 显示背景图
     */
    private void showBg(int firstItemPosition) {
        if (mData != null && mData.size() > 0) {
            int isall = mData.get(firstItemPosition).getIsall();
            if (isall == VedioContants.ALL_VIEW_VEDIO) {
                initPanorama(HttpURL.IV_HOST + mData.get(firstItemPosition).getImg());
            } else if (isall == VedioContants.TWO_D_VEDIO) {
                initTwoD(HttpURL.IV_HOST + mData.get(firstItemPosition).getImg(), mIvTwoDBg);
            }
        }
    }


    /**
     * 获取话题
     */
    private void HttpTopic(int topicId, int pager) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            mIvDetialErrorBg.setVisibility(View.VISIBLE);
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.vodByTopic);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("topicId", topicId + "");//
        requestParams.addBodyParameter("sourceNum", "222");//
        requestParams.addBodyParameter("page", pager + "");//
        requestParams.addBodyParameter("count", COUNT);//
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("---------------", result);
                VodbyTopicBean topicByVideoBean = new Gson().fromJson(result, VodbyTopicBean.class);
                if (topicByVideoBean.getCode() == 0) {
                    mIvDetialErrorBg.setVisibility(View.GONE);
                    mTotalpage = topicByVideoBean.getPage().getTotalPage();
                    if (mData != null && mData.size() > 0) {//下拉加载
                        mData.addAll(topicByVideoBean.getResult());
                        mVideoDetialAdapter.notifyDataSetChanged();
                    } else {
                        mData = topicByVideoBean.getResult();
                        mVideoDetialAdapter = new VideoDetialAdapter(VideoDetialActivity.this, mData);
                        mRvVideoDetaillist.setAdapter(mVideoDetialAdapter);
                        showBg(mPosition);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
                mSwipyRefresh.setRefreshing(false);
            }
        });
    }


    /**
     * 初始化全景图播放器
     */
    private void initPanorama(final String url) {

        //加载背景图片
        Glide.with(VideoDetialActivity.this)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
                        panoWidgetView.loadImageFromBitmap(resource, panoOptions);
                        LogUtil.i(url);
                    }
                });
    }

    /**
     * 初始化2D图播放器
     */
    private void initTwoD(final String url, final ImageView imageView) {
        mIvTwoDBg.setImageBitmap(null);
        mIvTwoDBg.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext())
                .load(url)
                .crossFade()
                .into(imageView);
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


    @Override
    protected void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        panoWidgetView.resumeRendering();
        super.onResume();
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
