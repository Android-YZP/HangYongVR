package com.hy.vrfrog.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hy.vrfrog.R;

/**
 * Created by qwe on 2017/8/3.
 */

public class VerifiedDialog {
    private Context context;
    private Dialog dialog;
    private TextView mVerifiedCancelTv;
    private TextView mVerifiedConfirmTv;
    private Display display;

    public VerifiedDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public VerifiedDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_verified,null);

        // 获取自定义Dialog布局中的控件

        mVerifiedCancelTv = (TextView) view.findViewById(R.id.tv_dialog_verified_cancel);
        mVerifiedConfirmTv = (TextView)view.findViewById(R.id.tv_dialog_verified_confirm_pay);

        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);

        return this;
    }

    public VerifiedDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public VerifiedDialog show() {
        dialog.show();
        return this ;
    }

    public VerifiedDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }

    public VerifiedDialog setNegativeButton(String text,
                                              final View.OnClickListener listener) {
        if ("".equals(text)) {
            mVerifiedCancelTv.setText(context.getString(R.string.dialog_pay_virtual_cancel));
        } else {
            mVerifiedCancelTv.setText(text);
        }
        mVerifiedCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public VerifiedDialog setPositiveButton(String text,
                                              final View.OnClickListener listener) {
        if ("".equals(text)) {
            mVerifiedConfirmTv.setText(context.getString(R.string.dialog_pay_virtual_confirm_pay));
        } else {
            mVerifiedConfirmTv.setText(text);
        }
        mVerifiedConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }
}
