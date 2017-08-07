package com.hy.vrfrog.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.vrfrog.R;

/**
 * Created by qwe on 2017/8/4.
 */

public class RechargeDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView mVideoRechargeBalanceTv;
    private LinearLayout mVideoRechargeDeleteLl;
    private Button mVideoRechargeBtn;
    private RelativeLayout mDemandPayRl;

    public RechargeDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public RechargeDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.video_recharge,null);

        // 获取自定义Dialog布局中的控件

        mVideoRechargeBalanceTv = (TextView)view.findViewById(R.id.tv_video_recharge_balance);

        mVideoRechargeDeleteLl = (LinearLayout)view.findViewById(R.id.ll_video_recharge_cancel);
        mVideoRechargeBtn = (Button)view.findViewById(R.id.btn_video_recharge);
        mDemandPayRl = (RelativeLayout)view.findViewById(R.id.rl_demand_pay_recharge);


        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);

        return this;
    }


    public RechargeDialog setDeleteListener(String text, final View.OnClickListener listener) {

        mVideoRechargeDeleteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public RechargeDialog setPayListener(String text, final View.OnClickListener listener) {

        mVideoRechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public RechargeDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public RechargeDialog show() {
        dialog.show();
        return this ;
    }

    public RechargeDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }
}
