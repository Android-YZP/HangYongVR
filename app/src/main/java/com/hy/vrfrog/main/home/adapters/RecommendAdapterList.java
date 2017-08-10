package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.main.home.bean.RecommendBean;

import java.util.ArrayList;

/**
 * Created by qwe on 2017/8/8.
 */

public class RecommendAdapterList extends BaseAdapter {

    private Context mContext;
    private ArrayList<RecommendBean>mList;
    private final int TYPE_STYLE_ONE = 0 ;
    private final int TYPE_STYLE_TWO = 1;
    private final int TYPE_STYLE_THREE = 2;
    public RecommendAdapterList(Context context ,ArrayList<RecommendBean>list){
        this.mList = list;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        // 按位置分配布局
        if (position == 0) {
            return TYPE_STYLE_ONE;
        } else if (position % 2 == 0){
            return TYPE_STYLE_TWO;
        }else {
            return TYPE_STYLE_THREE;
        }

    }

    // 布局类型数量
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder  = null;
        ViewHolderOne mOneHolder = null;
        ViewHolderTwo mTwoHolder = null;

        int type = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            mOneHolder = new ViewHolderOne();
            mTwoHolder = new ViewHolderTwo();
            switch (type){
                case TYPE_STYLE_ONE:
                    convertView = View.inflate(mContext, R.layout.item_recommend_one, null);
                    holder.mTitle = (TextView) convertView.findViewById(R.id.tv_recommend_one_title);
                    holder.mMessage = (TextView)convertView.findViewById(R.id.tv_recommend_one_message);
//                    holder.mNum = (TextView)convertView.findViewById(R.id.tv_recommend_one_num);
//                    holder.mBackgroundImg = (ImageView)convertView.findViewById(R.id.img_recommend_one_background);
                    convertView.setTag(holder);
                    break;
                case TYPE_STYLE_TWO:
                    convertView = View.inflate(mContext, R.layout.item_recommend_scollview, null);
                    mTwoHolder.mTwoTitle= (TextView) convertView.findViewById(R.id.tv_recommend_two_title);
                    mTwoHolder.mScrollViewNum = (TextView)convertView.findViewById(R.id.tv_recommend_scrollview_num);
                    mTwoHolder.mRecyclerTwoTitle = (RecyclerView)convertView.findViewById(R.id.rv_recommend_two_video_list);
                    convertView.setTag(mTwoHolder);

                    break;
                case TYPE_STYLE_THREE:
                    convertView = View.inflate(mContext, R.layout.item_recommend_more, null);
                    mOneHolder.mOneTitle = (TextView) convertView.findViewById(R.id.tv_recommend_three_title);
                    mOneHolder.mRecommendMoreTv = (TextView)convertView.findViewById(R.id.tv_recommend_more_num);
                    mOneHolder.mRecyclerOneTitle = (RecyclerView)convertView.findViewById(R.id.rv_recommend_three_more_video_list);
                    convertView.setTag(mOneHolder);
                    break;
            }

        } else {
            switch (type){
                case TYPE_STYLE_ONE:
                    holder = (ViewHolder) convertView.getTag();
                    break;
                case TYPE_STYLE_TWO:
                    mTwoHolder = (ViewHolderTwo) convertView.getTag();
                    break;
                case TYPE_STYLE_THREE:
                    mOneHolder = (ViewHolderOne) convertView.getTag();
                    break;

            }

        }

        switch (type){
            case TYPE_STYLE_ONE:
                holder.mTitle.setText(mList.get(position).getTitle());
                break;
            case TYPE_STYLE_TWO:
                mTwoHolder.mTwoTitle.setText(mList.get(position).getTitle());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                mTwoHolder.mRecyclerTwoTitle.setNestedScrollingEnabled(false);
                mTwoHolder.mRecyclerTwoTitle.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                RecommendOneAdapter adapter = new RecommendOneAdapter(mContext,mList);
                mTwoHolder.mRecyclerTwoTitle.setAdapter(adapter);
                break;
            case TYPE_STYLE_THREE:
                mOneHolder.mOneTitle.setText(mList.get(position).getTitle());
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
                mOneHolder.mRecyclerOneTitle.setNestedScrollingEnabled(false);
                mOneHolder.mRecyclerOneTitle.setLayoutManager(mLinearLayoutManager);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                RecommendTwoAdapter mAdapter = new RecommendTwoAdapter(mContext,mList);
                mOneHolder.mRecyclerOneTitle.setAdapter(mAdapter);
                break;
        }

        return convertView;
    }

    class ViewHolder{

        private TextView mTitle;
        private TextView mMessage;
        private TextView mNum;
        private ImageView mBackgroundImg;

    }

    class ViewHolderOne{

        private TextView mOneTitle;
        private RecyclerView mRecyclerOneTitle;
        private TextView mRecommendMoreTv;

    }

    class ViewHolderTwo{

        private TextView mTwoTitle;
        private RecyclerView mRecyclerTwoTitle;
        private TextView mTwoNum;
        private TextView mScrollViewNum;

    }
}
