package com.mytv365.view.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshListView;
import com.mytv365.R;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/31
 * Description:
 */
public class MessageActivity extends BaseActivity {
    /*加载界面*/
    private ProgressActivity progressActivity;
    /*带刷新的ListView*/
    private ListView listView;
    private PullToRefreshListView mPullListView;
    @Override
    public int bindLayout() {
        return R.layout.activity_list_content;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        iniTitle("我的消息");
        initRightDoneBtn(R.drawable.delete_light, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }
}
