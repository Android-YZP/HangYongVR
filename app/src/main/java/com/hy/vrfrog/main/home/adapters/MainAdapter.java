package com.hy.vrfrog.main.home.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.TopicBean;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.main.home.activitys.VideoListActivity;

import org.xutils.common.util.LogUtil;

import java.util.List;
import java.util.Random;

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
    private Activity context;
    private SwipeRefreshLayout mRecyclerfreshLayout;
    private RecyclerView mRecycler;
    private ViewPager mViewpager;
    private TextView mTvMore1;
    private List<TopicBean.ResultBeanX> topicBean;
    //构造函数

    public MainAdapter(Activity context, SwipeRefreshLayout mRecyclerfreshLayout, RecyclerView mRecycler, ViewPager mViewpager, List<TopicBean.ResultBeanX> topicBean) {
        this.mRecyclerfreshLayout = mRecyclerfreshLayout;
        this.context = context;
        this.mRecycler = mRecycler;
        this.mViewpager = mViewpager;
        this.topicBean = topicBean;
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
        if (position == 0 && mHeaderView != null) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1 && mFooterView != null) {
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ListHolder) {

                if (position == 0){
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    ((ListHolder) holder).mRvVideoList.setNestedScrollingEnabled(false);
                    ((ListHolder) holder).mRvVideoList.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    MainOneAdapter mainVideosAdapter = new MainOneAdapter(context, topicBean.get(position).getResult(), topicBean.get(position).getCode());
                    ((ListHolder) holder).mRvVideoList.setAdapter(mainVideosAdapter);
                }else {
                    int type = UIUtils.typeRandom(3);

                    LogUtil.i("type = " +  type);
//
                    if ( type== 0){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        ((ListHolder) holder).mRvVideoList.setNestedScrollingEnabled(false);
                        ((ListHolder) holder).mRvVideoList.setLayoutManager(linearLayoutManager);
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        MainOneAdapter mainVideosAdapter = new MainOneAdapter(context,topicBean.get(position).getResult(), topicBean.get(position).getCode());
                        ((ListHolder) holder).mRvVideoList.setAdapter(mainVideosAdapter);

                    }else if (type == 1){

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        ((ListHolder) holder).mRvVideoList.setNestedScrollingEnabled(false);
                        ((ListHolder) holder).mRvVideoList.setLayoutManager(linearLayoutManager);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        MainVideosOneAdapter mainVideosAdapter = new MainVideosOneAdapter(context, mViewpager, topicBean.get(position).getResult(), topicBean.get(position).getCode(),type);
                        ((ListHolder) holder).mRvVideoList.setAdapter(mainVideosAdapter);


                    }else  if (type == 2){

                        int typeOne = new Random().nextInt(2) ;

                        if (topicBean.get(position).getResult().size() >= 3){
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            ((ListHolder) holder).mRvVideoList.setNestedScrollingEnabled(false);
                            ((ListHolder) holder).mRvVideoList.setLayoutManager(linearLayoutManager);
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            MainVideosAdapter mainVideosAdapter = new MainVideosAdapter(context, mViewpager, topicBean.get(position).getResult(), topicBean.get(position).getCode(),type);
                            ((ListHolder) holder).mRvVideoList.setAdapter(mainVideosAdapter);
                        }else {

                            if (type == 0){
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                ((ListHolder) holder).mRvVideoList.setNestedScrollingEnabled(false);
                                ((ListHolder) holder).mRvVideoList.setLayoutManager(linearLayoutManager);
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                MainOneAdapter mainVideosAdapter = new MainOneAdapter(context,topicBean.get(position).getResult(), topicBean.get(position).getCode());
                                ((ListHolder) holder).mRvVideoList.setAdapter(mainVideosAdapter);
                            }else {
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                ((ListHolder) holder).mRvVideoList.setNestedScrollingEnabled(false);
                                ((ListHolder) holder).mRvVideoList.setLayoutManager(linearLayoutManager);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                MainVideosOneAdapter mainVideosAdapter = new MainVideosOneAdapter(context, mViewpager, topicBean.get(position).getResult(), topicBean.get(position).getCode(),type);
                                ((ListHolder) holder).mRvVideoList.setAdapter(mainVideosAdapter);
                            }

                        }

                    }
                }


                ((ListHolder) holder).mTvTopicTitle.setText(topicBean.get(position).getMsg());
                ((ListHolder) holder).mTvTotalVideos.setText(topicBean.get(position).getPage().getTotal() + "个视频");

                if (topicBean.get(position).getResult().get(0).getTypeName() != null){
                    ((ListHolder) holder).mSortMessageTv.setText((String)topicBean.get(position).getResult().get(0).getTypeName());
                }

                ((ListHolder) holder).mRlMainMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, VideoListActivity.class);
                        intent.putExtra(VedioContants.TopicId, topicBean.get(position).getCode());
                        intent.putExtra(VedioContants.TopicTitle, topicBean.get(position).getMsg());
                        context.startActivity(intent);
                        context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
                    }
                });

//                if (topicBean.get(position).getResult().size() <= 7) {
//                    View noview = View.inflate(context, R.layout.main_list_item_foot_no_view, null);
//                    mainVideosAdapter.setFooterView(noview);
//                }else {
//                    View v = View.inflate(context, R.layout.main_list_item_foot_view, null);
//                    mTvMore1 = (TextView) v.findViewById(R.id.tv_main_more);
//                    mTvMore1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(context, VideoListActivity.class);
//                            intent.putExtra(VedioContants.TopicId, topicBean.get(position).getCode());
//                            context.startActivity(intent);
//                            context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
//                        }
//                    });
//                    mainVideosAdapter.setFooterView(v);
//                }


                //修复滑动事件的冲突
                ((ListHolder) holder).mRvVideoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {//滑动停止后
                            mRecyclerfreshLayout.setEnabled(true);
                        } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            mRecyclerfreshLayout.setEnabled(false);
                        }
                    }
                });

                ((ListHolder) holder).mRvVideoList.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                            default:
                        }
                        return false;
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
        private TextView mTvTopicTitle;
        private RelativeLayout mRlMainMore;
        private TextView mTvTotalVideos;
        private TextView mSortMessageTv;

        public ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            mRvVideoList = (RecyclerView) itemView.findViewById(R.id.rv_video_sort_list);
            mTvTopicTitle = (TextView) itemView.findViewById(R.id.tv_video_sort_title);
            mRlMainMore = (RelativeLayout) itemView.findViewById(R.id.rl_main_sort_more);
            mTvTotalVideos = (TextView) itemView.findViewById(R.id.tv_video_sort_num);
            mSortMessageTv = (TextView)itemView.findViewById(R.id.tv_video_message);

        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return topicBean.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return topicBean.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return topicBean.size() + 1;
        } else {
            return topicBean.size() + 2;
        }
    }


}
