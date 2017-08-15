package com.mytv365.view.activity.teacher;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.R;
import com.mytv365.entity.TeacherView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/26
 * Description:
 */

public class TeacherServerViewActivity extends BaseActivity {
    private TeacherView teacherView;
    private TextView tv_date;
    private TextView tv_title;
    private TextView tv_content;

    @Override
    public int bindLayout() {
        return R.layout.activity_service_view;
    }

    @Override
    public void initParms(Bundle parms) {
        teacherView = (TeacherView) parms.getSerializable("teacherView");
    }

    @Override
    public void initView(View view) {
        iniTitle("老师观点");
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_title = (TextView) findViewById(R.id.tv_mytitle);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void doBusiness(Context mContext) {
//        UserServer.getView(this, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//            }
//        }, roomId, viewId, Constant.token);
        if (teacherView != null) {
            tv_date.setText(teacherView.getCreateTime() + "");
            tv_title.setText(teacherView.getTitle());
            tv_content.setText(Html.fromHtml(teacherView.getRecommendation()));
        }

    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }
}
