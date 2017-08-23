package com.hy.vrfrog.main.home.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.VrApplication;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.AccountBean;
import com.hy.vrfrog.http.responsebean.CertificationBean;
import com.hy.vrfrog.http.responsebean.ChannelStatusBean;
import com.hy.vrfrog.http.responsebean.RechargeBean;
import com.hy.vrfrog.main.personal.AuthenticationActivity;
import com.hy.vrfrog.main.personal.HistoryActivity;
import com.hy.vrfrog.main.personal.HistoryPayActivity;
import com.hy.vrfrog.main.personal.LoginActivity;
import com.hy.vrfrog.main.personal.PersonalActivity;
import com.hy.vrfrog.main.personal.ReleaseLiveActivity;
import com.hy.vrfrog.main.personal.UploadingDocumentsActivity;
import com.hy.vrfrog.ui.LoadingDataUtil;
import com.hy.vrfrog.utils.BasePreferences;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.ToolToast;
import com.hy.vrfrog.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;


@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    private static final String TAG = MyFragment.class.getSimpleName();
    private Button mBtnLogin;
    private boolean isLogin;
    private LinearLayout mRootPersonInfo;
    private LinearLayout mRootUnLogin;
    private LinearLayout mRootHistory;
    private ScrollView mRootLogined;
    private LinearLayout mCertification;
    private RelativeLayout mRootGoLogin;
    private RelativeLayout mRootGoLogin1;
    private TextView mTvName;
    private LinearLayout mLlMyPay;
    private TextView mAccountTv;
    private TextView mCertificationTv;
    private BasePreferences mBasePreferences;
    private ChannelStatusBean channelStatusBean;
    private CertificationBean certificationBean;
    private ImageView mIvHead;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mBasePreferences = new BasePreferences(getActivity());
        initView(view);
        initData();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initRemain();
        if (SPUtil.getUser() != null){
            HttpCreatRoom(SPUtil.getUser().getResult().getUser().getUid() + "");
            Glide.with(this).load(HttpURL.IV_USER_HOST +SPUtil.getUser().getResult().getUser().getHead()+"").asBitmap().into(mIvHead);
        }

//        initAuditStatus();
    }

    private void initAuditStatus() {

        if(SPUtil.getUser() != null){
            RequestParams requestParams = new RequestParams(HttpURL.ChannelStatus);
            requestParams.addHeader("token",SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid",SPUtil.getUser().getResult().getUser().getUid()+"");

            LogUtil.i("审核状态token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("审核状态uid = " + SPUtil.getUser().getResult().getUser().getUid());

            //获取数据
            x.http().get(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {
                    BasePreferences basePreferences = new BasePreferences(getActivity());

                    LogUtil.i("certificate =" + basePreferences.getPrefString("certificate"));
                    channelStatusBean = new Gson().fromJson(result,ChannelStatusBean.class);

                        if (channelStatusBean.getCode() == 0 || channelStatusBean.getCode() == 110){
                            mCertificationTv.setText("已认证");
                        }else {
                            mCertificationTv.setText("未认证");
                        }


                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {


//                    UIUtils.showTip("服务端连接失败");

                }

                @Override
                public void onFinished() {
                }
            });
        }

    }

    private void initRemain() {
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

                        mAccountTv.setText(String.valueOf(accountBean.getResult()));
                        mBasePreferences.setPrefString("account",accountBean.getResult());
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

        mCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                BasePreferences basePreferences = new BasePreferences(getActivity());
//                LogUtil.i("certificate =" + basePreferences.getPrefBoolean("certificate"));
//                boolean certificate = basePreferences.getPrefBoolean("certificate");
                if (certificationBean.getCode() == 0 || certificationBean.getCode() == 110){

                }else {
                    startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                    getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
                }

            }
        });
    }

    private void initData() {
        isLogin = (Boolean) SPUtil.get(getActivity(), "isLogin", false);

        if (SPUtil.getUser() != null) {
            if (SPUtil.getUser().getResult() != null) {
                if (SPUtil.getUser().getResult().getUser() != null) {
                    if (SPUtil.getUser().getResult().getUser().getUsername() != null) {
                        mTvName.setText(String.valueOf(SPUtil.getUser().getResult().getUser().getUsername()));
                    }
                }
            }
        }



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
        mRootLogined = (ScrollView) view.findViewById(R.id.my_logined);
        mRootUnLogin = (LinearLayout) view.findViewById(R.id.my_un_login);
        mCertification = (LinearLayout) view.findViewById(R.id.ll_my_certification);
        mLlMyPay = (LinearLayout) view.findViewById(R.id.ll_my_pay);
        mTvName = (TextView) view.findViewById(R.id.tv_my_name);
        mIvHead = (ImageView) view.findViewById(R.id.iv_head);
        mRootGoLogin = (RelativeLayout) mRootUnLogin.findViewById(R.id.lll_root_login);
        mRootGoLogin1 = (RelativeLayout) mRootUnLogin.findViewById(R.id.lll_root_login1);
        mAccountTv = (TextView)view.findViewById(R.id.tv_fragment_num);
        mCertificationTv = (TextView)view.findViewById(R.id.tv_my_certification);

//        BasePreferences basePreferences = new BasePreferences(getActivity());
////        LogUtil.i("certificate =" + basePreferences.getPrefBoolean("certificate");
//        boolean certificate = basePreferences.getPrefBoolean("certificate");
//        if (certificate){
//            mCertificationTv.setText("已认证");
//        }else {
//            mCertificationTv.setText("未认证");
//
//        }


    }


    private void HttpCreatRoom(final String UID) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.createRoom);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("uid", UID);

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i("实名认证 = " + result);
                certificationBean = new Gson().fromJson(result,CertificationBean.class);
                if (certificationBean.getCode() == 0 || certificationBean.getCode() == 110){
                    mCertificationTv.setText("已认证");
                }else {
                    mCertificationTv.setText("未认证");
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                super.onFinished();

            }
        });
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
