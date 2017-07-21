package com.jt.base.videos.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.GetHistoryBean;
import com.jt.base.personal.HistoryActivity;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.VideoPlayActivity;
import java.util.List;


/**
 * Created by wzq930102 on 2017/7/17.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryReViewHolder> implements View.OnLongClickListener{
    public HistoryActivity context;
    private List<GetHistoryBean.ResultBean> seeHistory;


    /************************************设置点击事件********************************************************/
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
        return true;
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



    public HistoryListAdapter(HistoryActivity context, List<GetHistoryBean.ResultBean> seeHistory) {
        this.context = context;
        this.seeHistory = seeHistory;
    }

    @Override
    public HistoryReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_history_item, parent, false);

        return new HistoryReViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryReViewHolder holder, final int position) {

        holder.mTvtitle.setText(seeHistory.get(position).getChannelName());
        holder.mTvpersent.setText("以观看至"+seeHistory.get(position).getWatchTime() + "%");

        Glide.with(context)
                .load(HttpURL.IV_HOST + seeHistory.get(position).getImg1())
                .asBitmap()
                .into(holder.mIvImg);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("desc", seeHistory.get(position).getChannelName());
                intent.putExtra(VedioContants.PlayType, VedioContants.Video);
                intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(seeHistory.get(position).getVodInfos()));
                intent.putExtra("position", seeHistory.get(position).getWatchTime());
                intent.putExtra("vid", seeHistory.get(position).getId());
                //判断视频类型
                int isall = seeHistory.get(position).getIsall();
                if (isall == VedioContants.TWO_D_VEDIO) {//2D
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
                } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
                } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
                } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
                }
                context.startActivityForResult(intent,position);
            }
        });

        holder.layout.setTag(position);
        holder.layout.setOnLongClickListener(this);
    }

    @Override
    public int getItemCount() {
        return seeHistory.size();
    }

    public class HistoryReViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layout;
        private final ImageView mIvImg;
        private final TextView mTvtitle;
        private final TextView mTvpersent;

        public HistoryReViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_history_list);
            mIvImg = (ImageView) itemView.findViewById(R.id.iv_history_img);
            mTvtitle = (TextView) itemView.findViewById(R.id.tv_history_title);
            mTvpersent = (TextView) itemView.findViewById(R.id.tv_history_persent);
        }
    }




}
