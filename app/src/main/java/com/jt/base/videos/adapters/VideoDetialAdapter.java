package com.jt.base.videos.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.TopicBean;
import com.jt.base.http.responsebean.VodbyTopicBean;
import com.jt.base.utils.LocalUtils;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.videos.activitys.VideoDetialActivity;
import com.jt.base.vrplayer.PlayActivity;
import com.jt.base.vrplayer.VideoPlayActivity;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by Smith on 2017/6/19.
 */

public class VideoDetialAdapter extends RecyclerView.Adapter<VideoDetialAdapter.MyViewHolder> {
    private Context context;
    private AlertDialog show;
    private List<VodbyTopicBean.ResultBean> mData;
    private Intent intent;

    public VideoDetialAdapter(Context context, List<VodbyTopicBean.ResultBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_details_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //判断1直播，0点播

        holder.mTvPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showTip(mData.get(position).getChannelName() + position);

                int type = mData.get(position).getType();
                if (type == VedioContants.Video) {//点播
                    intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(mData.get(position).getVodInfos()));
                    intent.putExtra(VedioContants.PlayType, VedioContants.Video);
                } else if (type == VedioContants.Living) {//直播
                    intent = new Intent(context, PlayActivity.class);
                    intent.putExtra(VedioContants.PlayUrl, mData.get(position).getRtmpDownstreamAddress());
                    intent.putExtra(VedioContants.PlayType, VedioContants.Living);
                }
                intent.putExtra("desc", mData.get(position).getChannelName());
                intent.putExtra("vid", mData.get(position).getId());

                //判断视频类型
                int isall = mData.get(position).getIsall();
                if (isall == VedioContants.TWO_D_VEDIO) {//2D
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
                } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
                } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
                } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
                }

                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(HttpURL.IV_HOST + mData.get(position).getHead())
                .crossFade()
                .into(holder.mIvRoomHead);
        holder.mTvChannelName.setText(mData.get(position).getChannelName());

        int price = mData.get(position).getPrice();
        if (price != 0) {
            holder.mTvRoomPay.setVisibility(View.VISIBLE);
        } else {
            holder.mTvRoomPay.setVisibility(View.GONE);
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

        MyViewHolder(View view) {
            super(view);
            mTvPlayer = (TextView) view.findViewById(R.id.title);
            mTvPersonName = (TextView) view.findViewById(R.id.tv_room_person_name);
            mTvChannelName = (TextView) view.findViewById(R.id.tv_play_channelName);
            mTvRoomPay = (TextView) view.findViewById(R.id.tv_pay);
            mIvRoomHead = (ImageView) view.findViewById(R.id.iv_room_head);
        }
    }

}
