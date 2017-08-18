package com.hy.vrfrog.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.vrfrog.R;

/**
 * Created by qwe on 2017/8/4.
 */

public class PaySuccessDialog {
    private Context context;
    private Dialog dialog;
    private Display display;

    public PaySuccessDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public PaySuccessDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.pay_success,null);

        // 获取自定义Dialog布局中的控件

        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);

        return this;
    }


    public PaySuccessDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public PaySuccessDialog show() {
        dialog.show();
        return this ;
    }

    public PaySuccessDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }

}
