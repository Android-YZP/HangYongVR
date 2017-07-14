package com.jt.base.videos.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.TopicBean;
import com.jt.base.ui.XCRoundRectImageView;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.videos.activitys.VideoDetialActivity;

import java.util.List;

/**
 * Created by Smith on 2017/7/4.
 */

public class MainVideosAdapter extends RecyclerView.Adapter<MainVideosAdapter.ListHolder> {
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
    private List<TopicBean.ResultBeanX.ResultBean> resultBean;
    //构造函数


    public MainVideosAdapter(Activity context, ViewPager mViewpager, List<TopicBean.ResultBeanX.ResultBean> resultBean) {
        this.context = context;
        this.mViewpager = mViewpager;
        this.resultBean = resultBean;
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
            return new MainVideosAdapter.ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new MainVideosAdapter.ListHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_video_item_main, parent, false);
        return new MainVideosAdapter.ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, final int position) {

        //图片
        if (position < getItemCount() - 1) {
            Glide.with(context)
                    .load(HttpURL.IV_HOST + resultBean.get(position).getImg())
                    .asBitmap()
                    .error(R.mipmap.camera_off)
                    .into(holder.mivVideoImg);

            //设置视频的描述信息
            holder.mTvVideoDesc.setText(resultBean.get(position).getChannelName());



            holder.mivVideoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoDetialActivity.class);

//                    intent.putExtra("pic", HttpURL.IV_HOST + resultBean.get(position).getImg1());
//
//                    intent.putExtra("desc", resultBean.get(position).getChannelName());
//                    //判断1直播，0点播
//                    int type = resultBean.get(position).getType();
//
//                    if (type == VedioContants.Video) {//点播
//                        intent.putExtra("url", resultBean.get(position).getRtmpDownstreamAddress());
//                    } else if (type == VedioContants.Living) {//直播
//                        intent.putExtra("url", resultBean.get(position).getRtmpDownstreamAddress());
//                    }
//
//
//                    //判断视频类型
//                    int isall = resultBean.get(position).getIsall();
//                    if (isall == VedioContants.TWO_D_VEDIO) {//2D
//                        intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
//                    } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
//                        intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
//                    } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
//                        intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
//                    } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
//                        intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
//                    }

                    intent.putExtra(VedioContants.Datas, new Gson().toJson(resultBean));
                    intent.putExtra(VedioContants.Position, position);//首次显示在哪一个封面
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
                }
            });
        } else if (position == getItemCount() - 1) {//脚布局

        }
    }

    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder {
        private XCRoundRectImageView mivVideoImg;
        private TextView mTvVideoDesc;

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
        }
    }


    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return resultBean.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return resultBean.size()+1;
        } else if (mHeaderView != null && mFooterView == null) {
            return resultBean.size()+1;
        } else {
            return resultBean.size()+2;
        }
    }

}
