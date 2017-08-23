package com.hy.vrfrog.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
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

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.AccountBean;
import com.hy.vrfrog.utils.BasePreferences;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
    private TextView mDemandPayDetailedIdTv;

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
        mDemandPayDetailedIdTv = (TextView)view.findViewById(R.id.tv_demand_pay_detailed_id);

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
        dialogWindow.setGravity( Gravity.BOTTOM);
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
        if(SPUtil.getUser() != null){
            RequestParams requestParams = new RequestParams(HttpURL.Remain);
            requestParams.addHeader("token",SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid",SPUtil.getUser().getResult().getUser().getUid()+"");

            LogUtil.i("余额token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("余额uid = " + SPUtil.getUser().getResult().getUser().getUid());

            //获取数据
            x.http().get(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    LogUtil.i("余额 = " +  result);
                    AccountBean accountBean = new Gson().fromJson(result,AccountBean.class);
                    if (accountBean.getCode() == 0){
                        LogUtil.i("余额 = " +  accountBean.getResult());
                        mDemandPayBalancetv.setText(String.valueOf(accountBean.getResult()));
                        BasePreferences basePreferences = new BasePreferences(context);
                        basePreferences.setPrefString("account",accountBean.getResult());
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

    public DemandPayDialog setPaybalance(String text) {
        if (!TextUtils.isEmpty(text)){
            mDemandPayBalancetv.setText(text);
        }

        return this;
    }

    public DemandPayDialog setDemandPayNumber(String text) {
        if (!TextUtils.isEmpty(text)){
            mDemandPayNumberTv.setText(text);
        }

        return this;
    }

    public DemandPayDialog setPayTitle(String text) {
        if (!TextUtils.isEmpty(text)){
            mDemandPayDetailedTv.setText(text);
        }

        return this;
    }

    public DemandPayDialog setPayId(String text) {
        if (!TextUtils.isEmpty(text)){
            mDemandPayDetailedIdTv.setText(text);
        }

        return this;
    }

    public DemandPayDialog setRechargeListener(String text, final View.OnClickListener listener) {

        mDemandPayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showTip("暂未开放");
//                listener.onClick(v);
//                dialog.dismiss();
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
