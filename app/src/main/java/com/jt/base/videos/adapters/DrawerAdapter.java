package com.jt.base.videos.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.http.responsebean.VideoTypeBean;
import com.jt.base.utils.UIUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Smith on 2017/7/3.
 */

public class DrawerAdapter extends BaseAdapter {
    List<VideoTypeBean.ResultBean> mDatas;
    Context context;
    private TextView mTvTitle;
    private View mLine;
    private LinearLayout mLlRootBg;
    private HashMap<Integer, Boolean> mItemPress;

    public DrawerAdapter(List<VideoTypeBean.ResultBean> mDatas, Context context, HashMap<Integer, Boolean> mItemPress) {
        this.mDatas = mDatas;
        this.context = context;
        this.mItemPress = mItemPress;
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
        mLlRootBg = (LinearLayout) view.findViewById(R.id.ll_drawer_bg);
        mLine = view.findViewById(R.id.drawer_item_line);
        mTvTitle.setText(mDatas.get(position).getName());

        if (mItemPress.get(position)){
            //非点击状态
            mTvTitle.setTextColor(Color.parseColor("#bdbdc4"));
            mLlRootBg.setBackgroundColor(Color.TRANSPARENT);
            mLine.setVisibility(View.VISIBLE);
        }else {//点击状态
            mTvTitle.setTextColor(Color.parseColor("#000000"));
            mLine.setVisibility(View.INVISIBLE);
            mLlRootBg.setBackground(UIUtils.getDrawable(R.drawable.drawer_item_bg));
        }

        return view;
    }
}
