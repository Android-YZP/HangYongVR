package com.jt.base.videos.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.http.responsebean.VideoTypeBean;

import java.util.List;

/**
 * Created by Smith on 2017/7/3.
 */

public class DrawerAdapter extends BaseAdapter {
    List<VideoTypeBean.ResultBean> mDatas;
    Context context;
    private TextView mTvTitle;

    public DrawerAdapter(List<VideoTypeBean.ResultBean> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.item_drawer, null);
        mTvTitle = (TextView) view.findViewById(R.id.tv_drawer_item_title);
        mTvTitle.setText(mDatas.get(position).getName());
        return view;
    }
}
