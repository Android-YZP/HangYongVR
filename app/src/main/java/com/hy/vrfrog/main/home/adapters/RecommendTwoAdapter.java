package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.RecommendBean;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.utils.TimeUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2017/8/8.
 */

public class RecommendTwoAdapter extends RecyclerView.Adapter<RecommendTwoAdapter.RecommendOneAdapterViewHolder>{
    private Context mContext;
    private int id;
    private List<RecommendBean.ResultBeanX.ResultBean> mList;

    public RecommendTwoAdapter(Context context, List<RecommendBean.ResultBeanX.ResultBean> mLiveData, int id) {
        this.mContext = context;
        this.mList = mLiveData;
        this.id = id;
    }
    @Override
    public RecommendTwoAdapter.RecommendOneAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_two_adapter, parent, false);
        return new RecommendTwoAdapter.RecommendOneAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendTwoAdapter.RecommendOneAdapterViewHolder holder, final int position) {
            holder.mTvVideoTitle.setText((String)mList.get(position ).getChannelName());
            holder.mTvVideoDesc.setText((String)mList.get(position ).getIntroduce());
            Glide.with(mContext).load(HttpURL.IV_HOST+mList.get(position).getImg()).asBitmap().into(holder.mivVideoImg);
            holder.mTvVideoTime.setText(TimeUtils.generateTime(Integer.parseInt((String) mList.get(position ).getTime())));


        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VideoDetialActivity.class);
                intent.putExtra(VedioContants.Position,position);
                intent.putExtra(VedioContants.TopicId,id);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList.size() > 3){
            return 3;
        }else {
            return mList.size();
        }

    }

    public class RecommendOneAdapterViewHolder extends RecyclerView.ViewHolder {

        private XCRoundRectImageView mivVideoImg;
        private TextView mTvVideoDesc;
        private TextView mTvVideoTitle;
        private TextView mTvVideoTime;
        private LinearLayout mLinearLayout;


        public RecommendOneAdapterViewHolder(View itemView) {
            super(itemView);

            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.img_recommend_adapter_two);
            mTvVideoDesc = (TextView) itemView.findViewById(R.id.tv_recommend_adapter_two_message);
            mTvVideoTitle = (TextView) itemView.findViewById(R.id.tv_recommend_adapter_two_title);
            mTvVideoTime = (TextView)itemView.findViewById(R.id.tv_video_two_time);
            mLinearLayout = (LinearLayout)itemView.findViewById(R.id.ll_recommend_introduce);

        }
    }
}
