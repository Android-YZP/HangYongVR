package com.mytv365.view.fragment.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.tools.Tool;
import com.mytv365.R;
import com.mytv365.adapter.listview.TeacherViewAdapter;
import com.mytv365.entity.TeacherView;

import java.util.ArrayList;
import java.util.List;

/**
 * 观点
 * Created by zhangguohao on 16/8/22.
 */
public class TeacherViewFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private BaseMAdapter<TeacherView> adapter;
    private com.mytv365.view.activity.teacher.TeacherServerActivity teacherServerActivity;
    private List<TeacherView> teacherViews;

    @Override
    public int bindLayout() {
        return R.layout.list_view;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        teacherViews = new ArrayList<>();
        teacherServerActivity = (com.mytv365.view.activity.teacher.TeacherServerActivity) getActivity();
        listView = (ListView) findViewById(R.id.listview);
        adapter = new BasicDataAdapter<TeacherView>(new TeacherViewAdapter(mContext));

    }

    @Override
    public void doBusiness(Context mContext) {
        adapter.addItem(teacherViews);
    }

    public void setData(List<TeacherView> teacherViews) {
        adapter.clear();
        this.teacherViews = teacherViews;
        adapter.addItem(teacherViews);
        listView.setAdapter(adapter);
        Tool.setListViewHeightBasedOnChildren(listView);
        listView.setSelectionAfterHeaderView();
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        teacherServerActivity.selectTab(0);
        listView.setOnItemClickListener(this);
    }

    public int getListViewHight() {
        return listView.getHeight();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), com.mytv365.view.activity.teacher.TeacherServerViewActivity.class);
        intent.putExtra("teacherView", teacherViews.get(position));
        startActivity(intent);
    }
}
