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
    private LinearLayout mRootUnLogin;
    private LinearLayout mRootLogined;
    private LinearLayout mRootGoLogin;

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

        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initListener() {
        //退出登录
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.clearUser();//清除缓存
                SPUtil.put(getActivity(), "isLogin", false);
                UIUtils.showTip("退出成功");
               initData();
            }
        });

        mRootPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin) {
                    startActivity(new Intent(getActivity(), PersonalActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        mRootGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    private void initData() {
        isLogin = (Boolean) SPUtil.get(getActivity(), "isLogin", false);

        refreshUI();
    }

    private void refreshUI() {
        if (isLogin) {
            mRootLogined.setVisibility(View.VISIBLE);
            mRootUnLogin.setVisibility(View.GONE);
        } else {
            mRootLogined.setVisibility(View.GONE);
            mRootUnLogin.setVisibility(View.VISIBLE);
        }
    }


    private void initView(View view) {
        mBtnLogin = (Button) view.findViewById(R.id.btn_my_login);
        mRootPersonInfo = (LinearLayout) view.findViewById(R.id.ll_root_my_personal_info);
        mRootLogined = (LinearLayout) view.findViewById(R.id.my_logined);
        mRootUnLogin = (LinearLayout) view.findViewById(R.id.my_un_login);
        mRootGoLogin = (LinearLayout) mRootUnLogin.findViewById(R.id.ll_root_login);
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
