package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;

import java.util.ArrayList;

/**
 * Created by qwe on 2017/8/8.
 */

public class EnterpriseLiveAdapter extends RecyclerView.Adapter<EnterpriseLiveAdapter.EnterPriseLiveHomeAdapterHolder>{
    private Context mContext;
    private ArrayList<GetLiveHomeBean> mLiveData;

    public EnterpriseLiveAdapter(Context context, ArrayList<GetLiveHomeBean> mLiveData) {
        this.mContext = context;
        this.mLiveData = mLiveData;
    }
    @Override
    public EnterpriseLiveAdapter.EnterPriseLiveHomeAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_live_home, parent, false);
        return new EnterpriseLiveAdapter.EnterPriseLiveHomeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(EnterpriseLiveAdapter.EnterPriseLiveHomeAdapterHolder holder, int position) {
        holder.mLiveHomeTitleTv.setText(mLiveData.get(position).getTitle());
        holder.mLiveHomeHeadNameTv.setText(mLiveData.get(position).getName());
        holder.mLiveHomePlayStateTv.setText(mLiveData.get(position).getPlayState());
        holder.mLiveHomePeopleNumberTv.setText(mLiveData.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mLiveData.size();
    }

    public class EnterPriseLiveHomeAdapterHolder extends RecyclerView.ViewHolder {

        private TextView mLiveHomeTitleTv;
        private TextView mLiveHomePeopleNumberTv;
        private TextView mLiveHomePlayStateTv;
        private TextView mLiveHomeHeadNameTv;
        private ImageView mLiveHomePlayStateImg;
        private ImageView mLiveHomeHeadImg;


        public EnterPriseLiveHomeAdapterHolder(View itemView) {
            super(itemView);

            mLiveHomeTitleTv  = (TextView)itemView.findViewById(R.id.tv_live_home_title);
            mLiveHomePeopleNumberTv = (TextView)itemView.findViewById(R.id.tv_live_home_people_number);
            mLiveHomePlayStateTv = (TextView)itemView.findViewById(R.id.tv_live_home_live_state);
            mLiveHomeHeadNameTv = (TextView)itemView.findViewById(R.id.tv_live_home_name);

            mLiveHomePlayStateImg = (ImageView)itemView.findViewById(R.id.img_live_home_play_state);
            mLiveHomeHeadImg = (ImageView)itemView.findViewById(R.id.img_live_home_head);

        }
    }
}
