package com.hy.vrfrog.main.home.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
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
import com.hy.vrfrog.application.VrApplication;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.RechargeBean;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.main.activitys.Guide1Activity;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.ui.GiveRewardDialog;
import com.hy.vrfrog.ui.LoadingDataUtil;
import com.hy.vrfrog.ui.VirtuelPayPlayPriceDialog;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.ToolToast;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.main.home.adapters.VideoDetialAdapter;
import com.hy.vrfrog.ui.SwipeBackActivity;
import com.hy.vrfrog.vrplayer.VideoPlayActivity;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class VideoDetialActivity extends SwipeBackActivity implements VideoDetialAdapter.IVideoDetailAdapter ,VideoContract.VideoView{
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
    private ImageView mBack;
    private VideoPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_detials);
        initData();
        initView();
        initPanorama();
        initListenter();
        initRecyclerViewPager();
        new VideoPresenter(this);
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
        mBack = (ImageView) findViewById(R.id.ibt_video_detail);

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

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoDetialActivity.this.finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
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
                        int mFirstItemPosition = linearManager.findFirstVisibleItemPosition();
                        if (mFirstItemPosition == 0)
                            showBg(mFirstItemPosition);

                        if (!isFling)
                            showBg(mFirstItemPosition);
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
                        mVideoDetialAdapter.setInnerListener(VideoDetialActivity.this);
                        showBg(mPosition);
                        mVideoDetialAdapter.setOnItemClickListener(new VideoDetialAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, boolean isup) {
                                if (isup) {//点击了上面
                                    mRvVideoDetaillist.scrollToPosition(--position);
                                    showBg(position);
                                } else {//点击了下面
                                    mRvVideoDetaillist.scrollToPosition(++position);
                                    showBg(position);
                                }
                            }
                        });
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

    @Override
    public void onGiveReward(final int position) {

        new GiveRewardDialog(VideoDetialActivity.this).builder()
                .setCanceledOnTouchOutside(true)
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("", new GiveRewardDialog.IGiveReward() {
                    @Override
                    public void GoGiveReward(int count) {
                        LogUtil.i("打赏 = " + count);
                        mPresenter.getRewardData(count,position,mData);
                    }
                })

                .show();
    }

    @Override
    public void onPlayVideo(final int position) {

        LogUtil.i("支付价格 =" + mData.get(position).getPrice());

      if (mData.get(position).getPrice() != 0){

        new VirtuelPayPlayPriceDialog(VideoDetialActivity.this).builder()
                .setCanceledOnTouchOutside(true)
                .setTitle(mData.get(position).getChannelName())
                .setPrice(mData.get(position).getPrice() + "蛙豆")
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mPresenter.goPayMoney(mData.get(position).getPrice(),position,mData);

                    }
                }).show();

      }else {
          //进入点播
          Intent intent = new Intent(VideoDetialActivity.this, VideoPlayActivity.class);
          intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(mData.get(position).getVodInfos()));
          intent.putExtra(VedioContants.PlayType, VedioContants.Video);
          intent.putExtra("vid", mData.get(position).getId());
          intent.putExtra("position",position);
          intent.putExtra("yid",mData.get(position).getUid());


          //判断视频类型
          int isall = mData.get(position).getIsall();
          if (isall == VedioContants.TWO_D_VEDIO) {//2D
              intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
          } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
              intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
          } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
              intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
          } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
              intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
          }
          intent.putExtra("desc", mData.get(position).getChannelName());

          if (NetUtil.isOpenNetwork()) {
              VideoDetialActivity.this.startActivity(intent);
          } else {
              UIUtils.showTip("请连接网络");
          }
      }

    }


    @Override
    public void setPresenter(VideoContract.Presenter presenter) {

        this.mPresenter = (VideoPresenter) presenter;

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showGetRewardResult(GiveRewardBean giveRewardBean) {
        if (giveRewardBean.getCode() == 0){
            ToolToast.buildToast(VideoDetialActivity.this,"打赏成功",1);
        }else {
            ToolToast.buildToast(VideoDetialActivity.this,"蛙豆不足",1);
        }
    }

    @Override
    public void showGetRewardResultError(Throwable ex) {
        UIUtils.showTip("打赏失败");
    }

    @Override
    public void showPayMoneyResult(int position,GiveRewardBean giveRewardBean) {
        if (giveRewardBean.getCode() == 0){
            ToolToast.buildToast(VideoDetialActivity.this,"支付成功",1);
            //进入点播
            Intent intent = new Intent(VideoDetialActivity.this, VideoPlayActivity.class);
            intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(mData.get(position).getVodInfos()));
            intent.putExtra(VedioContants.PlayType, VedioContants.Video);
            intent.putExtra("vid", mData.get(position).getId());
            intent.putExtra("position",position);
            intent.putExtra("yid",mData.get(position).getUid());


            //判断视频类型
            int isall = mData.get(position).getIsall();
            if (isall == VedioContants.TWO_D_VEDIO) {//2D
                intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
            } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
                intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
            } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
                intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
            } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
                intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
            }
            intent.putExtra("desc", mData.get(position).getChannelName());

            if (NetUtil.isOpenNetwork()) {
                VideoDetialActivity.this.startActivity(intent);
            } else {
                UIUtils.showTip("请连接网络");
            }
        }else {
            ToolToast.buildToast(VideoDetialActivity.this,"蛙豆不足",1);
        }
    }

    @Override
    public void showPayMoneyResultError(Throwable ex) {

        UIUtils.showTip("支付失败");

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
