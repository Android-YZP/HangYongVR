package com.jt.base.videos.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.personal.HistoryActivity;
import com.jt.base.personal.HistoryPayActivity;
import com.jt.base.personal.LoginActivity;
import com.jt.base.personal.PersonalActivity;
import com.jt.base.personinformation.UploadingDocumentsActivity;
import com.jt.base.ui.VirtuelPayPriceDialog;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.UIUtils;

@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    private static final String TAG = MyFragment.class.getSimpleName();
    private Button mBtnLogin;
    private boolean isLogin;
    private LinearLayout mRootPersonInfo;
    private LinearLayout mRootUnLogin;
    private LinearLayout mRootHistory;
    private LinearLayout mRootLogined;
    private RelativeLayout mRootGoLogin;
    private RelativeLayout mRootGoLogin1;
    private TextView mTvName;
    private LinearLayout mLlMyPay;

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
                UIUtils.showTip("登出成功");
                initData();
            }
        });

        mRootPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin) {
                    startActivity(new Intent(getActivity(), PersonalActivity.class));
                    getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);

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
        mRootGoLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        mRootHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
            }
        });
        mLlMyPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HistoryPayActivity.class));
                getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
            }
        });
    }

    private void initData() {
        isLogin = (Boolean) SPUtil.get(getActivity(), "isLogin", false);
        if (isLogin)
            mTvName.setText(SPUtil.getUser().getResult().getUser().getUsername());

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
        mRootHistory = (LinearLayout) view.findViewById(R.id.ll_root_history);
        mRootLogined = (LinearLayout) view.findViewById(R.id.my_logined);
        mRootUnLogin = (LinearLayout) view.findViewById(R.id.my_un_login);
        mLlMyPay = (LinearLayout) view.findViewById(R.id.ll_my_pay);
        mTvName = (TextView) view.findViewById(R.id.tv_my_name);
        mRootGoLogin = (RelativeLayout) mRootUnLogin.findViewById(R.id.lll_root_login);
        mRootGoLogin1 = (RelativeLayout) mRootUnLogin.findViewById(R.id.lll_root_login1);
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
