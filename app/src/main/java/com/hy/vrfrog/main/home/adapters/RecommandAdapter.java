package com.hy.vrfrog.main.home.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.TopicBean;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.main.home.bean.RecommendBean;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.utils.TimeUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smith on 2017/7/4.
 */

public class RecommandAdapter extends RecyclerView.Adapter<RecommandAdapter.ListHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private static final String ACTION = "com.jt.base.SENDBROADCAST";
    //获取从Activity中传递过来每个item的数据集合
    private List<String> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;
    private Activity context;
    private int position;
    private ArrayList<RecommendBean> mList;
    private int TopicId;
    //构造函数


    public RecommandAdapter(Activity context, ArrayList<RecommendBean> resultBean, int TopicId) {
        this.context = context;
        this.mList = resultBean;
        this.TopicId = TopicId;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }


    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new RecommandAdapter.ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new RecommandAdapter.ListHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_video_item_main, parent, false);
        return new RecommandAdapter.ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, final int position) {

        //图片


            //设置视频的描述信息
            holder.mTvVideoDesc.setText(mList.get(position).getTitle());
            if (mList.get(position).getTitle() != null)
                holder.mTvVideoTime.setText(TimeUtils.generateTime(Integer.parseInt(mList.get(position).getTitle())));//设置时间


    }

    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder {
        private XCRoundRectImageView mivVideoImg;
        private TextView mTvVideoDesc;
        private TextView mTvVideoTime;

        public ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_video_img);
            mTvVideoDesc = (TextView) itemView.findViewById(R.id.tv_video_desc);
            mTvVideoTime = (TextView) itemView.findViewById(R.id.tv_video_time);
        }
    }


    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mList.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return mList.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return mList.size() + 1;
        } else {
            return mList.size() + 2;
        }
    }


}
