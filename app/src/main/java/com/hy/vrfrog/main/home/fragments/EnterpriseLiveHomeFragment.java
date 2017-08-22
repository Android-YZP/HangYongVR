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
import com.hy.vrfrog.main.home.adapters.EnterpriseOnLiveAdapter;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.ui.DemandPayDialog;
import com.hy.vrfrog.ui.RechargeDialog;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;
import com.hy.vrfrog.ui.VirtuelPayPriceDialog;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.ToolToast;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by qwe on 2017/8/4.
 */
@SuppressLint("ValidFragment")
public class EnterpriseLiveHomeFragment extends Fragment implements EnterpriseLiveContract.EnterpriseLiveView,
        EnterpriseOnLiveAdapter.IEnterpriseLiveAdapter{

    private LinearLayout mEmptyll;
    private VerticalSwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private EnterpriseOnLiveAdapter mAdapter;
    private List<GetLiveHomeBean.ResultBean> mList ;
    private int pager = 1;
    private GetLiveHomeBean getLiveHomeBean;
    private boolean isLoadingMore;
    private EnterpriseLivePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_live_home, container, false);
        initView(view);
//        initData(pager);
        mPresenter.getEnterpriseLiveData(pager, 10, 1);
        initListener();
        return view;
    }

    private void initListener() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 pager = 1 ;
                if (mList.size() != 0){
                    mList.clear();
                }
//                initData(pager);
                mPresenter.getEnterpriseLiveData(pager, 10, 1);

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean visBottom = UIUtils.isVisBottom(mRecyclerView);
                if (visBottom) {
                    if (mAdapter.getFooterView() == null) {
                        ++ pager;
                        isLoadingMore = true;
//                        initData(pager);
                        mPresenter.getEnterpriseLiveData(pager, 10, 1);
                    } else {
                        return;
                    }

                    if (getLiveHomeBean.getPage().getTotal() <= pager) {
                        View v = View.inflate(getContext(), R.layout.main_list_no_datas, null);//main_list_item_foot_view
                        mAdapter.setFooterView(v);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    private void initView(View view) {

        mEmptyll = (LinearLayout)view.findViewById(R.id.ll_live_home_no_data);
        mSwipeRefresh = (VerticalSwipeRefreshLayout)view.findViewById(R.id.vsr_live_home__refresh);
//        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_live_home_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        new EnterpriseLivePresenter(this);


    }

    private void initData(int pager) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            mEmptyll.setVisibility(View.VISIBLE);
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.AllLive);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("sourceNum", "111");//
        requestParams.addBodyParameter("page", pager + "");//
        requestParams.addBodyParameter("count", 10 +"");//
        requestParams.addBodyParameter("type", 1 + "");//

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("企业直播---------------", result);
                getLiveHomeBean = new Gson().fromJson(result, GetLiveHomeBean.class);


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                UIUtils.showTip("企业直播----服务端连接失败");
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFinished() {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void getEnterpriseLiveDataSuccess(GetLiveHomeBean getLiveHomeBean) {
        if (getLiveHomeBean.getCode() == 0) {
            mEmptyll.setVisibility(View.GONE);

            if (isLoadingMore) {
                mList.addAll(getLiveHomeBean.getResult());
                mAdapter.notifyDataSetChanged();
            } else {
                mList = getLiveHomeBean.getResult();
                mAdapter = new EnterpriseOnLiveAdapter(getActivity(),mList);
                mAdapter.setEnterpriseLiveListener(this);
                if (getLiveHomeBean.getPage().getTotal() <= 10) {
                    View v = View.inflate(getContext(), R.layout.main_list_no_datas, null);//main_list_item_foot_view
                    mAdapter.setFooterView(v);
                }
                mRecyclerView.setAdapter(mAdapter);

            }

        }
    }

    @Override
    public void getEnterpriseLiveDataFail(Throwable throwable) {
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void getEnterpriseLiveDataFinish() {
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
        UIUtils.showTip("网络异常");
    }

    @Override
    public void rechargeMoneySuccess(final int position, RechargeBean rechargeBean) {
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
        }else {
            ToolToast.buildToast(getActivity(), "充值失败", 1);
        }

    }

    @Override
    public void rechargeMoneyFaiiure(Throwable ex) {
        UIUtils.showTip("网络异常");

    }

    @Override
    public void setPresenter(EnterpriseLiveContract.Presenter presenter) {
         this.mPresenter = (EnterpriseLivePresenter) presenter;
    }

    @Override
    public boolean isActive() {
        return false;
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
                                .setPayId("ID:" + mList.get(position).getId() + "")
                                .setDemandPayNumber(String.valueOf(mList.get(position).getPrice()))
                                .setPayTitle("主播:" + mList.get(position).getUsername())
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

    @Override
    public void onReViewPLay(int position) {
        if (mList.get(position).getLvbChannelRecords().size() != 0){
            Intent intent = new Intent(getActivity(), LivingPlayActivity.class);
            intent.putExtra(VedioContants.LivingPlayUrl, mList.get(position).getLvbChannelRecords().get(0).getFileurl());
            intent.putExtra(VedioContants.ChannelId, mList.get(position).getLvbChannelRecords().get(0).getLvbchannelid());
            intent.putExtra(VedioContants.ChannelName, mList.get(position).getLvbChannelRecords().get(0).getRecordname());
            intent.putExtra(VedioContants.RoomImg, HttpURL.IV_PERSON_HOST + mList.get(position).getLvbChannelRecords().get(0).getImg());
            intent.putExtra(VedioContants.Vid, mList.get(position).getLvbChannelRecords().get(0).getId());

            LogUtil.e(mList.get(position).getId() + "<" + VedioContants.Yid + mList.get(position).getUid());

            getActivity().startActivity(intent);
        }else {
            UIUtils.showTip("无回看记录");
        }
    }
}
