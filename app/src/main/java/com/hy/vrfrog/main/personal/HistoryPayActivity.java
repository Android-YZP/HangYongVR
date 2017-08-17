package com.hy.vrfrog.main.personal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.HistoryPayBean;
import com.hy.vrfrog.ui.LoadingDataUtil;
import com.hy.vrfrog.ui.SwipeBackActivity;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videos.adapters.HistoryPayAdapter;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by wzq930102 on 2017/7/31.
 */
public class HistoryPayActivity extends SwipeBackActivity {
    private ImageView mBack;
    private HistoryPayAdapter adapter;
    private RecyclerView mRecycler;
    private ArrayList<HistoryPayBean>mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pay);
        initData();
        initView();
        initListener();
    }

    private void initData() {
//        mList = new ArrayList<>();
//        for (int i = 0 ; i < 20 ; i ++){
//            HistoryPayBean bean = new HistoryPayBean();
//            bean.setDemand("demand");
//            bean.setObject("object");
//            bean.setPrice("price");
//            bean.setTime("time");
//            bean.setTitle("title");
//            mList.add(bean);
//        }

        LoadingDataUtil.startLoad("正在加载...");

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        RequestParams requestParams = new RequestParams(HttpURL.RechargeRecode);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addHeader("uid", SPUtil.getUser().getResult().getUser().getUid() + "");
        requestParams.addHeader("count", 10 + "");
        requestParams.addHeader("page", 1 + "");

        LogUtil.i("用户id = " + SPUtil.getUser().getResult().getUser().getUid());



        //包装请求参数
//        requestParams.addBodyParameter("sourceNum", "111");//


        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("个人支付---------------", result);




            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");

            }

            @Override
            public void onFinished() {
                LoadingDataUtil.stopLoad();
            }
        });


    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });
    }

    private void initView() {
        mBack = ((ImageView) findViewById(R.id.tv_history_pay_return));
        mRecycler = (RecyclerView) findViewById(R.id.my_pay_list);
        mRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new HistoryPayAdapter(this,mList);
        mRecycler.setAdapter(adapter);
    }

}
