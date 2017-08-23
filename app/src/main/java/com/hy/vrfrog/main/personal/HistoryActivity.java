package com.hy.vrfrog.main.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GetHistoryBean;
import com.hy.vrfrog.http.responsebean.SeeHistory;
import com.hy.vrfrog.utils.DialogUtils;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.main.home.adapters.HistoryListAdapter;
import com.hy.vrfrog.ui.SwipeBackActivity;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wzq930102 on 2017/7/11.
 */
public class HistoryActivity extends SwipeBackActivity {
    private HistoryListAdapter adapter;
    private ImageView mTvReturn;
    private RelativeLayout mReDialog;
    private List<SeeHistory> seeHistory;
    private RecyclerView mRecycler;
    private SwipeRefreshLayout mRecyclerRefresh;
    private User user;
    private TimerTask task;
    private int recLen;
    private Timer mTimer;
    private List<GetHistoryBean.ResultBean> resultData;
    final int RESULT_CODE = 101;
    private TextView mTvhistorydelete;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        initView();
        initData();
        initListener();
        mContext = this;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.i("onActivityResult" + "requestCode" + requestCode + "\n resultCode=" + resultCode);

        if (resultCode == RESULT_CODE) {
            int position = data.getIntExtra("position", 0);
            if (resultData != null && resultData.size() > 0) {
                resultData.get(requestCode).setWatchTime(position);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resultData != null)
            resultData.clear();
    }

    private void initListener() {
        mTvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
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

        mTvhistorydelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetUtil.isOpenNetwork()) {
                    UIUtils.showTip("请打开网络");
                } else {
                    HttpDeleteHistory(null);
                    mRecycler.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {
        mTvReturn = (ImageView) findViewById(R.id.tv_history_return);
//        mReDialog = ((RelativeLayout) findViewById(R.id.rl_history_list));
        mRecycler = (RecyclerView) findViewById(R.id.history_list);
        mRecyclerRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_history_refreshing);
        mTvhistorydelete = (TextView) findViewById(R.id.tv_history_delete);
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
        requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken() + "");
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
                    if (resultData == null) return;
                    adapter = new HistoryListAdapter(HistoryActivity.this, resultData);
                    mRecycler.setAdapter(adapter);
                    //添加长点击事件
                    adapter.setOnItemClickListener(new HistoryListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            showToastStyleDialog(position);
                        }
                    });
                    mRecycler.setVisibility(View.VISIBLE);
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
     * 删除用户历史观看数据
     */
    private void HttpDeleteHistory(String vid) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        LogUtil.i("历史观看token =" + SPUtil.getUser().getResult().getUser().getToken());
        LogUtil.i("历史观看vid =" + vid);
        LogUtil.i("历史观看uid =" + SPUtil.getUser().getResult().getUser().getUid());



        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.DeleteHistory);
        requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken() + "");
        //包装请求参数
        requestParams.addBodyParameter("uid", SPUtil.getUser().getResult().getUser().getUid() + "");//用户id
        if (vid != null && vid != "") {
            requestParams.addBodyParameter("vid", vid);//用户id
        }
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i("历史观看---------------" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {

            }

        });
    }

    /**
     * 视频播放本地缓存
     */

    private void showToastStyleDialog(final int position) {

        final DialogUtils dialogUtils = new DialogUtils(HistoryActivity.this);
        View contentView = LayoutInflater.from(HistoryActivity.this).inflate(
                R.layout.history_delete, null);
        dialogUtils.setContentView(contentView);
        dialogUtils.setGravity(Gravity.BOTTOM);
        dialogUtils.setXY(100, 100);
        dialogUtils.show();
        Button delete = (Button) contentView.findViewById(R.id.btn_history_delete);
        Button cancel = (Button) contentView.findViewById(R.id.btn_history_cancel);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!NetUtil.isOpenNetwork()) {
                    UIUtils.showTip("请打开网络");
                } else {
                    LogUtil.i("vid = " + resultData.get(position).getVid());
                    HttpDeleteHistory(resultData.get(position).getVid() + "");
                    resultData.remove(position);
                    adapter.notifyDataSetChanged();
                }
                dialogUtils.getBaseDialog().dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogUtils.getBaseDialog().dismiss();
            }
        });
    }

}
