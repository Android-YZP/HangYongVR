package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jt.base.R;

/**
 * Created by m1762 on 2017/6/8.
 */

public class MainVideoListAdapter extends RecyclerView.Adapter<MainVideoListAdapter.MainReViewHolder> {
    public Context context;
    private RecyclerView recyclerView;

    public MainVideoListAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public MainReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.re_video_item_main, parent,
                false);
        MainReViewHolder holder = new MainReViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainReViewHolder holder, final int position) {
//        holder.mivVideoImg


    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MainReViewHolder extends RecyclerView.ViewHolder {
        private ImageView mivVideoImg;


        public MainReViewHolder(View itemView) {
            super(itemView);
            mivVideoImg = (ImageView) itemView.findViewById(R.id.iv_video_img);
        }
    }
}
