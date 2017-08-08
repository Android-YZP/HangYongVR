package com.hy.vrfrog.main.home.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.utils.TimeUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.main.home.activitys.VideoListActivity;

import java.util.List;

/**
 * Created by Smith on 2017/6/29.
 */

public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合
    private List<String> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;
    private VideoListActivity context;
    private List<VodbyTopicBean.ResultBean> topicBean;
    Intent intent;
    //构造函数

    public VideoListAdapter(VideoListActivity context, List<VodbyTopicBean.ResultBean> topicBean) {
        this.context = context;
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
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0 && mHeaderView != null) {
            //diyi一个,应该加载head
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

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new ListHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        intent = new Intent(context, VideoDetialActivity.class);
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ListHolder) {

                Glide.with(context)
                        .load(HttpURL.IV_HOST + topicBean.get(position - 1).getImg1())
                        .asBitmap()
                        .into(((ListHolder) holder).mXuImg);

                ((ListHolder) holder).mTvVideoDesc.setText(topicBean.get(position - 1).getChannelName());

                ((ListHolder) holder).mXuImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra(VedioContants.Position, position - 1);//哪个话题
                        intent.putExtra(VedioContants.TopicId, topicBean.get(position - 1).getTopicId());//哪个话题
                        context.startActivity(intent);
                    }
                });

                if (topicBean.get(position - 1).getType() == VedioContants.Video){
                    if (topicBean.get(position - 1).getTime() != null)
                        ((ListHolder) holder).mTvVideoTime.setText(TimeUtils.generateTime(Integer.parseInt((String) topicBean.get(position - 1).getTime())));//设置时间
                }else {
                    ((ListHolder) holder).mTvVideoTime.setVisibility(View.GONE);
                }

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


        private ImageView mXuImg;
        private TextView mTvVideoDesc;
        private TextView mTvVideoTime;

        public ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            mXuImg = (ImageView) itemView.findViewById(R.id.tv_video_list_img);
            mTvVideoDesc = (TextView) itemView.findViewById(R.id.tv_video_desc);
            mTvVideoTime = (TextView) itemView.findViewById(R.id.tv_video_time);
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
