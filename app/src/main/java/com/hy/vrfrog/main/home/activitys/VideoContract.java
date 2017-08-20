package com.hy.vrfrog.main.home.activitys;

import android.content.Context;

import com.hy.vrfrog.base.BasePresenter;
import com.hy.vrfrog.base.BaseView;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;

import java.util.List;

/**
 * Created by qwe on 2017/8/20.
 */

public class VideoContract {


    public interface VideoView extends BaseView<Presenter> {


        void showGetRewardResult(GiveRewardBean giveRewardBean);

        void showGetRewardResultError(Throwable ex);

        void showPayMoneyResult(int position,GiveRewardBean giveRewardBean);

        void showPayMoneyResultError(Throwable ex);


    }

    public interface Presenter extends BasePresenter {

        void getRewardData(int count, int position , List<VodbyTopicBean.ResultBean> mData);

        void goPayMoney(int count, int position, List<VodbyTopicBean.ResultBean> mData);

    }

}
