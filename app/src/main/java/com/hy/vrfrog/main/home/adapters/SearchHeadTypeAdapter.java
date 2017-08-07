package com.hy.vrfrog.main.home.adapters;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.SearchTopicBean;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.main.home.activitys.VideoListActivity;

import java.util.List;

/**
 * Created by m1762 on 2017/6/8.
 */

public class SearchHeadTypeAdapter extends RecyclerView.Adapter<SearchHeadTypeAdapter.MainReViewHolder> {
    public FragmentActivity context;
    public List<SearchTopicBean.ResultBean> mSearchResult ;

    public SearchHeadTypeAdapter(FragmentActivity context, List<SearchTopicBean.ResultBean> mSearchResult) {
        this.context = context;
        this.mSearchResult = mSearchResult;
    }

    @Override
    public MainReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.search_head_type_item, parent,
                false);
        MainReViewHolder holder = new MainReViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainReViewHolder holder, final int position) {

        holder.mSearchTopicDesc.setText(mSearchResult.get(position).getName());

        Glide.with(context)
                .load(HttpURL.IV_HOST + mSearchResult.get(position).getImg())
                .asBitmap()
                .into(holder.mivVideoImg);

        holder.mivVideoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoListActivity.class);
                intent.putExtra(VedioContants.TopicId, mSearchResult.get(position).getId());//哪个话题
                intent.putExtra(VedioContants.TopicTitle,mSearchResult.get(position).getName());
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSearchResult.size();
    }


    public class MainReViewHolder extends RecyclerView.ViewHolder {
        private XCRoundRectImageView mivVideoImg;
        private TextView mSearchTopicDesc;

        public MainReViewHolder(View itemView) {
            super(itemView);
            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_video_img);
            mSearchTopicDesc = (TextView) itemView.findViewById(R.id.tv_video_desc);
        }
    }
}
