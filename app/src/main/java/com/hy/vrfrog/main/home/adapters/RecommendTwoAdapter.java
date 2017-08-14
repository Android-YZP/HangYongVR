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
import com.hy.vrfrog.http.responsebean.RecommendBean;
import com.hy.vrfrog.http.responsebean.TopicBean;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.utils.TimeUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by Smith on 2017/7/4.
 */

public class RecommendTwoAdapter extends RecyclerView.Adapter<RecommendTwoAdapter.ListHolder> {
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
    private ViewPager mViewpager;
    private int position;
    private List<RecommendBean.ResultBeanX.ResultBean> resultBean;
    private int TopicId;
    //构造函数
    private int type ;


    public RecommendTwoAdapter(Activity context, List<RecommendBean.ResultBeanX.ResultBean> resultBean, int TopicId, int type) {
        this.context = context;
        this.resultBean = resultBean;
        this.TopicId = TopicId;
        this.type = type ;
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
            return new RecommendTwoAdapter.ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new RecommendTwoAdapter.ListHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_video_item_main, parent, false);
        return new RecommendTwoAdapter.ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, final int position) {

            Glide.with(context)
                    .load(HttpURL.IV_HOST + resultBean.get(position).getImg1())
                    .asBitmap()
                    .error(R.mipmap.camera_off)
                    .into(holder.mivVideoImg);

            holder.mTvVideoDesc.setText(resultBean.get(position).getChannelName());
            if (resultBean.get(position).getTime() != null)
                holder.mTvVideoTime.setText(TimeUtils.generateTime(Integer.parseInt(resultBean.get(position).getTime())));//设置时间

            holder.mivVideoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoDetialActivity.class);
                    intent.putExtra(VedioContants.Position, position);//首次显示在哪一个封面
                    intent.putExtra(VedioContants.TopicId, TopicId);//哪个话题
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
                }
            });

            //设置视频的描述信息

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
            mTvVideoTime = (TextView) itemView.findViewById(R.id.tv_video_one_time);

        }
    }


    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        LogUtil.i("type is =" + type);
        if (mHeaderView == null && mFooterView == null) {
            if (type == 1){
                if (resultBean.size() > 3){
                    return 3;
                }

            }else if (type == 2) {
                if (resultBean.size() > 7) {
                    return 7;
                }
            }

            return resultBean.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return resultBean.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {

            return resultBean.size() + 1;
        } else {

            return resultBean.size() + 2;
        }
    }


}
