package com.mytv365.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.tools.ToolResource;
import com.mytv365.R;
import com.mytv365.common.TabManager;
import com.mytv365.view.fragment.mian.MainFragment03;

public class MainActivity extends BaseActivity implements TabManager.ChangeTable {
    private Context context;

    /**
     * 主页
     */
    private com.mytv365.view.fragment.mian.MainFragment01 fragment01;
    /**
     * 名师
     */
    private com.mytv365.view.fragment.mian.MainFragment02 fragment02;
    /**
     * 课件
     */
    private MainFragment03 fragment03;
    /**
     * 我的
     */
    private com.mytv365.view.fragment.mian.MainFragment04 fragment04;

    public String fragment01_text = "fragment01";
    public String fragment02_text = "fragment02";
    public String fragment03_text = "fragment03";
    public String fragment04_text = "fragment04";

    /**
     * 主页的table
     */
    private RelativeLayout fragment01_index;
    /**
     * 名师的table
     */
    private RelativeLayout fragment02_index;
    /**
     * 课件的table
     */
    private RelativeLayout fragment03_index;
    /**
     * 我的的table
     */
    private RelativeLayout fragment04_index;

    /**
     * 分类的tabhost
     */
    private TabHost tabHost;
    /**
     * tab管理
     */
    private TabManager mTabManager;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle(getResources().getString(ToolResource.getStringId(fragment01_text)));
        hiddeTitleBar();

        tabHost = (TabHost) findViewById(android.R.id.tabhost);

        // 初始化Fragment
        /** 主页*/
        fragment01 = new com.mytv365.view.fragment.mian.MainFragment01();
        /** 名师 */
        fragment02 = new com.mytv365.view.fragment.mian.MainFragment02();
        /** 课件 */
        fragment03 = new MainFragment03();
        /** 我的 */
        fragment04 = new com.mytv365.view.fragment.mian.MainFragment04();
    }

    @Override
    public void doBusiness(Context mContext) {
        this.context = mContext;
        initTab();

    }

    /**
     * 初始化tab选项卡
     */
    public void initTab() {
        tabHost.setup();

        if (mTabManager == null) {
            mTabManager = new TabManager(this, null, tabHost,
                    android.R.id.tabcontent, context, this);
        }
        // 主页
        fragment01_index = initTabItem(R.drawable.main_tabstyle01,
                R.string.fragment01);
        // 名师
        fragment02_index = initTabItem(R.drawable.main_tabstyle02, R.string.fragment02);
        // 课件
        fragment03_index = initTabItem(R.drawable.main_tabstyle03, R.string.fragment03);
        // 我的
        fragment04_index = initTabItem(R.drawable.main_tabstyle04, R.string.fragment04);

        mTabManager.addTab(
                tabHost.newTabSpec(fragment01_text).setIndicator(fragment01_index),
                fragment01, null);
        mTabManager.addTab(tabHost.newTabSpec(fragment02_text).setIndicator(fragment02_index),
                fragment02, null);
        mTabManager.addTab(tabHost.newTabSpec(fragment03_text).setIndicator(fragment03_index),
                fragment03, null);
        mTabManager.addTab(tabHost.newTabSpec(fragment04_text).setIndicator(fragment04_index),
                fragment04, null);
        tabHost.setCurrentTab(0);

    }

    /***
     * 初始化标题
     */
    public void initTitle(String title) {
        setWindowTitle(title, Gravity.CENTER);
        showTitleBar();
    }

    /***
     * 设置tab的item头部
     *
     * @param drawable_res 图片资源
     * @param string_res   文字资源
     * @return
     */
    public RelativeLayout initTabItem(int drawable_res, int string_res) {
        RelativeLayout layout = null;
        try {
            layout = (RelativeLayout) getLayoutInflater().inflate(
                    R.layout.public_item, null);
            ImageView index_icon = (ImageView) layout
                    .findViewById(R.id.item_icon);
            TextView index_name = (TextView) layout
                    .findViewById(R.id.item_name);
            index_name.setText(context.getResources().getString(string_res));

            index_icon.setBackgroundResource(drawable_res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }

    @Override
    public void getChangeTitle(String title) {
    }


}
