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
 * Created by qwe on 2017/7/31.
 */

public class VirtuelPayPriceDialog {
    private Context context;
    private Dialog dialog;
    private TextView mVirtualPayCancelTv;
    private TextView mVirtualPayConfirmTv;
    private Display display;
    private TextView mVirtualPayTitleTv;
    private TextView mVirtualPayPriceTv;
    private TextView mVirtualPayAccountBalanceTv;
    private TextView mVirtuePayHouseId;
    private TextView mVirtuePayLive;

    public VirtuelPayPriceDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public VirtuelPayPriceDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_virtue_pay_price,null);

        // 获取自定义Dialog布局中的控件

        mVirtualPayTitleTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_price_title);
        mVirtualPayPriceTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_price_price);
        mVirtualPayAccountBalanceTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_price_account_balance);
        mVirtualPayCancelTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_price_cancel);
        mVirtualPayConfirmTv = (TextView)view.findViewById(R.id.tv_dialog_virtual_pay_price_account_confirm_pay);
        mVirtuePayHouseId = (TextView)view.findViewById(R.id.tv_dialog_virtual_pay_house_id);
        mVirtuePayLive = (TextView)view.findViewById(R.id.dialog_virtual_pay_price_house_live);

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

    public VirtuelPayPriceDialog setTitle(String title ) {
        mVirtualPayTitleTv.setText(title);
        return this;
    }

    public VirtuelPayPriceDialog setPrice(String price ) {
        mVirtualPayPriceTv.setText(price);
        return this;
    }

    public VirtuelPayPriceDialog setAccountBalance(String account ) {
        mVirtualPayAccountBalanceTv.setText(account);
        return this;
    }

    public VirtuelPayPriceDialog setHouseId(String account ) {
        mVirtuePayHouseId.setText(account);
        return this;
    }

    public VirtuelPayPriceDialog setHouseLive(String account ) {
        mVirtuePayLive.setText(account);
        return this;
    }


    public VirtuelPayPriceDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public VirtuelPayPriceDialog show() {
        dialog.show();
        return this ;
    }

    public VirtuelPayPriceDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }

    public VirtuelPayPriceDialog setNegativeButton(String text,
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

    public VirtuelPayPriceDialog setPositiveButton(String text,
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
