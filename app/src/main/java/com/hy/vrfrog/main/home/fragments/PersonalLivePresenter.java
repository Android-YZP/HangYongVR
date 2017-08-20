package com.hy.vrfrog.main.home.fragments;


import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.RechargeBean;
import com.hy.vrfrog.main.home.adapters.PersonalLiveHomeAdapter;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.ui.LoadingDataUtil;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.ToolToast;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by qwe on 2017/8/20.
 */

public class PersonalLivePresenter implements PersonalLiveContract.Presenter{


    private PersonalLiveContract.PersonalLiveView mPersonalLiveView;


    public PersonalLivePresenter(PersonalLiveContract.PersonalLiveView personalLiveView){

        this.mPersonalLiveView = personalLiveView;

        mPersonalLiveView.setPresenter(this);

    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getPersonalLiveData(int pager, int count, int type) {
        if (!NetUtil.isOpenNetwork()) {
            mPersonalLiveView.noNetwork();
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.AllLive);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
//        requestParams.addBodyParameter("sourceNum", "111");//
        requestParams.addBodyParameter("page", pager + "");//
        requestParams.addBodyParameter("count", count +"");//
        requestParams.addBodyParameter("type", type + "");//

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("个人直播数据---------------", result );

                GetLiveHomeBean getLiveHomeBean = new Gson().fromJson(result, GetLiveHomeBean.class);

                mPersonalLiveView.getPersonalLiveDataSuccess(getLiveHomeBean );

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//
                mPersonalLiveView.getPersonalLiveDataFail(ex);
            }

            @Override
            public void onFinished() {
                mPersonalLiveView.getPersonalLiveDataFinish();
//                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onPayMoney(int price ,final int position, List<GetLiveHomeBean.ResultBean>mData) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        if (SPUtil.getUser() != null){
            RequestParams requestParams = new RequestParams(HttpURL.Pay);
            requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid",SPUtil.getUser().getResult().getUser().getUid()+"");
            requestParams.addBodyParameter("type",3+"");
            requestParams.addBodyParameter("vid",mData.get(position).getId()+ "");
            requestParams.addBodyParameter("money",price+"");
            requestParams.addBodyParameter("yid",mData.get(position).getUid()+"");

            LogUtil.i("个人直播支付token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("个人直播支付uid = " + SPUtil.getUser().getResult().getUser().getUid());
            LogUtil.i("个人直播支付vid = " + mData.get(position).getId());
            LogUtil.i("个人直播支付yid = " + mData.get(position).getUid());
            LogUtil.i("个人直播支付money = " + price);

            LogUtil.i("个人直播支付type = " + 3);

            //获取数据
            x.http().post(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    LogUtil.i("支付 = " +  result);

                    GiveRewardBean giveBean = new Gson().fromJson(result,GiveRewardBean.class);
                    mPersonalLiveView.payMoneySuccess(position,giveBean);


                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    mPersonalLiveView.payMoneyFailure(ex);

                }

                @Override
                public void onFinished() {

                }
            });

        }else {
            UIUtils.showTip("请登陆");
        }
    }

    @Override
    public void onRechargeMoney(final int position) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        RequestParams requestParams = new RequestParams(HttpURL.Add);
        requestParams.addHeader("token",SPUtil.getUser().getResult().getUser().getToken());
        requestParams.addBodyParameter("uid",SPUtil.getUser().getResult().getUser().getUid()+"");
        requestParams.addBodyParameter("cid",30+"");

        LogUtil.i("支付token = " + SPUtil.getUser().getResult().getUser().getToken());
        LogUtil.i("支付uid = " + SPUtil.getUser().getResult().getUser().getUid());



        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {

                LogUtil.i("充值 = " +  result);

                RechargeBean rechargeBean = new Gson().fromJson(result,RechargeBean.class);
                LogUtil.i("rechargeBean.getCode() = " +  rechargeBean.getCode() );
                mPersonalLiveView.rechargeMoneySuccess(position,rechargeBean);

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mPersonalLiveView.rechargeMoneyFaiiure(ex);

            }

            @Override
            public void onFinished() {

            }
        });



    }
}
