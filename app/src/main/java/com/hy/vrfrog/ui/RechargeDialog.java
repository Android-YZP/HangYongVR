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
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

    private ImageView mTopUpChooseLeftImg;
    private TextView mTopChooseLeftTv;
    private TextView mUpChooseLeftTv;
    private RelativeLayout mTopUpChooseLeftRl;


    private ImageView mTopUpChooseMiddleImg;
    private TextView mTopChooseMiddleTv;
    private TextView mUpChooseMiddleTv;
    private RelativeLayout mTopUpChooseMiddleRl;

    private ImageView mTopUpChooseRightImg;
    private TextView mTopChooseRightTv;
    private TextView mUpChooseRightTv;
    private RelativeLayout mTopUpChooseRightRl;
    private int mRechargeCount;

    private IChargeMoney mCallback;
    private ImageButton mBack;


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

        mTopUpChooseLeftImg = (ImageView)view.findViewById(R.id.iv_top_up_choose_left);
        mTopChooseLeftTv = (TextView)view.findViewById(R.id.tv_top_up_num_left);
        mUpChooseLeftTv = (TextView)view.findViewById(R.id.tv_top_up_price_left);
        mTopUpChooseLeftRl = (RelativeLayout)view.findViewById(R.id.rl_top_up_left);

        mTopUpChooseMiddleImg = (ImageView)view.findViewById(R.id.iv_top_up_choose_middle);
        mTopChooseMiddleTv = (TextView)view.findViewById(R.id.tv_top_up_num_middle);
        mUpChooseMiddleTv = (TextView)view.findViewById(R.id.tv_top_up_price_middle);
        mTopUpChooseMiddleRl = (RelativeLayout)view.findViewById(R.id.rl_top_up_middle);

        mTopUpChooseRightImg = (ImageView)view.findViewById(R.id.iv_top_up_choose_right);
        mTopChooseRightTv = (TextView)view.findViewById(R.id.tv_top_up_num_right);
        mUpChooseRightTv = (TextView)view.findViewById(R.id.tv_top_up_price_right);
        mTopUpChooseRightRl = (RelativeLayout)view.findViewById(R.id.rl_top_up_right);

        mBack = (ImageButton)view.findViewById(R.id.ib_top_up_back);



        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);

        initListener();

        return this;
    }

    private void initListener() {
        mTopUpChooseLeftRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTopUpChooseLeftImg.setImageResource(R.mipmap.pay_waves);
                mTopChooseLeftTv.setTextColor(Color.WHITE);
                mUpChooseLeftTv.setTextColor(Color.WHITE);

                mTopUpChooseMiddleImg.setImageResource(R.mipmap.pay_wave);
                mTopChooseMiddleTv.setTextColor(Color.parseColor("#666666"));
                mUpChooseMiddleTv.setTextColor(Color.parseColor("#666666"));

                mTopUpChooseRightImg.setImageResource(R.mipmap.pay_wave);
                mTopChooseRightTv.setTextColor(Color.parseColor("#666666"));
                mUpChooseRightTv.setTextColor(Color.parseColor("#666666"));
                mRechargeCount = 10 ;
                initRechargeRule();
            }
        });

        mTopUpChooseMiddleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTopUpChooseLeftImg.setImageResource(R.mipmap.pay_wave);
                mTopChooseLeftTv.setTextColor(Color.parseColor("#666666"));
                mUpChooseLeftTv.setTextColor(Color.parseColor("#666666"));

                mTopUpChooseMiddleImg.setImageResource(R.mipmap.pay_waves);
                mTopChooseMiddleTv.setTextColor(Color.WHITE);
                mUpChooseMiddleTv.setTextColor(Color.WHITE);

                mTopUpChooseRightImg.setImageResource(R.mipmap.pay_wave);
                mTopChooseRightTv.setTextColor(Color.parseColor("#666666"));
                mUpChooseRightTv.setTextColor(Color.parseColor("#666666"));

                mRechargeCount = 100;
            }
        });

        mTopUpChooseRightRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTopUpChooseLeftImg.setImageResource(R.mipmap.pay_wave);
                mTopChooseLeftTv.setTextColor(Color.parseColor("#666666"));
                mUpChooseLeftTv.setTextColor(Color.parseColor("#666666"));

                mTopUpChooseMiddleImg.setImageResource(R.mipmap.pay_wave);
                mTopChooseMiddleTv.setTextColor(Color.parseColor("#666666"));
                mUpChooseMiddleTv.setTextColor(Color.parseColor("#666666"));

                mTopUpChooseRightImg.setImageResource(R.mipmap.pay_waves);
                mTopChooseRightTv.setTextColor(Color.WHITE);
                mUpChooseRightTv.setTextColor(Color.WHITE);
                mRechargeCount = 200;
            }
        });


    }

    private void initRechargeRule() {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        if (SPUtil.getUser() != null){
            RequestParams requestParams = new RequestParams(HttpURL.Get);

            requestParams.addBodyParameter("page",1+"");
            requestParams.addBodyParameter("count",20+"");


            LogUtil.i("充值规则page = " + 1);
            LogUtil.i("充值规则 count " + 20);


            //获取数据
            x.http().post(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    LogUtil.i("打赏 = " +  result);


                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    UIUtils.showTip("服务端连接失败");

                }

                @Override
                public void onFinished() {

                }
            });

        }else {
            UIUtils.showTip("请登陆");
        }

    }


    public RechargeDialog setDeleteListener(String text, final View.OnClickListener listener) {

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public RechargeDialog setPayListener(String text, final IChargeMoney listener) {

        mVideoRechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.goChargeMoney(mRechargeCount);
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

    public interface IChargeMoney{

        void goChargeMoney(int money);

    }
}
