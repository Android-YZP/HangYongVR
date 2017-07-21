package com.jt.base.videos.adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.GetHistoryBean;
import com.jt.base.personal.HistoryActivity;
import com.jt.base.ui.XCRoundRectImageView;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.Definition;
import com.jt.base.vrplayer.PlayActivity;
import com.jt.base.vrplayer.VideoPlayActivity;
import org.xutils.common.util.LogUtil;
import java.util.List;


/**
 * Created by wzq930102 on 2017/7/17.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryReViewHolder> implements View.OnLongClickListener {
    public HistoryActivity context;
    private List<GetHistoryBean.ResultBean> seeHistory;


    /************************************设置点击事件********************************************************/
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
        return true;
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public HistoryListAdapter(HistoryActivity context, List<GetHistoryBean.ResultBean> seeHistory) {
        this.context = context;
        this.seeHistory = seeHistory;
    }

    @Override
    public HistoryReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_history_item, parent, false);

        return new HistoryReViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryReViewHolder holder, final int position) {

        holder.mTvtitle.setText(seeHistory.get(position).getChannelName());
        if (seeHistory.get(position).getType() == VedioContants.Video) {
            holder.mTvpersent.setText("已观看至" + seeHistory.get(position).getWatchTime() + "%");
            holder.mTvpersent.setVisibility(View.VISIBLE);
        } else if (seeHistory.get(position).getType() == VedioContants.Living) {
            holder.mTvpersent.setVisibility(View.GONE);
        }


        Glide.with(context)
                .load(HttpURL.IV_HOST + seeHistory.get(position).getImg1())
                .asBitmap()
                .into(holder.mIvImg);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断1直播，0点播
                int type = seeHistory.get(position).getType();
                if (type == VedioContants.Video) {//点播
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra("desc", seeHistory.get(position).getChannelName());
                    intent.putExtra(VedioContants.PlayType, VedioContants.Video);
                    intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(seeHistory.get(position).getVodInfos()));
                    intent.putExtra("position", seeHistory.get(position).getWatchTime());
                    intent.putExtra("vid", seeHistory.get(position).getId());
                    //判断视频类型
                    int isall = seeHistory.get(position).getIsall();
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
                        context.startActivityForResult(intent, position);
                    } else {
                        UIUtils.showTip("请连接网络");
                    }
                } else if (type == VedioContants.Living) {//直播
                    goToPlay(position);
                }
            }
        });
        holder.layout.setTag(position);
        holder.layout.setOnLongClickListener(this);
    }

    private void goToPlay(int position) {
        Intent i = new Intent(context, PlayActivity.class);
        int isall = seeHistory.get(position).getIsall();
        if (isall == VedioContants.TWO_D_VEDIO) {
            i.putExtra(Definition.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
        } else if (isall == VedioContants.ALL_VIEW_VEDIO) {
            i.putExtra(Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
        }
        LogUtil.i(seeHistory.get(position).getRtmpDownstreamAddress() + "");
        i.putExtra(VedioContants.PlayUrl, seeHistory.get(position).getRtmpDownstreamAddress() + "");
        i.putExtra(VedioContants.KEY_PLAY_HEAD, HttpURL.IV_HOST + seeHistory.get(position).getHead() + "");
        i.putExtra(VedioContants.KEY_PLAY_USERNAME, seeHistory.get(position).getUsername() + "");
        i.putExtra(VedioContants.KEY_PLAY_ID, seeHistory.get(position).getId() + "");
        if (NetUtil.isOpenNetwork()) {
            context.startActivity(i);
        } else {
            UIUtils.showTip("请连接网络");
        }
    }


    @Override
    public int getItemCount() {
        return seeHistory.size();
    }

    public class HistoryReViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layout;
        private final XCRoundRectImageView mIvImg;
        private final TextView mTvtitle;
        private final TextView mTvpersent;

        public HistoryReViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_history_list);
            mIvImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_history_img);
            mTvtitle = (TextView) itemView.findViewById(R.id.tv_history_title);
            mTvpersent = (TextView) itemView.findViewById(R.id.tv_history_persent);
        }
    }


}
