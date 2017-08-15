package com.mytv365.view.fragment.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.tools.Tool;
import com.mytv365.R;
import com.mytv365.adapter.listview.StockAdapter;
import com.mytv365.entity.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票池
 * Created by zhangguohao on 16/8/22.
 */
public class StockFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private BaseMAdapter<Stock> adapter;
    private View titleView;
    private List<Stock> stocks;
    private com.mytv365.view.activity.teacher.TeacherServerActivity teacherServerActivity;

    @Override
    public int bindLayout() {
        return R.layout.list_view;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        stocks = new ArrayList<>();
        teacherServerActivity = (com.mytv365.view.activity.teacher.TeacherServerActivity) getActivity();
        titleView = LayoutInflater.from(mContext).inflate(R.layout.item_stock_title, null);
        listView = (ListView) findViewById(R.id.listview);
        adapter = new BasicDataAdapter<Stock>(new StockAdapter(mContext));
        listView.addHeaderView(titleView);
    }

    @Override
    public void doBusiness(Context mContext) {
        adapter.addItem(stocks);
//        listView.setAdapter(adapter);
//        Tool.setListViewHeightBasedOnChildren(listView);
//        listView.setSelectionAfterHeaderView();
//        adapter.notifyDataSetChanged();
//        listView.setSelection(0);
//        teacherServerActivity.selectTab(0);
//        listView.setOnItemClickListener(this);
    }

    public void setData(List<Stock> stocks) {
        adapter.clear();
        this.stocks = stocks;
        adapter.addItem(stocks);
        listView.setAdapter(adapter);
        Tool.setListViewHeightBasedOnChildren(listView);
        listView.setSelectionAfterHeaderView();
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        teacherServerActivity.selectTab(0);
        listView.setOnItemClickListener(this);
    }

    public int getListViewHeight() {
        return listView.getHeight();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            Intent intent = new Intent(getActivity(), com.mytv365.view.activity.teacher.TeacherServerStockActivity.class);
            intent.putExtra("stock", stocks.get(position - 1));
            startActivity(intent);
        }
    }
}
