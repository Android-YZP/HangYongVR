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

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.AccountBean;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by qwe on 2017/7/31.
 */

public class VirtuelPayPlayPriceDialog {
    private Context context;
    private Dialog dialog;
    private TextView mVirtualPayCancelTv;
    private TextView mVirtualPayConfirmTv;
    private Display display;
    private TextView mVirtualPayTitleTv;
    private TextView mVirtualPayPriceTv;
    private TextView mVirtualPayAccountBalanceTv;


    public VirtuelPayPlayPriceDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public VirtuelPayPlayPriceDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_virtue_pay_play_price,null);

        // 获取自定义Dialog布局中的控件

        mVirtualPayTitleTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_play_price_title);
        mVirtualPayPriceTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_play_price_price);
        mVirtualPayAccountBalanceTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_price_account_balance);
        mVirtualPayCancelTv = (TextView) view.findViewById(R.id.tv_dialog_virtual_pay_play_price_cancel);
        mVirtualPayConfirmTv = (TextView)view.findViewById(R.id.tv_dialog_virtual_pay_play_price_account_confirm_pay);

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
        initAccount();


        return this;
    }

    private void initAccount() {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        if (SPUtil.getUser() != null) {
            RequestParams requestParams = new RequestParams(HttpURL.Remain);
            requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid", SPUtil.getUser().getResult().getUser().getUid() + "");

            LogUtil.i("余额token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("余额uid = " + SPUtil.getUser().getResult().getUser().getUid());

            //获取数据
            x.http().get(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    LogUtil.i("余额 = " + result);
                    AccountBean accountBean = new Gson().fromJson(result, AccountBean.class);
                    if (accountBean.getCode() == 0) {
                        LogUtil.i("余额 = " + accountBean.getResult());
                        mVirtualPayAccountBalanceTv.setText(String.valueOf(accountBean.getResult()));
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    UIUtils.showTip("服务端连接失败");

                }

                @Override
                public void onFinished() {
                }
            });
        }
    }

    public VirtuelPayPlayPriceDialog setTitle(String title ) {
        mVirtualPayTitleTv.setText(title);
        return this;
    }

    public VirtuelPayPlayPriceDialog setPrice(String price ) {
        mVirtualPayPriceTv.setText(price);
        return this;
    }

    public VirtuelPayPlayPriceDialog setAccountBalance(String account ) {
        mVirtualPayAccountBalanceTv.setText(account);
        return this;
    }



    public VirtuelPayPlayPriceDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public VirtuelPayPlayPriceDialog show() {
        dialog.show();
        return this ;
    }

    public VirtuelPayPlayPriceDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }

    public VirtuelPayPlayPriceDialog setNegativeButton(String text,
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

    public VirtuelPayPlayPriceDialog setPositiveButton(String text,
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
