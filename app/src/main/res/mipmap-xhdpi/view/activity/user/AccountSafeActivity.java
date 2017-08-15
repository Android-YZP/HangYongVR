package com.mytv365.view.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshListView;
import com.mytv365.R;
import com.mytv365.adapter.listview.ListViewSetUpAdapter;
import com.mytv365.entity.Up;


/***
 * 个人中心子模块
 * 账号与安全
 *
 * @author 阳志
 * @date 2016年8月11日 下午9:20:00
 */
public class AccountSafeActivity extends BaseActivity implements View.OnClickListener {
    /*加载界面*/
    private ProgressActivity progressActivity;
    /*带刷新的ListView*/
    private ListView listView;
    private PullToRefreshListView mPullListView;
    /* 设置数据*/
    private BaseMAdapter<Up> adapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_account_safe;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        iniTitle("帐户与安全");
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        progressActivity.showContent();
        mPullListView = (PullToRefreshListView) findViewById(R.id.listlView);
        listView = mPullListView.getRefreshableView();
        listView.setVerticalScrollBarEnabled(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        initListView(mContext);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    startActivityForResult(new Intent(com.mytv365.view.activity.user.AccountSafeActivity.this, com.mytv365.view.activity.user.PhoneBindActivity.class), 1);
                } else if (i == 2) {
                    startActivityForResult(new Intent(com.mytv365.view.activity.user.AccountSafeActivity.this, UserNameAuthActivity.class), 2);
                } else if (i == 3) {
                    startActivity(new Intent(com.mytv365.view.activity.user.AccountSafeActivity.this, ChangePasswordActivity.class));
                }
            }
        });
    }

    /***
     * 初始化数据
     */
    private void initListView(final Context mContex) {
        adapter = new BasicDataAdapter<Up>(new ListViewSetUpAdapter(mContex));
        boolean[] right = new boolean[]{true, true, true};
        // 是否显示左边图标
        boolean[] left = new boolean[]{false, false, false};
        String names[] = {"手机号", "实名认证", "修改密码"};
        String rightNames[] = {"已绑定", "未认证", null};
        for (int i = 0; i < names.length; i++) {
            Up up = new Up(names[i], rightNames[i], left[i], right[i], 0);
            adapter.addItem(up);
        }
        mPullListView.setAdapter(adapter);
        mPullListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        mPullListView.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                mPullListView.onRefreshComplete();
                            }
                        }, 1000);
                    }
                });
    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }

    @Override
    public void onClick(View view) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //绑定成功
        }
    }
}
