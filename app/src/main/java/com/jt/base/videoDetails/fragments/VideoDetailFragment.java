package com.jt.base.videoDetails.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailFragment extends Fragment {

    private static final int HTTP_SUCCESS = 0;
    private RecyclerViewPager mRvVideoDetaillist;
    private List<String> mDatas = new ArrayList<>();
    private VrPanoramaView panoWidgetView;
    private VrPanoramaView.Options panoOptions;
    public VideoDetailFragment() {
    }

    public VideoDetailFragment(VrPanoramaView panoWidgetView ,VrPanoramaView.Options panoOptions) {
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
        initData();
        initListenter();
        return view;
    }

    private void initView(View view) {
        mRvVideoDetaillist = (RecyclerViewPager) view.findViewById(R.id.rv_video_detail_list);
    }

    private void initData() {
        for (int i = 0; i < 21; i++) {
            mDatas.add("" + i);
        }
        //初始化竖直的viewPager
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvVideoDetaillist.setLayoutManager(layout);
        mRvVideoDetaillist.setAdapter(new RvAdapter(getContext(), mDatas));
        //从网络获取数据
        HttpRoomList("1", "10");
        //初始化全景图
        initPanorama();
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
                    panoWidgetView.setVisibility(View.VISIBLE);
                }
            }
        });

        //选中的页面
        mRvVideoDetaillist.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i, int i1) {
                LogUtil.i(i + "=" + i1);


            }
        });



    }


    /**
     * 初始化全景图播放器
     */
    private void initPanorama() {
        //加载背景图片
        Glide.with(this)
                .load("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg")
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        BitmapDrawable bd = (BitmapDrawable) resource;
                        panoWidgetView.loadImageFromBitmap(bd.getBitmap(), panoOptions);
                    }
                });
    }




    /**
     * 直播列表
     */
    private void HttpRoomList(String pager, String count) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.RoomList);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("pager", pager);//页数
        requestParams.addBodyParameter("count", count);//数量
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                GetRoomBean RoomListBean = new Gson().fromJson(result, GetRoomBean.class);
                if (RoomListBean.getCode() == HTTP_SUCCESS) {
                    LogUtil.i("成功");

                } else {
                    UIUtils.showTip(RoomListBean.getMsg());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");

            }
        });


    }


}
