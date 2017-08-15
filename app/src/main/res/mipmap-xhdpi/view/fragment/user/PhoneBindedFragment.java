package com.mytv365.view.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.mytv365.R;
import com.mytv365.common.Constant;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/6
 * Description:
 */
public class PhoneBindedFragment extends BaseFragment {
    private TextView tv_current_bind;
    private Button btn_change_phone_bind;

    public static com.mytv365.view.fragment.user.PhoneBindedFragment newInstance() {

        Bundle args = new Bundle();

        com.mytv365.view.fragment.user.PhoneBindedFragment fragment = new com.mytv365.view.fragment.user.PhoneBindedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_phone_binded;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        tv_current_bind = (TextView) findViewById(R.id.tv_current_bind);
        btn_change_phone_bind = (Button) findViewById(R.id.btn_change_phone_bind);
        btn_change_phone_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.mytv365.view.activity.user.PhoneBindActivity) getActivity()).goToBind();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_current_bind.setText("当前绑定的手机号：" + Constant.userinfo.getUsername());
    }
}
