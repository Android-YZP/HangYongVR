package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.ui.CircleImageView;
import com.hy.vrfrog.ui.VirtuelPayPlayPriceDialog;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.TimeUtils;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.vrplayer.Definition;
import com.hy.vrfrog.vrplayer.PlayActivity;
import com.hy.vrfrog.vrplayer.VideoPlayActivity;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Smith on 2017/6/19.
 */

public class VideoDetialAdapter extends RecyclerView.Adapter<VideoDetialAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private AlertDialog show;
    private List<VodbyTopicBean.ResultBean> mData;


    /************************************设置点击事件********************************************************/
    private OnItemClickListener mOnItemClickListener = null;

    private IVideoDetailAdapter mCallback;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position,true表示向上
            mOnItemClickListener.onItemClick(v, (int) v.getTag(), v.getId() == R.id.ll_video_up);
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position, boolean isup);
    }

    public void setInnerListener(IVideoDetailAdapter listener){
        this.mCallback = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public VideoDetialAdapter(Context context, List<VodbyTopicBean.ResultBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_detail_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        LogUtil.i("videoadapter is = " + position);
        //判断1直播，0点播

        holder.mUpVideoLl.setTag(position);
        holder.mDownVideoLl.setTag(position);
        holder.mUpVideoLl.setOnClickListener(this);
        holder.mDownVideoLl.setOnClickListener(this);


        int type = mData.get(position).getType();
        if (type == VedioContants.Video) {//点播
            holder.mllRoomName.setVisibility(View.GONE);
            holder.mRlPersonName.setVisibility(View.GONE);
            holder.mVideoLiveLl.setVisibility(View.VISIBLE);
            holder.mVideoName.setVisibility(View.VISIBLE);

            if (mData.size() != 1) {
                if (position == 0) {
                    holder.mUpVideoLl.setVisibility(View.GONE);
                    holder.mDownVideoLl.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(R.mipmap.video_down)
                            .into(holder.mIvdowngif);

                } else if (position == mData.size() - 1) {
                    holder.mDownVideoLl.setVisibility(View.GONE);
                    holder.mUpVideoLl.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(R.mipmap.video_up)
                            .into(holder.mIvupgif);

                } else {
                    holder.mDownVideoLl.setVisibility(View.VISIBLE);
                    holder.mUpVideoLl.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(R.mipmap.video_down)
                            .into(holder.mIvdowngif);
                    Glide.with(context)
                            .load(R.mipmap.video_up)
                            .into(holder.mIvupgif);
                }

            } else {
                holder.mUpVideoLl.setVisibility(View.GONE);
                holder.mDownVideoLl.setVisibility(View.GONE);
            }

            int isall = mData.get(position).getIsall();
            if (isall == VedioContants.TWO_D_VEDIO) {//2D
                holder.mVideo3DImg.setImageResource(R.mipmap.video_play_2d);
            } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
                holder.mVideo3DImg.setImageResource(R.mipmap.video_play_view);
            } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
                holder.mVideo3DImg.setImageResource(R.mipmap.video_play_3d);
            } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
                holder.mVideo3DImg.setImageResource(R.mipmap.video_play_vr);
            }

            if (mData.get(position).getPrice() == 0){
                holder.mPayImg.setVisibility(View.GONE);
            }else {
                holder.mPayImg.setVisibility(View.VISIBLE);
            }

            Glide.with(context).load(mData.get(position).getHead()).into(holder.mVideoNameHead);
            holder.mVideoNameTv.setText(mData.get(position).getUsername());
            holder.mVideoPlayTitleTv.setText(mData.get(position).getChannelName());
            holder.mVideoPLayTimeTv.setText(TimeUtils.generateTime(Integer.parseInt(String.valueOf(mData.get(position).getTime()))));

            if (mData.get(position).getFormat() != null) {
                holder.mVideoPlayDateTv.setText((String) mData.get(position).getFormat());
            }
            holder.mVideoPlayBig.setText(String.valueOf(mData.get(position).getSize()));
            if (mData.get(position).getIntroduce() != null) {
                holder.mVideoPlayMessageTv.setText((String) mData.get(position).getIntroduce());
            }

            holder.mGiveReward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onGiveReward(position);
                }
            });

            holder.mTvPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCallback.onPlayVideo(position);

                }
            });



        } else if (type == VedioContants.Living) {//直播
            holder.mllRoomName.setVisibility(View.VISIBLE);
            holder.mRlPersonName.setVisibility(View.VISIBLE);
            holder.mVideoLiveLl.setVisibility(View.GONE);
            holder.mVideoName.setVisibility(View.GONE);
            //加载圆形头像
//            ImageOptions imageOptions = new ImageOptions.Builder().setCircular(true).build(); //淡入效果
//            x.image().bind(holder.mIvRoomHead, HttpURL.IV_HOST + mData.get(position).getHead(), imageOptions, new Callback.CommonCallback<Drawable>() {
//                @Override
//                public void onSuccess(Drawable result) {
//
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    ex.printStackTrace();
//                    UIUtils.showTip("背景图片加载失败,请刷新重试");
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//                }
//
//
//                @Override
//                public void onFinished() {
//                }
//            });

            holder.mTvPersonName.setText(mData.get(position).getUsername());
            holder.mTvChannelName.setText(mData.get(position).getChannelName());
            //是否付费
            if (mData.get(position).getPrice() == 0) {
                holder.mTvRoomPay.setVisibility(View.GONE);
            } else {
                holder.mTvRoomPay.setVisibility(View.VISIBLE);
            }
            holder.mTvPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvPlayer;
        private TextView mTvPersonName;
        private TextView mTvRoomPay;
        private TextView mTvChannelName;
        private ImageView mIvRoomHead;
        private RelativeLayout mRlPersonName;
        private LinearLayout mllRoomName;

        private LinearLayout mVideoLiveLl;
        private TextView mVideoPlayTitleTv;
        private TextView mVideoPlayTitleSecond;
        private TextView mVideoPlayDateTv;
        private TextView mVideoPlayBig;
        private TextView mVideoPLayTimeTv;
        private TextView mVideoPlayMessageTv;
        private ImageView mVideo3DImg;
        private ImageView mVideoPlayAttentionImg;
        private LinearLayout mVideoName;

        private CircleImageView mVideoNameHead;
        private TextView mVideoNameTv;

        private LinearLayout mUpVideoLl;
        private LinearLayout mDownVideoLl;
        private ImageView mIvupgif;
        private ImageView mIvdowngif;

        private ImageButton mDowning;
        private ImageButton mGiveReward;
        private ImageButton mShare;

        private ImageView mPayImg;


        MyViewHolder(View view) {
            super(view);
            mTvPlayer = (TextView) view.findViewById(R.id.title);
            mTvPersonName = (TextView) view.findViewById(R.id.tv_room_person_name);
            mTvChannelName = (TextView) view.findViewById(R.id.tv_play_channelName);
            mTvRoomPay = (TextView) view.findViewById(R.id.tv_pay);
            mIvRoomHead = (ImageView) view.findViewById(R.id.iv_room_head);
            mRlPersonName = (RelativeLayout) view.findViewById(R.id.ll_root_person_name);
            mllRoomName = (LinearLayout) view.findViewById(R.id.ll_root_room_name);

            mVideoLiveLl = (LinearLayout) view.findViewById(R.id.ll_video_live);
            mVideoPlayTitleTv = (TextView) view.findViewById(R.id.tv_video_play_title);
            mVideoPlayTitleSecond = (TextView) view.findViewById(R.id.tv_video_play_title_second);
            mVideoPLayTimeTv = (TextView) view.findViewById(R.id.tv_video_play_time);
            mVideoPlayDateTv = (TextView) view.findViewById(R.id.tv_video_play_date);
            mVideoPlayBig = (TextView) view.findViewById(R.id.tv_video_play_big);
            mVideoPlayMessageTv = (TextView) view.findViewById(R.id.tv_video_play_message);

            mVideo3DImg = (ImageView) view.findViewById(R.id.img_video_play_3d);
            mVideoPlayAttentionImg = (ImageView) view.findViewById(R.id.img_video_play_attention);
            mPayImg = (ImageView)view.findViewById(R.id.img_video_play_price);

            mVideoName = (LinearLayout) view.findViewById(R.id.ll_video_name);
            mVideoNameHead = (CircleImageView) view.findViewById(R.id.img_video_head);
            mVideoNameTv = (TextView) view.findViewById(R.id.tv_video_name);

            mDownVideoLl = (LinearLayout) view.findViewById(R.id.ll_video_down);
            mUpVideoLl = (LinearLayout) view.findViewById(R.id.ll_video_up);
            mIvupgif = (ImageView) view.findViewById(R.id.iv_up_gif);
            mIvdowngif = (ImageView) view.findViewById(R.id.iv_down_gif);

            mDowning = (ImageButton)view.findViewById(R.id.ib_video_down);
            mGiveReward = (ImageButton)view.findViewById(R.id.ib_video_shang);
            mShare = (ImageButton)view.findViewById(R.id.ib_video_share);



        }
    }

    public interface IVideoDetailAdapter{

        void onGiveReward(int position);

        void onPlayVideo(int position);

    }

}
