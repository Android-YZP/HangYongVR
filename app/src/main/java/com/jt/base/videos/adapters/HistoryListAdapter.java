package com.jt.base.videos.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.TextView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.responsebean.GetHistoryBean;
import com.jt.base.http.responsebean.SeeHistory;
import com.jt.base.utils.DialogUtils;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.VideoPlayActivity;

import java.util.List;

import static com.ashokvarma.bottomnavigation.utils.Utils.dp2px;
import static com.ashokvarma.bottomnavigation.utils.Utils.getScreenWidth;
import static com.jt.base.R.id.rl_history_list;
import static com.jt.base.R.style.dialog;


/**
 * Created by wzq930102 on 2017/7/17.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryReViewHolder> {
    public Activity context;
    private TextView delete;
    private TextView cancel;
    private List<GetHistoryBean.ResultBean> seeHistory;

    public HistoryListAdapter(Activity context, List<GetHistoryBean.ResultBean> seeHistory) {
        this.context = context;
        this.seeHistory = seeHistory;
    }

    @Override
    public HistoryReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_history_item, parent, false);
        return new HistoryReViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryReViewHolder holder, final int position) {
        holder.mTvtitle.setText(seeHistory.get(position).getChannelName());
        holder.mTvpersent.setText(seeHistory.get(position).getWatchTime() + "%");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                context.startActivity(intent);
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showToastStyleDialog();
                return true;
            }
        });
    }

    private void showToastStyleDialog() {


        DialogUtils dialogUtils = new DialogUtils(context);
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.history_delete, null);
        dialogUtils.setContentView(contentView);
        dialogUtils.setGravity(Gravity.BOTTOM);
        dialogUtils.setXY(100, 100);
        dialogUtils.show();
        delete = (Button) contentView.findViewById(R.id.btn_history_delete);
        cancel = (Button) contentView.findViewById(R.id.btn_history_cancel);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });


        // 设置dialog的宽度
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.width = getScreenWidth(context) - dp2px(context, 50);
//        dialog.getWindow().setAttributes(params);}
//
//        /**
//         * 获得屏幕宽度
//         *
//         * @param context
//         * @return
//         */
//        public int getScreenWidth(Context context) {
//            WindowManager wm = (WindowManager) context
//                    .getSystemService(Context.WINDOW_SERVICE);
//            DisplayMetrics outMetrics = new DisplayMetrics();
//            wm.getDefaultDisplay().getMetrics(outMetrics);
//            return outMetrics.widthPixels;
//        }
//    /**
//     * dp转px
//     *
//     * @param context
//     * @param
//     * @return
//     */
//    public int dp2px(Context context, float dpVal) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                dpVal, context.getResources().getDisplayMetrics());
//    }
    }

    @Override
    public int getItemCount() {
        return seeHistory.size();
    }

    public class HistoryReViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layout;
        private final ImageView mIvImg;
        private final TextView mTvtitle;
        private final TextView mTvpersent;

        public HistoryReViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_history_list);
            mIvImg = (ImageView) itemView.findViewById(R.id.iv_history_img);
            mTvtitle = (TextView) itemView.findViewById(R.id.tv_history_title);
            mTvpersent = (TextView) itemView.findViewById(R.id.tv_history_persent);
        }
    }
}
