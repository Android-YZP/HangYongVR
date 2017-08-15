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
import com.mytv365.adapter.listview.MyServiceAdapter;
import com.mytv365.common.Constant;
import com.mytv365.entity.MyService;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 我的服务
 * Created by zhangguohao on 16/8/25.
 */
public class MyServiceActivity extends BaseActivity {
    /*加载界面*/
    private ProgressActivity progressActivity;
    /*带刷新的ListView*/
    private ListView listView;
    private PullToRefreshListView mPullListView;
    /* 设置数据*/
    private BaseMAdapter<MyService> adapter;


    @Override
    public int bindLayout() {
        return R.layout.activity_list_content;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        iniTitle("我的服务");
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);

        mPullListView = (PullToRefreshListView) findViewById(R.id.listlView);
        listView = mPullListView.getRefreshableView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(20);
        listView.setEmptyView(findViewById(R.id.emptyElement));

    }

    @Override
    public void doBusiness(Context mContext) {
        adapter = new BasicDataAdapter<MyService>(new MyServiceAdapter(mContext));
        getData();
    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }

    private void getData() {
        UserServer.myServet(null, new TextHttpResponseHandler() {
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
                    Log.i("=====", responseString);
                    JSONObject object = new JSONObject(responseString);
                    if (object.getString("type").equals("1")) {
                        JSONObject obj = object.getJSONObject("obj");
                        JSONArray jsonArray = obj.getJSONArray("resultList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            MyService myService = new MyService();
                            myService.setName(jsonObject.getString("teacherName"));
                            myService.setRoomType(jsonObject.getString("roomType"));
                            myService.setStartTime(jsonObject.getString("startServiceTime"));
                            myService.setEndTime(jsonObject.getString("endServiceTime"));
                            myService.setServerId(jsonObject.getString("serviceID"));
                            myService.setState(jsonObject.getString("statestr"));
                            adapter.addItem(myService);
                        }
                        listView.setAdapter(adapter);
                    } else {
                        ToolAlert.toastShort(object.getString("msg"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "1", "10", Constant.userinfo.getToken(), Constant.userinfo.getUserid());
    }
}
