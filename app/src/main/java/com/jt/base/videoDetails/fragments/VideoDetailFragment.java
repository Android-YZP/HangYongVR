package com.jt.base.videoDetails.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.GetRoomBean;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.videoDetails.adapters.RvAdapter;
import com.jt.base.vrplayer.PlayActivity;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

@SuppressLint("ValidFragment")
public class VideoDetailFragment extends Fragment {

    private static final int HTTP_SUCCESS = 0;
    private static final String COUNT = "3";//每次获取到的数据
    private RecyclerViewPager mRvVideoDetaillist;
    private VrPanoramaView panoWidgetView;
    private VrPanoramaView.Options panoOptions;
    private SwipyRefreshLayout mSwipyRefresh;
    private int mPager = 1;
    private List<GetRoomBean.ResultBean> mRoomLists;
    private RvAdapter mRvAdapter;
    private GetRoomBean mRoomListBean;
    private int mCurrentPage = 0;
    private LinearLayout mIvDetialErrorBg;
    private ImageView mIvTwoDBg;
    private Handler handler = new Handler();
    private boolean isScrolled;

    public VideoDetailFragment() {
    }

    public VideoDetailFragment(VrPanoramaView panoWidgetView, VrPanoramaView.Options panoOptions) {
        this.panoWidgetView = panoWidgetView;
        this.panoOptions = panoOptions;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);
        initView(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListenter();
    }

    private void initView(View view) {
        mSwipyRefresh = (SwipyRefreshLayout) view.findViewById(R.id.sf_detail_SwipeRefreshLayout);
        mRvVideoDetaillist = (RecyclerViewPager) view.findViewById(R.id.rv_video_detail_list);
        mIvDetialErrorBg = (LinearLayout) view.findViewById(R.id.iv_detial_bg);
        mIvTwoDBg = (ImageView) view.findViewById(R.id.iv_two_bg);

    }

    private void initData() {
        initRecyclerViewPager();
        HttpRoomList(mPager + "", false);
    }

    private void initRecyclerViewPager() {
        //初始化竖直的viewPager
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvVideoDetaillist.setLayoutManager(layout);
    }


    private void initListenter() {
        //控制全景图的显示和影藏
        mRvVideoDetaillist.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    panoWidgetView.setVisibility(View.GONE);
                    mIvTwoDBg.setVisibility(View.GONE);//显示2D图片
                } else if (newState == OnScrollListener.SCROLL_STATE_IDLE) {
                    //得到当前显示的位置，判别背景图显示那一张？
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        if (mRoomLists == null) return;
                        //判断是不是全景图片，来显示到底要不要显示全景图片
                        int isall = mRoomLists.get(firstItemPosition).getIsall();
                        if (isall == VedioContants.ALL_VIEW_VEDIO) {
                            mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
                            initPanorama(HttpURL.IV_HOST + mRoomLists.get(firstItemPosition).getImg());
                        } else if (isall == VedioContants.TWO_D_VEDIO) {

                            initTwoD(HttpURL.IV_HOST + mRoomLists.get(firstItemPosition).getImg(), mIvTwoDBg);
                        }
                    }
                }
            }
        });


        mRvVideoDetaillist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


        //选中的页面
        mRvVideoDetaillist.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i, final int i1) {
                LogUtil.i(i + "=" + i1);
                mCurrentPage = i1;
            }
        });

        //刷新和下拉加载的监听
        mSwipyRefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                LogUtil.i(direction + "");
                if (direction.equals(SwipyRefreshLayoutDirection.TOP)) {
                    //刷新列表
                    mPager = 1;
                    HttpRoomList(mPager + "", false);


                } else if (direction.equals(SwipyRefreshLayoutDirection.BOTTOM)) {
                    if (mRoomListBean == null) return;
                    mPager++;
                    if (mPager >= mRoomListBean.getPage().getTotalPage()) {
                        UIUtils.showTip("没有数据了");
                        mSwipyRefresh.setRefreshing(false);
                    } else {
                        HttpRoomList(mPager + "", true);
                        LogUtil.i(mPager + "");
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
                Glide.with(getContext())
                        .load(url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                panoWidgetView.loadImageFromBitmap(resource, panoOptions);
                            }
                        });
            }
        },400);






    }

    /**
     * 初始化2D图播放器
     */


    private void initTwoD(final String url, final ImageView imageView) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(getActivity())
                        .load(url)
                        .crossFade()
                        .into(imageView);
            }
        },400);
        mIvTwoDBg.setImageBitmap(null);
        mIvTwoDBg.setVisibility(View.VISIBLE);//隐藏2D图片
    }

    /**
     * 直播列表
     */
    private void HttpRoomList(String pager, final Boolean isLodingMore) {
        mSwipyRefresh.setRefreshing(true);

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            mIvDetialErrorBg.setVisibility(View.VISIBLE);
            panoWidgetView.setVisibility(View.GONE);
            mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
            mRvVideoDetaillist.setVisibility(View.VISIBLE);
            mSwipyRefresh.setRefreshing(false);
            //清空数据
            if (mRoomLists != null) {
                mRoomLists.clear();
                mRvAdapter.notifyDataSetChanged();
            }
            return;
        }

        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.RoomList);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("page", pager);//页数
        requestParams.addBodyParameter("count", COUNT);//数量
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                mRoomListBean = new Gson().fromJson(result, GetRoomBean.class);
                if (mRoomListBean.getCode() == HTTP_SUCCESS) {
                    if (mRoomListBean.getResult().size() < 1) {
                        panoWidgetView.setVisibility(View.GONE);
                        UIUtils.showTip("当前没有直播。。。。");
                        return;
                    }
                    mIvDetialErrorBg.setVisibility(View.GONE);
                    panoWidgetView.setVisibility(View.VISIBLE);
                    mRvVideoDetaillist.setVisibility(View.VISIBLE);

                    if (isLodingMore) {//加载更多
                        mRoomLists.addAll(mRoomListBean.getResult());
                        mRvAdapter.notifyDataSetChanged();
                    } else {//刷新数据
                        if (mRoomLists != null) {
                            mRoomLists.clear();
                            mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
                            mRvAdapter.notifyDataSetChanged();
                        }

                        mRoomLists = mRoomListBean.getResult();
                        mRvAdapter = new RvAdapter(getContext(), mRoomLists);
                        mRvVideoDetaillist.setAdapter(mRvAdapter);

                        int isall = mRoomListBean.getResult().get(0).getIsall();
                        if (isall == VedioContants.ALL_VIEW_VEDIO) {
                            mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
                            initPanorama(HttpURL.IV_HOST + mRoomListBean.getResult().get(0).getImg());
                        } else if (isall == VedioContants.TWO_D_VEDIO) {
                            panoWidgetView.setVisibility(View.GONE);
                            initTwoD(HttpURL.IV_HOST + mRoomListBean.getResult().get(0).getImg(), mIvTwoDBg);
                        }
                    }
                } else {
                    UIUtils.showTip(mRoomListBean.getMsg());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
                mIvDetialErrorBg.setVisibility(View.VISIBLE);
                panoWidgetView.setVisibility(View.GONE);
                mRvVideoDetaillist.setVisibility(View.VISIBLE);
                mIvTwoDBg.setVisibility(View.GONE);//隐藏2D图片
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mSwipyRefresh.setRefreshing(false);
            }
        });
    }


}
