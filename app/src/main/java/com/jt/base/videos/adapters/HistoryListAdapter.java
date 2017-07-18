package com.jt.base.videos.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.utils.DialogUtils;

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

    public HistoryListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public HistoryReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_history_item, parent, false);
        return new HistoryReViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryReViewHolder holder, final int position) {
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
        dialogUtils.setXY(100,100);
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
        return 20;
    }

    public class HistoryReViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layout;

        public HistoryReViewHolder(View itemView) {
            super(itemView);
            layout = ((RelativeLayout) itemView.findViewById(rl_history_list));
        }
    }
}
