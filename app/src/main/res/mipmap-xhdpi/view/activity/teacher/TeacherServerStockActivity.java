package com.mytv365.view.activity.teacher;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.R;
import com.mytv365.entity.Stock;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/26
 * Description:
 */

public class TeacherServerStockActivity extends BaseActivity {
    private Stock stock;
    private TextView tv_date;
    private TextView tv_title;
    private TextView tv_status;
    private TextView tv_section;
    private TextView tv_suggest;
    private TextView tv_zhisun;
    private TextView tv_zhiying;
    private TextView tv_recommend;
    private TextView tv_content;

    @Override
    public int bindLayout() {
        return R.layout.activity_service_stock;
    }

    @Override
    public void initParms(Bundle parms) {
        stock = (Stock) parms.getSerializable("stock");
    }

    @Override
    public void initView(View view) {
        iniTitle("股票池");
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_title = (TextView) findViewById(R.id.tv_mytitle);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_section = (TextView) findViewById(R.id.tv_section);
        tv_suggest = (TextView) findViewById(R.id.tv_suggest);
        tv_zhisun = (TextView) findViewById(R.id.tv_zhisun);
        tv_zhiying = (TextView) findViewById(R.id.tv_zhiying);
        tv_recommend = (TextView) findViewById(R.id.tv_recommend);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (stock != null) {
            tv_title.setText(stock.getStockName());
            tv_status.setText(stock.getHoldingStatus());
            tv_date.setText(stock.getCreateTime() + "");

            tv_section.setText(stock.getPriceLowest() + " - " + stock.getPriceHighest());
            tv_suggest.setText(stock.getHoldingPercent() + "%");
            tv_zhisun.setText(stock.getStopLoss() + "");
            tv_zhiying.setText(stock.getStopGain() + "");

            tv_recommend.setText(stock.getRecommendedPerson());
            tv_content.setText(Html.fromHtml(stock.getRecommendation()));
        }
    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }
}
