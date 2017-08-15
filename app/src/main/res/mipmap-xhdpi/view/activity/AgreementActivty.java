package com.mytv365.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.mytv365.R;

/**
 * 协议相关
 * Created by zhangguohao on 16/8/19.
 */
public class AgreementActivty extends BaseActivity {
    private Context mContext;

    private CheckBox agreement1;
    private CheckBox agreement2;
    private CheckBox agreement3;
    private CheckBox agreement4;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    private TextView no;
    private TextView ok;

    @Override
    public int bindLayout() {
        return R.layout.activity_agreement;
    }

    @Override
    public void initParms(Bundle parms) {

    }


    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle("服务协议");
        agreement1 = (CheckBox) findViewById(R.id.agreement1);
        agreement2 = (CheckBox) findViewById(R.id.agreement2);
        agreement3 = (CheckBox) findViewById(R.id.agreement3);
        agreement4 = (CheckBox) findViewById(R.id.agreement4);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        textView4 = (TextView) findViewById(R.id.text4);

        no = (TextView) findViewById(R.id.no);
        ok = (TextView) findViewById(R.id.ok);


    }

    /***
     * 初始化标题
     *
     * @param title
     */
    private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();

    }

    @Override
    public void doBusiness(Context mContext) {
        this.mContext = mContext;
        initText();
        initOnClick();
    }

    private void initOnClick() {
        textView1.setOnClickListener(new MyOnClick(1));
        textView2.setOnClickListener(new MyOnClick(2));
        textView3.setOnClickListener(new MyOnClick(3));
        no.setOnClickListener(new MyOnClick(4));
        ok.setOnClickListener(new MyOnClick(5));
    }

    /***
     * 初始化Text相关样式
     */
    private void initText() {
        SpannableStringBuilder builder = Tool.textViewColor(textView1.getText()
                .toString(), 10, 16, ContextCompat
                .getColor(mContext, R.color.theme_select));
        textView1.setText(builder);

        builder = Tool.textViewColor(textView2.getText()
                .toString(), 10, 16, ContextCompat
                .getColor(mContext, R.color.theme_select));
        textView2.setText(builder);
        builder = Tool.textViewColor(textView3.getText()
                .toString(), 16, 31, ContextCompat
                .getColor(mContext, R.color.theme_select));
        textView3.setText(builder);
    }


    /**
     * 点击事件
     */
    private class MyOnClick implements View.OnClickListener {
        private int item;

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1://客户须知
                    getOperation().addParameter("url", "http://www.bdcgw.cn/xieyi/xieyi1.html");
                    getOperation().addParameter("isShowShare", false);
                    getOperation().forward(WebPageActivity.class);
                    break;
                case 2://风险提示
                    getOperation().addParameter("url", "http://www.bdcgw.cn/xieyi/xieyi2.html");
                    getOperation().addParameter("isShowShare", false);
                    getOperation().forward(WebPageActivity.class);
                    break;
                case 3://智能投顾
                    getOperation().addParameter("url", "http://www.bdcgw.cn/xieyi/xieyi3.html");
                    getOperation().addParameter("isShowShare", false);
                    getOperation().forward(WebPageActivity.class);
                    break;
                case 4://不同意
                    finish();
                    break;
                case 5://同意
                    if (agreement1.isChecked() == false && agreement2.isChecked() == false && agreement3.isChecked() == false && agreement4.isChecked() == false) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(com.mytv365.view.activity.AgreementActivty.this);
                        preferences.edit().putBoolean("agree_clause", true).commit();
                        ToolAlert.toastShort("购买");
                        finish();
                    } else {
                        ToolAlert.toastShort("请勾选服务协议");
                    }
                    break;
            }
        }
    }
}
