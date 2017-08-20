package com.hy.vrfrog.main.home.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.RechargeBean;
import com.hy.vrfrog.http.responsebean.RecommendBean;
import com.hy.vrfrog.main.home.adapters.EnterpriseOnLiveAdapter;
import com.hy.vrfrog.main.home.adapters.RecommandAdapter;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.ui.DemandPayDialog;
import com.hy.vrfrog.ui.ItemDivider;
import com.hy.vrfrog.ui.RechargeDialog;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;
import com.hy.vrfrog.main.home.adapters.PersonalLiveHomeAdapter;
import com.hy.vrfrog.ui.VirtualPayDialog;
import com.hy.vrfrog.ui.VirtuelPayPriceDialog;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.ToolToast;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2017/8/4.
 */
@SuppressLint("ValidFragment")
public class PersonalLiveHomeFragment extends Fragment implements PersonalLiveContract.PersonalLiveView, PersonalLiveHomeAdapter.IPersonalLiveAdapter {

    private LinearLayout mEmptyll;
    private VerticalSwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private PersonalLiveHomeAdapter mAdapter;
    private List<GetLiveHomeBean.ResultBean> mList;
    private int pager = 1;
    private GetLiveHomeBean getLiveHomeBean;
    private boolean isLoadingMore;
    private PersonalLivePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_live_home, container, false);
        initView(view);
        mPresenter.getPersonalLiveData(pager, 10, 2);

        initListener();
        return view;
    }

    private void initListener() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pager = 1;
                if (mList.size() != 0) {
                    mList.clear();
                }
                mPresenter.getPersonalLiveData(pager, 10, 2);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean visBottom = UIUtils.isVisBottom(mRecyclerView);
                if (visBottom) {
                    if (mAdapter.getFooterView() == null) {
                        ++pager;
                        isLoadingMore = true;
                        mPresenter.getPersonalLiveData(pager, 10, 2);
                    } else {
                        return;
                    }

//                    if (getLiveHomeBean.getPage().getTotal() <= pager) {
//                        View v = View.inflate(getContext(), R.layout.main_list_no_datas, null);//main_list_item_foot_view
//                        mAdapter.setFooterView(v);
//                        mAdapter.notifyDataSetChanged();
//                    }
                }
            }
        });
    }

    private void initView(View view) {

        mEmptyll = (LinearLayout) view.findViewById(R.id.ll_live_home_no_data);
        mSwipeRefresh = (VerticalSwipeRefreshLayout) view.findViewById(R.id.vsr_live_home__refresh);
//        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_live_home_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new ItemDivider(10));
        new PersonalLivePresenter(this);

    }

    @Override
    public void setPresenter(PersonalLiveContract.Presenter presenter) {
        this.mPresenter = (PersonalLivePresenter) presenter;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void getPersonalLiveDataSuccess(GetLiveHomeBean getLiveHomeBean) {
        if (getLiveHomeBean.getCode() == 0) {

            mEmptyll.setVisibility(View.GONE);

            if (isLoadingMore) {
                mList.addAll(getLiveHomeBean.getResult());
                mAdapter.notifyDataSetChanged();
            } else {

                mList = getLiveHomeBean.getResult();
                mAdapter = new PersonalLiveHomeAdapter(getActivity(), mList);
                mAdapter.setListener(this);

//                if (getLiveHomeBean.getPage().getTotal() <= 10) {
//                    View v = View.inflate(getContext(), R.layout.main_list_no_datas, null);//main_list_item_foot_view
//                    mAdapter.setFooterView(v);
//                }
                mRecyclerView.setAdapter(mAdapter);

            }

        }
    }

    @Override
    public void getPersonalLiveDataFail(Throwable throwable) {
        UIUtils.showTip("服务端连接失败");
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void getPersonalLiveDataFinish() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void noNetwork() {
        UIUtils.showTip("请打开网络");
        mEmptyll.setVisibility(View.VISIBLE);
    }

    @Override
    public void payMoneySuccess(int position, GiveRewardBean giveRewardBean) {
        if (giveRewardBean.getCode() == 0) {
            ToolToast.buildToast(getActivity(), "支付成功", 1);
            Intent intent = new Intent(getActivity(), LivingPlayActivity.class);
            intent.putExtra(VedioContants.LivingPlayUrl, mList.get(position).getRtmpDownstreamAddress());
            intent.putExtra(VedioContants.ChannelId, mList.get(position).getChannelId());
            intent.putExtra(VedioContants.GroupID, mList.get(position).getAlipay() + "");
            intent.putExtra(VedioContants.HeadFace, HttpURL.IV_USER_HOST + mList.get(position).getHead() + "");
            intent.putExtra(VedioContants.ChannelName, mList.get(position).getChannelName());
            intent.putExtra(VedioContants.RoomImg, HttpURL.IV_PERSON_HOST+mList.get(position).getImg());
            intent.putExtra(VedioContants.GiftGroup, mList.get(position).getGiftGroup());
            intent.putExtra(VedioContants.Vid, mList.get(position).getId());
            intent.putExtra(VedioContants.Yid, mList.get(position).getUid());
            LogUtil.e(mList.get(position).getId()+"<"+VedioContants.Yid+mList.get(position).getUid());

            getActivity().startActivity(intent);
        } else {
            ToolToast.buildToast(getActivity(), "蛙豆不足", 1);
        }
    }


    @Override
    public void payMoneyFailure(Throwable throwable) {
        UIUtils.showTip(throwable.toString());
    }

    @Override
    public void rechargeMoneySuccess(int position, RechargeBean rechargeBean) {
        if (rechargeBean.getCode() == 0) {
            ToolToast.buildToast(getActivity(), "充值成功", 1);
            Intent intent = new Intent(getActivity(), LivingPlayActivity.class);
            intent.putExtra(VedioContants.LivingPlayUrl, mList.get(position).getRtmpDownstreamAddress());
            intent.putExtra(VedioContants.ChannelId, mList.get(position).getChannelId());
            intent.putExtra(VedioContants.GroupID, mList.get(position).getAlipay() + "");
            intent.putExtra(VedioContants.HeadFace, HttpURL.IV_USER_HOST + mList.get(position).getHead() + "");
            intent.putExtra(VedioContants.ChannelName, mList.get(position).getChannelName());
            intent.putExtra(VedioContants.RoomImg, HttpURL.IV_PERSON_HOST+mList.get(position).getImg());
            intent.putExtra(VedioContants.GiftGroup, mList.get(position).getGiftGroup());
            intent.putExtra(VedioContants.Vid, mList.get(position).getId());
            intent.putExtra(VedioContants.Yid, mList.get(position).getUid());
            LogUtil.e(mList.get(position).getId()+"<"+VedioContants.Yid+mList.get(position).getUid());
            getActivity().startActivity(intent);

        }
    }

    @Override
    public void rechargeMoneyFaiiure(Throwable ex) {
        UIUtils.showTip(ex.toString());

    }

    @Override
    public void onPayMoney(final int position) {

        new VirtuelPayPriceDialog(getActivity()).builder()
                .setPrice(String.valueOf(mList.get(position).getPrice()) + "蛙豆")
                .setHouseId(String.valueOf(mList.get(position).getId()))
                .setHouseLive(mList.get(position).getUsername())
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DemandPayDialog(getActivity()).builder()
                                .setCanceledOnTouchOutside(true)
                                .setDemandPayNumber(String.valueOf(mList.get(position).getPrice()))
                                .setPayTitle("影片名称：" + mList.get(position).getUsername())
                                .setDeleteListener("", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setPayListener("", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mPresenter.onPayMoney(mList.get(position).getPrice(), position, mList);
                                    }
                                })
                                .setRechargeListener("", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        new RechargeDialog(getActivity()).builder()
                                                .setCanceledOnTouchOutside(true)
                                                .setPayListener("", new RechargeDialog.IChargeMoney() {
                                                    @Override
                                                    public void goChargeMoney(int money) {
                                                        LogUtil.i("money = " + money);
                                                        mPresenter.onRechargeMoney(money);
                                                    }
                                                })
                                                .setDeleteListener("", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                }).show();

                                    }
                                }).show();
                    }
                }).show();

    }
}
