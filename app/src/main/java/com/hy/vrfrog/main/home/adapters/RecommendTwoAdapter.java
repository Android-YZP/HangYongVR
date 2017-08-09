package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.main.home.bean.RecommendBean;
import com.hy.vrfrog.ui.XCRoundRectImageView;

import java.util.ArrayList;

/**
 * Created by qwe on 2017/8/8.
 */

public class RecommendTwoAdapter extends RecyclerView.Adapter<RecommendTwoAdapter.RecommendOneAdapterViewHolder>{
    private Context mContext;
    private ArrayList<RecommendBean>mList;

    public RecommendTwoAdapter(Context context, ArrayList<RecommendBean> mLiveData) {
        this.mContext = context;
        this.mList = mLiveData;
    }
    @Override
    public RecommendTwoAdapter.RecommendOneAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_two_adapter, parent, false);
        return new RecommendTwoAdapter.RecommendOneAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendTwoAdapter.RecommendOneAdapterViewHolder holder, int position) {
        holder.mTvVideoTitle.setText(mList.get(position).getTitle());
        holder.mivVideoImg.setImageResource(R.mipmap.img5);
        holder.mTvVideoDesc.setText(mList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class RecommendOneAdapterViewHolder extends RecyclerView.ViewHolder {

        private XCRoundRectImageView mivVideoImg;
        private TextView mTvVideoDesc;
        private TextView mTvVideoTitle;


        public RecommendOneAdapterViewHolder(View itemView) {
            super(itemView);

            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.img_recommend_adapter_two);
            mTvVideoDesc = (TextView) itemView.findViewById(R.id.tv_recommend_adapter_two_message);
            mTvVideoTitle = (TextView) itemView.findViewById(R.id.tv_recommend_adapter_two_title);

        }
    }
}
