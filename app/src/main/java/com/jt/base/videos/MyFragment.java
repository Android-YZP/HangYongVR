package com.jt.base.videos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jt.base.R;
import com.jt.base.login.LoginActivity;
import com.jt.base.login.PersonalActivity;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.UIUtils;


public class MyFragment extends Fragment {


    private Button mBtnLogin;
    private boolean isLogin;
    private LinearLayout mRootPersonInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        //退出登录
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.clearUser();//清除缓存
                SPUtil.put(getActivity(), "isLogin", false);
                UIUtils.showTip("退出成功");

            }
        });

        mRootPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLogin = (Boolean) SPUtil.get(getActivity(), "isLogin", false);
                if (isLogin) {
                    startActivity(new Intent(getActivity(), PersonalActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
    }

    private void initData()  vb


    private void initView(View view) {
        mBtnLogin = (Button) view.findViewById(R.id.btn_my_login);
        mRootPersonInfo = (LinearLayout) view.findViewById(R.id.ll_root_my_personal_info);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
