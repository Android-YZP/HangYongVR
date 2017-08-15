package com.hy.vrfrog.main.home.fragments;


import android.annotation.SuppressLint;
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
import com.hy.vrfrog.main.home.adapters.EnterpriseLiveAdapter;
import com.hy.vrfrog.main.home.adapters.EnterpriseOnLiveAdapter;
import com.hy.vrfrog.main.home.adapters.PersonalLiveHomeAdapter;
import com.hy.vrfrog.ui.VerticalSwipeRefreshLayout;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2017/8/4.
 */
@SuppressLint("ValidFragment")
public class EnterpriseLiveHomeFragment extends Fragment {

    private LinearLayout mEmptyll;
    private VerticalSwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private EnterpriseOnLiveAdapter mAdapter;
    private List<GetLiveHomeBean.ResultBean> mList  ;
    private int pager = 1;
    private GetLiveHomeBean getLiveHomeBean;
    private boolean isLoadingMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_live_home, container, false);
        initView(view);
        initData(pager);
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
                initData(pager);
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
                        initData(pager);
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
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv_live_home_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));


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
                LongLogUtil.e("个人直播---------------", result);
                getLiveHomeBean = new Gson().fromJson(result, GetLiveHomeBean.class);
                if (getLiveHomeBean.getCode() == 0) {
                    mEmptyll.setVisibility(View.GONE);

                    if (isLoadingMore) {
                        mList.addAll(getLiveHomeBean.getResult());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        LogUtil.e("企业直播 =" + getLiveHomeBean.getResult());
                        mList = getLiveHomeBean.getResult();
                        mAdapter = new EnterpriseOnLiveAdapter(getActivity(),mList);

                        if (getLiveHomeBean.getPage().getTotal() <= 10) {
                            View v = View.inflate(getContext(), R.layout.main_list_no_datas, null);//main_list_item_foot_view
                            mAdapter.setFooterView(v);
                        }
                        mRecyclerView.setAdapter(mAdapter);

                    }

                }
                LogUtil.i("个人直播 = " + getLiveHomeBean.getResult());
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFinished() {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}
