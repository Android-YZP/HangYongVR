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
 * Created by qwe on 2017/8/3.
 */

public class LiveHomeAdapter  extends RecyclerView.Adapter<LiveHomeAdapter.LiveHomeAdapterHolder>{

    private Context mContext;
    private ArrayList<GetLiveHomeBean> mLiveData;

    public LiveHomeAdapter(Context context, ArrayList<GetLiveHomeBean> mLiveData) {
        this.mContext = context;
        this.mLiveData = mLiveData;
    }
    @Override
    public LiveHomeAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_live_home, parent, false);
        return new LiveHomeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveHomeAdapterHolder holder, int position) {
       holder.mLiveHomeTitleTv.setText(mLiveData.get(position).getTitle());
       holder.mLiveHomeHeadNameTv.setText(mLiveData.get(position).getName());
       holder.mLiveHomePlayStateTv.setText(mLiveData.get(position).getPlayState());
       holder.mLiveHomePeopleNumberTv.setText(mLiveData.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mLiveData.size();
    }

    public class LiveHomeAdapterHolder extends RecyclerView.ViewHolder {

        private TextView mLiveHomeTitleTv;
        private TextView mLiveHomePeopleNumberTv;
        private TextView mLiveHomePlayStateTv;
        private TextView mLiveHomeHeadNameTv;
        private ImageView mLiveHomePlayStateImg;
        private ImageView mLiveHomeHeadImg;


        public LiveHomeAdapterHolder(View itemView) {
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
