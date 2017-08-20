package com.hy.vrfrog.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.vrfrog.R;

/**
 * Created by zhengshicheng on 10/23/15.
 * 打赏dialog
 */
public class GiveRewardDialog {
    private Context context;
    private Dialog dialog;
    private TextView mVirtualPayCancelTv;
    private TextView mVirtualPayConfirmTv;
    private Display display;
    private LinearLayout mGiveRewardOneLl;
    private LinearLayout mGiveRewardTwoLl;
    private LinearLayout mGiveRewardThreeLl;
    private int mCount;
    private EditText mCountEt;
    private IGiveReward mCallback;

    public void setGiveReward(IGiveReward listener){
        this.mCallback = listener;
    }


    public GiveRewardDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public GiveRewardDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_give_reward,null);

        // 获取自定义Dialog布局中的控件

        mVirtualPayCancelTv = (TextView) view.findViewById(R.id.tv_give_reward_cancel);
        mVirtualPayConfirmTv = (TextView)view.findViewById(R.id.tv_give_reward_confirm);
        mCountEt = (EditText)view.findViewById(R.id.ed_give_reward_num);

        mGiveRewardOneLl = (LinearLayout)view.findViewById(R.id.ll_give_reward_one);
        mGiveRewardTwoLl = (LinearLayout)view.findViewById(R.id.ll_give_reward_two);
        mGiveRewardThreeLl = (LinearLayout)view.findViewById(R.id.ll_give_reward_three);


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

        initListener();

        return this;
    }

    private void initListener() {

        mGiveRewardOneLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCount = 88 ;
            }
        });

        mGiveRewardTwoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCount = 188;
            }
        });

        mGiveRewardThreeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCount = 288;
            }
        });

        mCountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               mCount = Integer.valueOf(mCountEt.getText().toString());
            }
        });


    }


    public GiveRewardDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public GiveRewardDialog show() {
        dialog.show();
        return this ;
    }

    public GiveRewardDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }

    public GiveRewardDialog setNegativeButton(String text,
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

    public GiveRewardDialog setPositiveButton(String text, final IGiveReward listener) {
        if ("".equals(text)) {
            mVirtualPayConfirmTv.setText("打赏");
        } else {
            mVirtualPayConfirmTv.setText(text);
        }

        mVirtualPayConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.GoGiveReward(mCount);
                dialog.dismiss();
            }
        });



        return this;
    }

    public interface IGiveReward{
        void GoGiveReward(int count );
    }

}
