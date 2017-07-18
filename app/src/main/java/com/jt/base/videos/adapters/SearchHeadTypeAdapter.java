package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.SearchTopicBean;
import com.jt.base.ui.XCRoundRectImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1762 on 2017/6/8.
 */

public class SearchHeadTypeAdapter extends RecyclerView.Adapter<SearchHeadTypeAdapter.MainReViewHolder> {
    public Context context;
    public List<SearchTopicBean.ResultBean> mSearchResult ;

    public SearchHeadTypeAdapter(Context context, List<SearchTopicBean.ResultBean> mSearchResult) {
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
