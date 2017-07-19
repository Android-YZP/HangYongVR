package com.jt.base.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.application.User;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.GetHistoryBean;
import com.jt.base.http.responsebean.SeeHistory;
import com.jt.base.utils.LocalUtils;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videos.adapters.HistoryListAdapter;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wzq930102 on 2017/7/11.
 */
public class HistoryActivity extends AppCompatActivity {
    private HistoryListAdapter adapter;
    private TextView mTvReturn;
    private RelativeLayout mReDialog;
    private List<SeeHistory> seeHistory;
    private RecyclerView mRecycler;
    private SwipeRefreshLayout mRecyclerRefresh;
    private User user;
    private TimerTask task;
    private int recLen;
    private Timer mTimer;
    private List<GetHistoryBean.ResultBean> resultData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();
        initData();
        initListener();

    }

    private void initData() {
        user = SPUtil.getUser();
        if (user != null) {
            HttpGetHistory(user.getResult().getUser().getUid() + "");
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRecycler.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initListener() {
        mTvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerRefresh.setRefreshing(false);
                if (user != null) {
                    HttpGetHistory(user.getResult().getUser().getUid() + "");
                }

            }
        });
    }

    private void initView() {
        mTvReturn = ((TextView) findViewById(R.id.tv_history_return));
//        mReDialog = ((RelativeLayout) findViewById(R.id.rl_history_list));
        mRecycler = (RecyclerView) findViewById(R.id.history_list);
        mRecyclerRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_history_refreshing);

    }


    /**
     * 保存用户历史观看数据
     */
    private void HttpGetHistory(String uid) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.GetHistory);
        requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getUid() + "");
        //包装请求参数
        requestParams.addBodyParameter("uid", uid);//用户id
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                GetHistoryBean HistoryBean = new Gson().fromJson(result, GetHistoryBean.class);
                if (HistoryBean.getCode() == 0) {
                    resultData = HistoryBean.getResult();
                    adapter = new HistoryListAdapter(HistoryActivity.this, resultData);
                    mRecycler.setAdapter(adapter);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
                mRecyclerRefresh.setRefreshing(false);
            }

        });
    }

    /**
     * 计时重新发送验证码
     */
    private void timekeeping() {
        recLen = 1;
        mTimer = new Timer();
        // UI thread
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        recLen--;
                        mRecyclerRefresh.setRefreshing(true);
                        if (recLen < 0) {
                            mTimer.cancel();
                            if (user != null) {
                                HttpGetHistory(user.getResult().getUser().getUid() + "");
                            }

                        }
                    }
                });
            }
        };
        //从现在起过10毫秒以后，每隔1000毫秒执行一次。
        mTimer.schedule(task, 4000, 1000);    // timeTask

    }

}
