package com.hy.vrfrog.main.home.fragments;


import com.google.gson.Gson;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.RechargeBean;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by qwe on 2017/8/20.
 */

public class EnterpriseLivePresenter implements EnterpriseLiveContract.Presenter{


    private EnterpriseLiveContract.EnterpriseLiveView mEnterpriseLiveView;


    public EnterpriseLivePresenter(EnterpriseLiveContract.EnterpriseLiveView enterpriseLiveView){

        this.mEnterpriseLiveView = enterpriseLiveView;

        mEnterpriseLiveView.setPresenter(this);

    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getEnterpriseLiveData(int pager, int count, int type) {
        if (!NetUtil.isOpenNetwork()) {
            mEnterpriseLiveView.noNetwork();
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
        requestParams.addBodyParameter("sourceNum", "111");//

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
//                LongLogUtil.e("个人直播数据---------------", result );

                GetLiveHomeBean getLiveHomeBean = new Gson().fromJson(result, GetLiveHomeBean.class);

                mEnterpriseLiveView.getEnterpriseLiveDataSuccess(getLiveHomeBean );

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//
                mEnterpriseLiveView.getEnterpriseLiveDataFail(ex);
            }

            @Override
            public void onFinished() {
                mEnterpriseLiveView.getEnterpriseLiveDataFinish();
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

            LogUtil.i("企业直播支付token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("企业直播支付uid = " + SPUtil.getUser().getResult().getUser().getUid());
            LogUtil.i("企业直播支付vid = " + mData.get(position).getId());
            LogUtil.i("企业直播支付yid = " + mData.get(position).getUid());
            LogUtil.i("企业直播支付money = " + price);

            LogUtil.i("企业直播支付type = " + 3);

            //获取数据
            x.http().post(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    LogUtil.i("支付 = " +  result);

                    GiveRewardBean giveBean = new Gson().fromJson(result,GiveRewardBean.class);
                    mEnterpriseLiveView.payMoneySuccess(position,giveBean);


                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    mEnterpriseLiveView.payMoneyFailure(ex);

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
                mEnterpriseLiveView.rechargeMoneySuccess(position,rechargeBean);

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mEnterpriseLiveView.rechargeMoneyFaiiure(ex);

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
