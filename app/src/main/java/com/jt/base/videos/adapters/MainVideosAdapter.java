package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jt.base.R;
import com.jt.base.ui.XCRoundRectImageView;

import java.util.List;

/**
 * Created by Smith on 2017/7/4.
 */

public class MainVideosAdapter extends RecyclerView.Adapter<MainVideosAdapter.ListHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合
    private List<String> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;
    private Context context;
    private RecyclerView mRecycler;
    //构造函数


    public MainVideosAdapter(Context context, RecyclerView mRecycler) {
        this.context = context;
        this.mRecycler = mRecycler;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }


    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new MainVideosAdapter.ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new MainVideosAdapter.ListHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_video_item_main, parent, false);
        return new MainVideosAdapter.ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        //图片
        if (position < getItemCount() - 2) {
            Glide.with(context)
                    .load("https://raw.githubusercontent.com/Android-YZP/HelloTrace/master/cxzczx.jpg")
                    .asBitmap()
                    .into(holder.mivVideoImg);
        } else if (position == getItemCount() - 1) {//脚布局
        }
    }

    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder {
        private XCRoundRectImageView mivVideoImg;

        public ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            mivVideoImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_video_img);
        }
    }


    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return 20;
        } else if (mHeaderView == null && mFooterView != null) {
            return 20;
        } else if (mHeaderView != null && mFooterView == null) {
            return 20;
        } else {
            return 20;
        }
    }

}
