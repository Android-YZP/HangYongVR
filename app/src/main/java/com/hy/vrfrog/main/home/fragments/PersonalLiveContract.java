package com.hy.vrfrog.main.home.fragments;

import com.hy.vrfrog.base.BasePresenter;
import com.hy.vrfrog.base.BaseView;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.RechargeBean;

import java.util.List;


/**
 * Created by qwe on 2017/8/20.
 */

public class PersonalLiveContract {

    public interface PersonalLiveView extends BaseView<Presenter> {

        void getPersonalLiveDataSuccess(GetLiveHomeBean getLiveHomeBean);

        void getPersonalLiveDataFail(Throwable throwable);

        void getPersonalLiveDataFinish();

        void noNetwork();

        void payMoneySuccess(int position,GiveRewardBean giveRewardBean);

        void payMoneyFailure(Throwable throwable);

        void rechargeMoneySuccess(int position ,RechargeBean rechargeBean);

        void rechargeMoneyFaiiure(Throwable ex);



    }

    public interface Presenter extends BasePresenter {

        void getPersonalLiveData(int pager ,int count,int type);

        void onPayMoney(int price,int position, List<GetLiveHomeBean.ResultBean> mList);

        void onRechargeMoney(int money);


    }


}
