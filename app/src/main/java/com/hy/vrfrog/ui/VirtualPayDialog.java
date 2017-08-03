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
 * Created by zhengshicheng on 10/23/15.
 */
public class VirtualPayDialog {
    private Context context;
    private Dialog dialog;
    private TextView mVirtualPayCancelTv;
    private TextView mVirtualPayConfirmTv;
    private Display display;
    private TextView mVirtualPayTitleTv;
    private TextView mVirtualPayPriceTv;
    private TextView mVirtualPayAccountBalanceTv;

    public VirtualPayDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public VirtualPayDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_pay_virtual,null);

        // 获取自定义Dialog布局中的控件

        mVirtualPayTitleTv = (TextView) view.findViewById(R.id.tv_dialog_pay_virtual_title);
        mVirtualPayPriceTv = (TextView) view.findViewById(R.id.tv_dialog_pay_virtual_price);
        mVirtualPayAccountBalanceTv = (TextView) view.findViewById(R.id.tv_dialog_pay_virtual_account_balance);
        mVirtualPayCancelTv = (TextView) view.findViewById(R.id.tv_dialog_pay_virtual_cancel);
        mVirtualPayConfirmTv = (TextView)view.findViewById(R.id.tv_dialog_pay_virtual_account_confirm_pay);

        mVirtualPayCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public VirtualPayDialog setTitle(String title ) {
        mVirtualPayTitleTv.setText(title);
        return this;
    }

    public VirtualPayDialog setPrice(String price ) {
        mVirtualPayPriceTv.setText(price);
        return this;
    }

    public VirtualPayDialog setAccountBalance(String account ) {
        mVirtualPayAccountBalanceTv.setText(account);
        return this;
    }


    public VirtualPayDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public VirtualPayDialog show() {
        dialog.show();
        return this ;
    }

    public VirtualPayDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }

    public VirtualPayDialog setNegativeButton(String text,
                                               final View.OnClickListener listener) {
        if ("".equals(text)) {
            mVirtualPayCancelTv.setText(context.getString(R.string.dialog_pay_virtual_cancel));
        } else {
            mVirtualPayCancelTv.setText(text);
        }
        mVirtualPayCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public VirtualPayDialog setPositiveButton(String text,
                                              final View.OnClickListener listener) {
        if ("".equals(text)) {
            mVirtualPayConfirmTv.setText(context.getString(R.string.dialog_pay_virtual_confirm_pay));
        } else {
            mVirtualPayConfirmTv.setText(text);
        }
        mVirtualPayConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

}
