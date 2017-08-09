package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.main.home.bean.RecommendBean;
import com.hy.vrfrog.ui.XCRoundRectImageView;

import java.util.ArrayList;

/**
 * Created by qwe on 2017/8/8.
 */

public class RecommendOneAdapter  extends RecyclerView.Adapter<RecommendOneAdapter.RecommendOneAdapterViewHolder>{
    private Context mContext;
    private ArrayList<RecommendBean>mList;

    public RecommendOneAdapter(Context context, ArrayList<RecommendBean> mLiveData) {
        this.mContext = context;
        this.mList = mLiveData;
    }
    @Override
    public RecommendOneAdapter.RecommendOneAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.re_video_item_main, parent, false);
        return new RecommendOneAdapter.RecommendOneAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendOneAdapter.RecommendOneAdapterViewHolder holder, int position) {
        holder.mTvVideoDesc.setText(mList.get(position).getTitle());
        holder.mivVideoImg.setImageResource(R.mipmap.img1);

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class RecommendOneAdapterViewHolder extends RecyclerView.ViewHolder {

        private XCRoundRectImageView mivVideoImg;
        private TextView mTvVideoDesc;
        private TextView mTvVideoTime;


        public RecommendOneAdapterViewHolder(View itemView) {
            super(itemView);

            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_video_img);
            mTvVideoDesc = (TextView) itemView.findViewById(R.id.tv_video_desc);
            mTvVideoTime = (TextView) itemView.findViewById(R.id.tv_video_time);

        }
    }
}
