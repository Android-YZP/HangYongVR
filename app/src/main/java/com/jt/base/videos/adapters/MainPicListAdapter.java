package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jt.base.R;

/**
 * Created by m1762 on 2017/6/8.
 */

public class MainPicListAdapter extends RecyclerView.Adapter<MainPicListAdapter.MainReViewHolder> {
    public Context context;

    public MainPicListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MainReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.re_pic_item_main, parent,
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
