package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.jt.base.R;

/**
 * Created by m1762 on 2017/6/8.
 */

public class MainReAdapter extends RecyclerView.Adapter<MainReAdapter.MainReViewHolder> {
    public Context context;
    private RecyclerView recyclerView;
    private HorizontalScrollView mScrollView;
    private LinearLayout mLlRoot;

    public MainReAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public MainReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.reitem_main, parent,
                false);
        MainReViewHolder holder = new MainReViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainReViewHolder holder, final int position) {
        holder.tvTest.setText("图" + position);
        /**
         * 局部点击事件
         */
        holder.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.mLlRoot.removeAllViews();
        for (int i = 0; i < 2; i++) {
            ImageView imageView = new ImageView(context);
            Glide.with(context)
                    .load("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg")
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
                }
            });
            holder.mLlRoot.addView(imageView);
        }


    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MainReViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTest;
        private LinearLayout mLlRoot;

        public MainReViewHolder(View itemView) {
            super(itemView);
            tvTest = (TextView) itemView.findViewById(R.id.tv_test);
            mScrollView = (HorizontalScrollView) itemView.findViewById(R.id.sv_test);
            mLlRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);

        }
    }
}
