package com.jt.base.videos.activitys;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.jt.base.http.responsebean.TopicImgBean;
import com.jt.base.http.responsebean.VodbyTopicBean;
import com.jt.base.ui.XCRoundRectImageView;
import com.jt.base.utils.LongLogUtil;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.videos.adapters.VideoListAdapter;
import com.jt.base.videos.ui.SwipeBackActivity;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by wzq930102 on 2017/7/12.
 */

public class VideoListActivity extends SwipeBackActivity {
    private ImageView mVideoback;
    private RecyclerView mRvVideolist;
    private VideoListAdapter mVideoListAdapter;
    private int mPager = 1;
    private SwipeRefreshLayout mSrlListTopic;
    private List<VodbyTopicBean.ResultBean> mData;
    private int mDataTotal;
    private int mTopicId;
    private TextView mTvTopicTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_list_activity);
        initView();
        initData();
        initRecyclerView();
        initListenter();
    }

    private void initView() {
        mRvVideolist = (RecyclerView) findViewById(R.id.rv_video_list);
        mVideoback = (ImageView) findViewById(R.id.im_video_list_return);
        mSrlListTopic = (SwipeRefreshLayout) findViewById(R.id.srl_video_list_topic);
        mTvTopicTitle = (TextView) findViewById(R.id.tv_list_topic_title);
    }

    private void initData() {
        mTopicId = getIntent().getIntExtra(VedioContants.TopicId, 0);
        String mTopicTitle = getIntent().getStringExtra(VedioContants.TopicTitle);
        mTvTopicTitle.setText(mTopicTitle);
        HttpTopic(mTopicId, mPager);
    }

    private void initListenter() {
        mVideoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下拉刷新的操作
        mSrlListTopic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPager = 1;
                mData.clear();
                HttpTopic(mTopicId, mPager);
            }
        });

    }


    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRvVideolist.setLayoutManager(mLayoutManager);
        mRvVideolist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean visBottom = UIUtils.isVisBottom(mRvVideolist);
                if (visBottom) {
                    if (mVideoListAdapter.getFooterView() == null) {
                        HttpTopic(mTopicId, ++mPager);
                    } else {
                        return;
                    }
                    if (mData.size() >= mDataTotal) {
                        View v = View.inflate(VideoListActivity.this, R.layout.main_list_no_datas, null);//main_list_item_foot_view
                        mVideoListAdapter.setFooterView(v);
                        mVideoListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    /**
     * 获取话题
     */
    private void HttpTopic(int topicId, int pager) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.vodByTopic);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("topicId", topicId + "");//
        requestParams.addBodyParameter("sourceNum", "222");//
        requestParams.addBodyParameter("page", pager + "");//
        requestParams.addBodyParameter("count", 3 + "");//
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                VodbyTopicBean topicByVideoBean = new Gson().fromJson(result, VodbyTopicBean.class);
                if (topicByVideoBean.getCode() == 0) {
                    mDataTotal = topicByVideoBean.getPage().getTotal();
                    if (mData != null && mData.size() > 0) {
                        mData.addAll(topicByVideoBean.getResult());
                        mVideoListAdapter.notifyDataSetChanged();
                    } else {
                        mData = topicByVideoBean.getResult();
                        mVideoListAdapter = new VideoListAdapter(VideoListActivity.this, mData);
                        setHeaderView(mRvVideolist, topicByVideoBean.getPage());
                        mRvVideolist.setAdapter(mVideoListAdapter);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
                mSrlListTopic.setRefreshing(false);
            }
        });
    }


    /**
     * 设置头布局
     */
    private void setHeaderView(RecyclerView view, VodbyTopicBean.PageBean page) {
        View header = LayoutInflater.from(VideoListActivity.this).inflate(R.layout.vedio_list_head, view, false);
        XCRoundRectImageView IvHead = (XCRoundRectImageView) header.findViewById(R.id.tv_video_list_header);
        TextView TvTopicDesc = (TextView) header.findViewById(R.id.video_list_topic_desc);
        Glide.with(VideoListActivity.this)
                .load(HttpURL.IV_HOST + page.getTopicImg())
                .asBitmap()
                .into(IvHead);
        TvTopicDesc.setText(page.getIntrduce());
        mVideoListAdapter.setHeaderView(header);//影藏轮播图
    }


}

