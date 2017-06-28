package com.jt.base.videos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jt.base.R;
import com.jt.base.application.BaseActivity;

import java.util.List;

/**
 * Created by Smith on 2017/6/28.
 */

public class GvDetialAdapter extends BaseAdapter {
    List<String> datas;
    Context context;

    public GvDetialAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.detail_gv_item, parent,
                false);

        return view;
    }
}
