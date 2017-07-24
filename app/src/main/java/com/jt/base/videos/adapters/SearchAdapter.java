package com.jt.base.videos.adapters;

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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.SearchVideoBean;
import com.jt.base.http.responsebean.TopicBean;
import com.jt.base.ui.XCRoundRectImageView;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.videos.activitys.VideoListActivity;
import com.jt.base.vrplayer.PlayActivity;
import com.jt.base.vrplayer.VideoPlayActivity;

import java.util.List;

/**
 * Created by Smith on 2017/6/29.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合
    private List<String> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;
    private Activity context;
    private List<SearchVideoBean.ResultBean> results;
    private Intent intent;
    //构造函数

    public SearchAdapter(Activity context, List<SearchVideoBean.ResultBean> results) {
        this.context = context;
        this.results = results;
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

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_rv_item, parent, false);
        return new ListHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ListHolder) {

                ((ListHolder) holder).mTvSearchVideoDesc.setText(results.get(position - 1).getChannelName());

                Glide.with(context)
                        .load(HttpURL.IV_HOST + results.get(position - 1).getImg1())
                        .asBitmap()
                        .into(((ListHolder) holder).mIvImg);

                ((ListHolder) holder).mIvImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int type = results.get(position - 1).getType();
                        if (type == VedioContants.Video) {//点播
                            intent = new Intent(context, VideoPlayActivity.class);
                            intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(results.get(position - 1).getVodInfos()));
                            intent.putExtra(VedioContants.PlayType, VedioContants.Video);
                            intent.putExtra("vid", results.get(position - 1).getId());
                            intent.putExtra("desc", results.get(position - 1).getChannelName());

                        } else if (type == VedioContants.Living) {//直播
                            intent = new Intent(context, PlayActivity.class);
                            intent.putExtra(VedioContants.PlayUrl, results.get(position - 1).getRtmpDownstreamAddress());
                            intent.putExtra(VedioContants.PlayType, VedioContants.Living);
                            intent.putExtra(VedioContants.KEY_PLAY_USERNAME, results.get(position - 1).getUsername() + "");
                            intent.putExtra(VedioContants.KEY_PLAY_ID, results.get(position - 1).getId() + "");
                        }


                        //判断视频类型
                        int isall = results.get(position - 1).getIsall();
                        if (isall == VedioContants.TWO_D_VEDIO) {//2D
                            intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
                        } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
                            intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
                        } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
                            intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
                        } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
                            intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
                        }
                        if (NetUtil.isOpenNetwork()) {
                            context.startActivity(intent);
                        } else {
                            UIUtils.showTip("请连接网络");
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

        private TextView mTvSearchVideoDesc;
        private XCRoundRectImageView mIvImg;

        public ListHolder(View itemView) {
            super(itemView);

            mTvSearchVideoDesc = (TextView) itemView.findViewById(R.id.search_video_desc);
            mIvImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_search_video_img);


            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }


        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return results.size() + 1;
        } else if (mHeaderView == null && mFooterView != null) {
            return results.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return results.size() + 1;
        } else {
            return results.size() + 1;
        }
    }


}
