package com.jt.base.videoDetails.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.responsebean.GetRoomBean;
import com.jt.base.http.responsebean.ResourceBean;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.Definition;
import com.jt.base.vrplayer.PlayActivity;
import com.jt.base.vrplayer.VideoPlayActivity;
import com.jt.base.vrplayer.utils.SPUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Smith on 2017/6/19.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    private Context context;

    private List<ResourceBean.ResultBean> mRoomLists;
    private AlertDialog show;

    public RvAdapter(Context context, List<ResourceBean.ResultBean> mRoomLists) {
        this.context = context;
        this.mRoomLists = mRoomLists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_detail_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //判断1直播，0点播
        int type = mRoomLists.get(position).getType();
        if (type == VedioContants.Video) {//点播
            holder.mllRoomName.setVisibility(View.GONE);
            holder.mRlPersonName.setVisibility(View.GONE);

            holder.mTvPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入点播
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(mRoomLists.get(position).getVodInfos()));
                    intent.putExtra(VedioContants.PlayType, VedioContants.Video);

                    //判断视频类型
                    int isall = mRoomLists.get(position).getIsall();
                    if (isall == VedioContants.TWO_D_VEDIO) {//2D
                        intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
                    } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
                        intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
                    } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
                        intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
                    } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
                        intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
                    }
                    context.startActivity(intent);

                }
            });

        } else if (type == VedioContants.Living) {//直播
            holder.mllRoomName.setVisibility(View.VISIBLE);
            holder.mRlPersonName.setVisibility(View.VISIBLE);
            //加载圆形头像
            ImageOptions imageOptions = new ImageOptions.Builder().setCircular(true).build(); //淡入效果
            x.image().bind(holder.mIvRoomHead, HttpURL.IV_HOST + mRoomLists.get(position).getHead(), imageOptions, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    UIUtils.showTip("背景图片加载失败,请刷新重试");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
            holder.mTvPersonName.setText(mRoomLists.get(position).getUsername());
            holder.mTvChannelName.setText(mRoomLists.get(position).getChannelName());
            //是否付费
            if (mRoomLists.get(position).getPrice() == 0) {
                holder.mTvRoomPay.setVisibility(View.GONE);
            } else {
                holder.mTvRoomPay.setVisibility(View.VISIBLE);
            }
            holder.mTvPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRoomLists.get(position).getPrice() == 0) {
                        goToPlay(position);
                    } else {
                        payDialog(position);
                    }
                }
            });
        }


//        holder.mTvPlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int type = mData.get(position).getType();
//                if (type == VedioContants.Video) {//点播
//                    intent = new Intent(context, VideoPlayActivity.class);
//                    intent.putExtra(VedioContants.PlayUrl, new Gson().toJson(mData.get(position).getVodInfos()));
//                    intent.putExtra(VedioContants.PlayType, VedioContants.Video);
//                } else if (type == VedioContants.Living) {//直播
//                    intent = new Intent(context, PlayActivity.class);
//                    intent.putExtra(VedioContants.PlayUrl, mData.get(position).getRtmpDownstreamAddress());
//                    intent.putExtra(VedioContants.PlayType, VedioContants.Living);
//                }
//                intent.putExtra("desc", mData.get(position).getChannelName());
//                intent.putExtra("vid", mData.get(position).getId());
//
//                //判断视频类型
//                int isall = mData.get(position).getIsall();
//                if (isall == VedioContants.TWO_D_VEDIO) {//2D
//                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
//                } else if (isall == VedioContants.ALL_VIEW_VEDIO) {//全景
//                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
//                } else if (isall == VedioContants.THREE_D_VEDIO) {//3D
//                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.THREE_D_VEDIO);
//                } else if (isall == VedioContants.VR_VIEW_VEDIO) {//VR
//                    intent.putExtra(VedioContants.PLEAR_MODE, VedioContants.VR_VIEW_VEDIO);
//                }
//
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mRoomLists.size();
    }


    /**
     * 对话框
     *
     * @param position
     */

    private void payDialog(final int position) {
//        final Builder mPayDialog = new Builder(context, R.style.MyDialogStyle);
        final AlertDialog.Builder mPayDialog = new AlertDialog.Builder(context, R.style.MyDialogStyle);

        final View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_pay_item, null);
        ImageView ivPayChacha = (ImageView) dialogView.findViewById(R.id.iv_pay_chacha);
        TextView TvPayDiaprice = (TextView) dialogView.findViewById(R.id.tv_play_dia_price);
        TvPayDiaprice.setText("价格： " + mRoomLists.get(position).getPrice() + "元");
        TextView TvPayId = (TextView) dialogView.findViewById(R.id.tv_play_dia_id);
        TvPayId.setText("房间ID：" + mRoomLists.get(position).getId());
        TextView TvPayName = (TextView) dialogView.findViewById(R.id.tv_play_dia_name);
        TvPayName.setText("当前直播：" + mRoomLists.get(position).getUsername());
        Button btnGoPay = (Button) dialogView.findViewById(R.id.btn_go_pay);
        //进入播放器
        btnGoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                goToPlay(position);
            }
        });

        mPayDialog.setView(dialogView);
        show = mPayDialog.show();
        //点击消失
        ivPayChacha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    private void goToPlay(int position) {
        Intent i = new Intent(context, PlayActivity.class);
        int isall = mRoomLists.get(position).getIsall();
        if (isall == VedioContants.TWO_D_VEDIO) {
            i.putExtra(Definition.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
        } else if (isall == VedioContants.ALL_VIEW_VEDIO) {
            i.putExtra(Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
        }
        LogUtil.i(mRoomLists.get(position).getRtmpDownstreamAddress() + "");
        i.putExtra(Definition.KEY_PLAY_URL, mRoomLists.get(position).getRtmpDownstreamAddress() + "");
        i.putExtra(Definition.KEY_PLAY_HEAD, HttpURL.IV_HOST + mRoomLists.get(position).getHead() + "");
        i.putExtra(Definition.KEY_PLAY_USERNAME, mRoomLists.get(position).getUsername() + "");
        i.putExtra(Definition.KEY_PLAY_ID, mRoomLists.get(position).getId() + "");

//                i.putExtra(Definition.KEY_PLAY_URL, "rtmp://9250.liveplay.myqcloud.com/live/9250_87716a9f19111");
        context.startActivity(i);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvPlayer;
        private TextView mTvPersonName;
        private TextView mTvRoomPay;
        private TextView mTvChannelName;
        private ImageView mIvRoomHead;
        private RelativeLayout mRlPersonName;
        private LinearLayout mllRoomName;

        MyViewHolder(View view) {
            super(view);
            mTvPlayer = (TextView) view.findViewById(R.id.title);
            mTvPersonName = (TextView) view.findViewById(R.id.tv_room_person_name);
            mTvChannelName = (TextView) view.findViewById(R.id.tv_play_channelName);
            mTvRoomPay = (TextView) view.findViewById(R.id.tv_pay);
            mIvRoomHead = (ImageView) view.findViewById(R.id.iv_room_head);
            mRlPersonName = (RelativeLayout) view.findViewById(R.id.ll_root_person_name);
            mllRoomName = (LinearLayout) view.findViewById(R.id.ll_root_room_name);
        }
    }

}
