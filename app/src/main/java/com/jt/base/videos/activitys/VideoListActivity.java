package com.jt.base.videos.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.ForgetYzmBean;
import com.jt.base.http.responsebean.TopicByVideoBean;
import com.jt.base.utils.LongLogUtil;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videos.adapters.VideoListAdapter;
import com.jt.base.videos.ui.SwipeBackActivity;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by wzq930102 on 2017/7/12.
 */

public class VideoListActivity extends SwipeBackActivity {
    private ImageView mImback;
    private RecyclerView mRvVideolist;
    private VideoListAdapter mVideoListAdapter;


//    private DetailVideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_list_activity);
        initView();
        initData();
        initRecyclerView();
    }

    private void initView() {
        mRvVideolist = (RecyclerView) findViewById(R.id.rv_video_list);
    }

    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRvVideolist.setLayoutManager(mLayoutManager);
        mVideoListAdapter = new VideoListAdapter(this, null);
        mRvVideolist.setAdapter(mVideoListAdapter);
        setHeaderView(mRvVideolist);
    }


    private void initData() {
        HttpTopic();

    }

    /**
     * 验证码 27 222
     */
    private void HttpTopic() {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.Topic);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("id", "27");//
        requestParams.addBodyParameter("sourceNum", "222");//
        requestParams.addBodyParameter("keywords", "");//
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
//                LongLogUtil.e("---------------",result);
                TopicByVideoBean topicByVideoBean = new Gson().fromJson(result, TopicByVideoBean.class);
                if (topicByVideoBean.getCode() == 0){

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {

            }

        });
    }



    /**
     * 设置头布局
     */
    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(VideoListActivity.this).inflate(R.layout.vedio_list_head, view, false);

        mVideoListAdapter.setHeaderView(header);//影藏轮播图


    }




}

