package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.ui.XCRoundRectImageView;

import java.util.ArrayList;

/**
 * Created by wzq930102 on 2017/8/9.
 */
public class EnterpriseOnLiveAdapter extends RecyclerView.Adapter<EnterpriseOnLiveAdapter.Enterprise1LiveHolder> {

    private Context mContext;
    private ArrayList<GetLiveHomeBean> mList;

    public EnterpriseOnLiveAdapter(Context context, ArrayList<GetLiveHomeBean> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public Enterprise1LiveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_enterprise, parent, false);
        return new EnterpriseOnLiveAdapter.Enterprise1LiveHolder(view);
    }

    @Override
    public void onBindViewHolder(Enterprise1LiveHolder holder, int position) {
           holder.linear.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Enterprise1LiveHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linear;
        private final XCRoundRectImageView mIvImg;
        private final TextView mEnterTitleTv;
//        private final TextView mEnterTvNum;
        private final TextView mEnterZhiBoTv;
        private final TextView mRnterpriseLiveTvName;
        private final ImageView mRnterprise1LiveIv;


        public Enterprise1LiveHolder(View itemView) {
            super(itemView);
            linear = (LinearLayout) itemView.findViewById(R.id.ll_enter_title);
            mIvImg = (XCRoundRectImageView) itemView.findViewById(R.id.xri_enter_img);
            mEnterTitleTv = (TextView) itemView.findViewById(R.id.tv_enter_home_title);
//            mEnterTvNum = (TextView) itemView.findViewById(R.id.tv_enter_home_people_number);
            mEnterZhiBoTv = (TextView) itemView.findViewById(R.id.tv_enter_home_live_state);
            mRnterpriseLiveTvName = (TextView) itemView.findViewById(R.id.tv_enter_home_name);
            mRnterprise1LiveIv = (ImageView) itemView.findViewById(R.id.img_enter_home_play_state);

        }
    }
}