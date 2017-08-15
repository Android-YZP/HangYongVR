package com.mytv365.view.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshListView;
import com.mytv365.R;
import com.mytv365.adapter.listview.TradingAdapter;
import com.mytv365.common.Constant;
import com.mytv365.entity.Trading;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 交易记录
 * Created by zhangguohao on 16/8/25.
 */
public class TradingActivity extends BaseActivity {
    /*加载界面*/
    private ProgressActivity progressActivity;
    /*带刷新的ListView*/
    private ListView listView;
    private PullToRefreshListView mPullListView;
    /* 设置数据*/
    private BaseMAdapter<Trading> adapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_trading;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        iniTitle("交易记录");
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        mPullListView = (PullToRefreshListView) findViewById(R.id.listlView);
        listView = mPullListView.getRefreshableView();
        listView.setVerticalScrollBarEnabled(false);
//        listView.setDividerHeight(20);
        listView.setEmptyView(findViewById(R.id.emptyElement));
    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }

    @Override
    public void doBusiness(Context mContext) {
        adapter = new BasicDataAdapter<Trading>(new TradingAdapter(mContext));
        listView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        UserServer.trading(null, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progressActivity.showLoading();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressActivity.showContent();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progressActivity.showContent();
                try {
                    Log.i("=====",responseString);
                    JSONObject jsonObject=new JSONObject(responseString);
                   if(jsonObject.getString("type").equals("1")){
                       JSONObject obj=jsonObject.getJSONObject("obj");
                       JSONArray jsonArray=obj.getJSONArray("arrList");
                       for (int i=0;i<jsonArray.length();i++){
                           Trading trading=new Trading();
                           JSONObject object=jsonArray.getJSONObject(i);
                           trading.setTime(object.getString("starttime"));
                           trading.setPrice(object.getString("paymony"));
                           trading.setTypeString(object.getString("typeString"));
                           trading.setState(object.getString("state"));
                           trading.setOrderId(object.getString("orderid"));
                           trading.setGatewayId(object.getString("gatewayid"));
                           adapter.addItem(trading);
                           adapter.notifyDataSetChanged();
                       }
                   }else{
                       ToolAlert.toastShort(jsonObject.getString("msg"));
                   }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, Constant.userinfo.getToken(), Constant.userinfo.getUserid());
    }
}
