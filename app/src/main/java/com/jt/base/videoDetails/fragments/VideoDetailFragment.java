package com.jt.base.videoDetails.fragments;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.GetRoomBean;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.adapters.RvAdapter;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.List;

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
        mRvVideoDetaillist = (RecyclerViewPager) view.findViewById(R.id.rv_video_detail_list);
        mSwipyRefresh = (SwipyRefreshLayout) view.findViewById(R.id.sf_detail_SwipeRefreshLayout);
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
                } else if (newState == OnScrollListener.SCROLL_STATE_IDLE) {
                    //滑动结束之后就加载图片
                    initPanorama(HttpURL.IV_HOST + mRoomLists.get(mCurrentPage).getImg());
                }
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
    private void initPanorama(String url) {
        LogUtil.i(url);
        //加载背景图片
        Glide.with(this)
                .load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        final BitmapDrawable bd = (BitmapDrawable) resource;
                        //从第二页开始,每次加载图片从这里开始
                        panoWidgetView.loadImageFromBitmap(bd.getBitmap(), panoOptions);
                    }
                });
    }

    /**
     * 直播列表
     */
    private void HttpRoomList(String pager, final Boolean isLodingMore) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        mSwipyRefresh.setRefreshing(true);

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
                    if (isLodingMore) {//加载更多
                        mRoomLists.addAll(mRoomListBean.getResult());
                        mRvAdapter.notifyDataSetChanged();
                    } else {//刷新数据
                        if (mRoomLists != null) mRoomLists.clear();//刷新的时候清除原有数据
                        mRoomLists = mRoomListBean.getResult();
                        mRvAdapter = new RvAdapter(getContext(), mRoomLists);
                        mRvVideoDetaillist.setAdapter(mRvAdapter);
                        //第一次加载背景图片从这里加载
                        initPanorama(HttpURL.IV_HOST + mRoomListBean.getResult().get(0).getImg());
                    }


                } else {
                    UIUtils.showTip(mRoomListBean.getMsg());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");

            }

            @Override
            public void onFinished() {
                super.onFinished();
                mSwipyRefresh.setRefreshing(false);
            }
        });
    }


}
