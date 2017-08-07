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

public class DemandPayDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView mDemandPayNumberTv;
    private TextView mDemandPayDetailedTv;
    private TextView mDemandPayBalancetv;
    private LinearLayout mDemandPayDeleteLl;
    private Button mDemandPayBtn;
    private RelativeLayout mDemandPayRl;

    public DemandPayDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public DemandPayDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.video_pay,null);

        // 获取自定义Dialog布局中的控件

        mDemandPayNumberTv = (TextView) view.findViewById(R.id.tv_demand_pay_number);
        mDemandPayDetailedTv = (TextView)view.findViewById(R.id.tv_demand_pay_detailed);
        mDemandPayBalancetv = (TextView)view.findViewById(R.id.tv_demand_pay_balance);

        mDemandPayDeleteLl = (LinearLayout)view.findViewById(R.id.ll_demand_pay_delete);
        mDemandPayBtn = (Button)view.findViewById(R.id.btn_demand_pay);
        mDemandPayRl = (RelativeLayout)view.findViewById(R.id.rl_demand_pay_recharge);

        mDemandPayBalancetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);

        return this;
    }


    public DemandPayDialog setDeleteListener(String text, final View.OnClickListener listener) {

        mDemandPayDeleteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public DemandPayDialog setPayListener(String text, final View.OnClickListener listener) {

        mDemandPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public DemandPayDialog setRechargeListener(String text, final View.OnClickListener listener) {

        mDemandPayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public DemandPayDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public DemandPayDialog show() {
        dialog.show();
        return this ;
    }

    public DemandPayDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }
}
