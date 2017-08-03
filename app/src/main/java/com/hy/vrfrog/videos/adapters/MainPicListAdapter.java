package com.hy.vrfrog.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.ui.XCRoundRectImageView;

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
//        XUtilsImageUtils.display(,"http://118.89.246.194:8080/head/ff601521-6c79-4a5f-9389-47ba8f09db28.jpg",10);
        Glide.with(context)
                .load("http://118.89.246.194:8080/head/ff601521-6c79-4a5f-9389-47ba8f09db28.jpg")
                .asBitmap()
                .into(holder.mivVideoImg);

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MainReViewHolder extends RecyclerView.ViewHolder {
        private XCRoundRectImageView mivVideoImg;


        public MainReViewHolder(View itemView) {
            super(itemView);
            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_video_img);
        }
    }
}
