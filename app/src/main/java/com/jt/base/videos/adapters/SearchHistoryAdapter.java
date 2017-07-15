package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jt.base.R;
import com.jt.base.ui.XCRoundRectImageView;

/**
 * Created by m1762 on 2017/6/8.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MainReViewHolder> {
    public Context context;

    public SearchHistoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MainReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.search_history_item, parent,
                false);
        MainReViewHolder holder = new MainReViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainReViewHolder holder, final int position) {
        holder.mTvSearchItem.setText("测试数据");
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class MainReViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvSearchItem;


        public MainReViewHolder(View itemView) {
            super(itemView);
            mTvSearchItem = (TextView) itemView.findViewById(R.id.tv_search_item);
        }
    }
}
