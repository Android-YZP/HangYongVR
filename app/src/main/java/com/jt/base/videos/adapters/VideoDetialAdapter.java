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

import com.jt.base.R;
import com.jt.base.http.responsebean.TopicBean;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.VideoPlayActivity;

import java.util.List;

/**
 * Created by Smith on 2017/6/19.
 */

public class VideoDetialAdapter extends RecyclerView.Adapter<VideoDetialAdapter.MyViewHolder> {
    private Context context;
    private List<TopicBean.ResultBeanX.ResultBean> mResultBean;
    private AlertDialog show;

    public VideoDetialAdapter(Context context, List<TopicBean.ResultBeanX.ResultBean> mResultBean) {
        this.context = context;
        this.mResultBean = mResultBean;

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
        final Intent intent = new Intent(context, VideoPlayActivity.class);

        int type = mResultBean.get(position).getType();
        if (type == VedioContants.Video) {//点播
            intent.putExtra(VedioContants.PlayUrl, (String) mResultBean.get(position).getRtmpDownstreamAddress());
        } else if (type == VedioContants.Living) {//直播
            intent.putExtra(VedioContants.PlayUrl, (String) mResultBean.get(position).getRtmpDownstreamAddress());
        }


        //判断视频类型
        int isall = mResultBean.get(position).getIsall();
        if (isall == VedioContants.TWO_D_VEDIO) {//2D
            intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
        } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
            intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
        } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
            intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
        } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
            intent.putExtra(com.jt.base.vrplayer.Definition.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
        }

        holder.mTvPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBean.size();
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
