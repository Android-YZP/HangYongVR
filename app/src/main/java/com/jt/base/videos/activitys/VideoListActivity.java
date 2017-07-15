package com.jt.base.videos.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.ForgetYzmBean;
import com.jt.base.http.responsebean.TopicByVideoBean;
import com.jt.base.http.responsebean.TopicImgBean;
import com.jt.base.http.responsebean.VodbyTopicBean;
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
    private ImageView mVideoback;
    private RecyclerView mRvVideolist;
    private VideoListAdapter mVideoListAdapter;
    private TopicImgBean topicImgBean;


//    private DetailVideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_list_activity);
        initView();
        initData();
        initRecyclerView();
        initListenter();
    }

    private void initListenter() {
        mVideoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mRvVideolist = (RecyclerView) findViewById(R.id.rv_video_list);
        mVideoback = (ImageView) findViewById(R.id.im_video_list_return);
    }

    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRvVideolist.setLayoutManager(mLayoutManager);

    }


    private void initData() {
        HttpTopic();
    }

    /**
     * 获取话题
     */
    private void HttpTopic() {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.vodByTopic);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("topicId", "62");//
        requestParams.addBodyParameter("sourceNum", "999");//
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("---------------", result);
                VodbyTopicBean topicByVideoBean = new Gson().fromJson(result, VodbyTopicBean.class);
                if (topicByVideoBean.getCode() == 0) {
                    mVideoListAdapter = new VideoListAdapter(VideoListActivity.this, topicByVideoBean);
                    setHeaderView(mRvVideolist);
                    mRvVideolist.setAdapter(mVideoListAdapter);
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
        ImageView IvHead = (ImageView) findViewById(R.id.tv_video_list_header);
//        Glide.with(VideoListActivity.this)
//                .load(HttpURL.IV_HOST + topicImgBean.getResult().getImg())
//                .asBitmap()
//                .into(IvHead);
        mVideoListAdapter.setHeaderView(header);//影藏轮播图
    }


}

