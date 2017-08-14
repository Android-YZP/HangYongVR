package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.RecommendBean;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.main.home.activitys.VideoListActivity;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.utils.TimeUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2017/8/8.
 */

public class RecommendOneAdapter  extends RecyclerView.Adapter<RecommendOneAdapter.RecommendOneAdapterViewHolder>{
    private Context mContext;
    private List<RecommendBean.ResultBeanX.ResultBean> mList;
    private  int id;

    public RecommendOneAdapter(Context context, List<RecommendBean.ResultBeanX.ResultBean> mLiveData, int id) {
        this.mContext = context;
        this.mList = mLiveData;
        this.id = id ;
    }
    @Override
    public RecommendOneAdapter.RecommendOneAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.re_video_item_main, parent, false);
        return new RecommendOneAdapter.RecommendOneAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendOneAdapter.RecommendOneAdapterViewHolder holder, final int position) {

        holder.mivVideoImg.setImageResource(R.mipmap.img5);
        holder.mivVideoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VideoDetialActivity.class);
                intent.putExtra(VedioContants.Position,position);
                intent.putExtra(VedioContants.TopicId,id);
                mContext.startActivity(intent);
            }
        });
        holder.mTvVideoDesc.setText((String)mList.get(position ).getChannelName());
        Glide.with(mContext).load(HttpURL.IV_HOST+mList.get(position).getImg()).asBitmap().into(holder.mivVideoImg);
        holder.mTvVideoTime.setText(TimeUtils.generateTime(Integer.parseInt(mList.get(position ).getTime())));

    }

    @Override
    public int getItemCount() {
        if (mList.size() > 7 ){
            return 7;
        }else {
            return mList.size();
        }

    }

    public class RecommendOneAdapterViewHolder extends RecyclerView.ViewHolder {

        private XCRoundRectImageView mivVideoImg;
        private TextView mTvVideoDesc;
        private TextView mTvVideoTime;


        public RecommendOneAdapterViewHolder(View itemView) {
            super(itemView);

            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_video_img);
            mTvVideoDesc = (TextView) itemView.findViewById(R.id.tv_video_desc);
            mTvVideoTime = (TextView) itemView.findViewById(R.id.tv_video_one_time);

        }
    }
}
