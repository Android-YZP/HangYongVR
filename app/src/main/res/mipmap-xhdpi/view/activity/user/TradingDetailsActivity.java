package com.mytv365.view.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshScrollView;
import com.mytv365.R;
import com.mytv365.entity.Trading;

/**
 * 交易记录详情
 * Created by zhangguohao on 16/8/26.
 */
public class TradingDetailsActivity extends BaseActivity {
    private Context mContext;
    /*滚动条*/
    private ScrollView scrollView;
    /*下拉刷新*/
    private PullToRefreshScrollView mPullScrollView;
    /*内容界面*/
    private LinearLayout parent;


    /*状态*/
    private TextView states;
    /*名称*/
    private TextView name;
    /*流水号*/
    private TextView number;
    /*交易类型*/
    private TextView type;
    /*交易时间*/
    private TextView time;
    /*价格*/
    private TextView price;

    private Trading trading;

    @Override
    public int bindLayout() {
        return R.layout.public_scroll_view;
    }

    @Override
    public void initParms(Bundle parms) {
//        trading= (Trading)getOperation().getParameters().get("dto");
        trading = (Trading) parms.getSerializable("dto");
    }

    @Override
    public void initView(View view) {
        iniTitle("交易详情");


    }

    @Override
    public void doBusiness(final Context mContext) {
        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.activity_trading_details, null);
        mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
        mPullScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ScrollView> refreshView) {
                        String label = DateUtils.formatDateTime(mContext,
                                System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME
                                        | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy()
                                .setLastUpdatedLabel(label);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy()
                                .setLastUpdatedLabel(label);
                        // foundPageIndex = 1;
                        mPullScrollView.onRefreshComplete();
                    }


                });
        /***
         * 状态发生改变时候调用
         */
        mPullScrollView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
            }
        });

        scrollView = mPullScrollView.getRefreshableView();
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(parent);

        states = (TextView) parent.findViewById(R.id.state);
        name = (TextView) parent.findViewById(R.id.name);
        number = (TextView) parent.findViewById(R.id.number);
        type = (TextView) parent.findViewById(R.id.type);
        time = (TextView) parent.findViewById(R.id.time);
        price = (TextView) parent.findViewById(R.id.price);


        switch (trading.getState()) {
            case "2":
                states.setText("支付成功");
                break;
        }
        switch (trading.getGatewayId()) {
            case "13":
                type.setText("网银");
                break;
            case "14":
                type.setText("微信");
                break;
        }
        name.setText(trading.getTypeString());
        price.setText("￥ " + trading.getPrice());
        time.setText(Tool.timeToString(trading.getTime(), "yyyy-MM-dd HH:mm:ss"));
        number.setText(trading.getOrderId());


    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }
}
