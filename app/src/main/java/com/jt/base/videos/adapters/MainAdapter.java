package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.utils.UIUtils;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * Created by Smith on 2017/6/29.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合
    private List<String> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;
    private Context context;
    private SwipyRefreshLayout mRecyclerfreshLayout;
    private RecyclerView mRecycler;
    //构造函数


    public MainAdapter(Context context, SwipyRefreshLayout mRecyclerfreshLayout, RecyclerView mRecycler) {
        this.mRecyclerfreshLayout = mRecyclerfreshLayout;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.reitem_main, parent, false);
        return new ListHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ListHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ((ListHolder) holder).mRvVideoList.setLayoutManager(linearLayoutManager);
                ((ListHolder) holder).mRvVideoList.setAdapter(new MainVideoListAdapter(context, ((ListHolder) holder).mRvVideoList));

                //修复滑动事件的冲突
                ((ListHolder) holder).mRvVideoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        //
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {//滑动停止后
                            mRecyclerfreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
                        } else {
                            mRecyclerfreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
                            //解决滑动到底部的事件冲突
                            LinearLayoutManager manager = (LinearLayoutManager) mRecycler.getLayoutManager();
                            //获取最后一个完全显示的ItemPosition ,角标值
                            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                            //所有条目,数量值
                            int totalItemCount = manager.getItemCount();
                            // 判断是否滚动到底部
                            if (lastVisibleItem == (totalItemCount - 1)) {
                                mRecyclerfreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
                            }
                        }
                    }
                });

                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {

            return;
        } else {
            return;
        }
    }

    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder {
        RecyclerView mRvVideoList;

        public ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            mRvVideoList = (RecyclerView) itemView.findViewById(R.id.rv_video_list);
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return 3;
        } else if (mHeaderView == null && mFooterView != null) {
            return 4;
        } else if (mHeaderView != null && mFooterView == null) {
            return 5;
        } else {
            return 5;
        }
    }


}
