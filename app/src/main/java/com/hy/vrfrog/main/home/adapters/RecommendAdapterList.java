package com.hy.vrfrog.main.home.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.RecommendBean;
import com.hy.vrfrog.main.home.activitys.VideoListActivity;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by qwe on 2017/8/8.
 */

public class RecommendAdapterList extends BaseAdapter {

    private Activity mContext;
    private List<RecommendBean.ResultBeanX> mList;
    private final int TYPE_STYLE_ONE = 0 ;
    private final int TYPE_STYLE_TWO = 1;
    private final int TYPE_STYLE_THREE = 2;
    public RecommendAdapterList(Activity context ,List<RecommendBean.ResultBeanX>list){
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        int type = getItemViewType(position);
        ViewHolder holder  = null;
        ViewHolderOne mOneHolder = null;
        ViewHolderTwo mTwoHolder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                mOneHolder = new ViewHolderOne();
                mTwoHolder = new ViewHolderTwo();
                switch (type){
                    case TYPE_STYLE_ONE:
                        convertView = View.inflate(mContext, R.layout.item_recommend_one, null);
                        holder.mTitle = (TextView) convertView.findViewById(R.id.tv_recommend_one_title);
                        holder.mMessage = (TextView)convertView.findViewById(R.id.tv_recommend_name);
                        holder.mType = (TextView)convertView.findViewById(R.id.tv_recommend_one_message);
                        holder.image = (XCRoundRectImageView)convertView.findViewById(R.id.xri_recommend_img);
                        convertView.setTag(holder);
                        break;
                    case TYPE_STYLE_TWO:
                        convertView = View.inflate(mContext, R.layout.item_recommend_scollview, null);
                        mTwoHolder.mTwoTitle= (TextView) convertView.findViewById(R.id.tv_recommend_two_title);
                        mTwoHolder.mScrollViewNum = (TextView)convertView.findViewById(R.id.tv_recommend_scrollview_num);
                        mTwoHolder.mRecyclerTwoTitle = (RecyclerView)convertView.findViewById(R.id.rv_recommend_two_video_list);
                        mTwoHolder.mType = (TextView)convertView.findViewById(R.id.tv_recommend_scrollview_message);
                        mTwoHolder.mTitleRl = (RelativeLayout) convertView.findViewById(R.id.rl_main_more);
                        convertView.setTag(mTwoHolder);

                        break;
                    case TYPE_STYLE_THREE:
                        convertView = View.inflate(mContext, R.layout.item_recommend_more, null);
                        mOneHolder.mOneTitle = (TextView) convertView.findViewById(R.id.tv_recommend_three_title);
                        mOneHolder.mRecommendMoreTv = (TextView)convertView.findViewById(R.id.tv_recommend_more_num);
                        mOneHolder.mRecyclerOneTitle = (RecyclerView)convertView.findViewById(R.id.rv_recommend_three_more_video_list);
                        mOneHolder.mOneType = (TextView)convertView.findViewById(R.id.tv_recommend_more_message);
                        mOneHolder.mOneTitleRl = (RelativeLayout)convertView.findViewById(R.id.rl_main_one_more);
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
                    if (mList != null && mList.size() != 0){

//                        holder.mTitle.setText((String)mList.get(position + 1 ).getResult().get(position).getTopicName());
//                        holder.mMessage.setText(mList.get(position + 1).getResult().get(position).getTopicName());
//                        holder.mType.setText(mList.get(position + 1).getResult().get(position).getTopicName());
//                        Glide.with(mContext).load(mList.get(position + 1).getResult().get(position).getImg()).into(holder.image);
//                        holder.mMessage.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent = new Intent(mContext, VideoListActivity.class);
//                                intent.putExtra(VedioContants.TopicId,mList.get(position ).getCode());
//                                intent.putExtra(VedioContants.TopicTitle,mList.get(position ).getMsg());
//                                mContext.startActivity( intent);
//                            }
//                        });
                    }
//                    if (mList != null){
//                        holder.mNum.setText("共" + mList.get(position).getPage().getTotal()+ "个视频");
//                    }


                    break;
                case TYPE_STYLE_TWO:
                    if (mList != null && mList.size() != 0){
                        mTwoHolder.mTwoTitle.setText(mList.get(position ).getMsg());
                        mTwoHolder.mScrollViewNum.setText("共" + mList.get(position).getPage().getTotal()+ "个视频");
                        mTwoHolder.mType.setText((String)mList.get(position).getResult().get(0).getTypeName());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                        mTwoHolder.mRecyclerTwoTitle.setNestedScrollingEnabled(false);
                        mTwoHolder.mRecyclerTwoTitle.setLayoutManager(linearLayoutManager);
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        RecommendOneAdapter adapter = new RecommendOneAdapter(mContext,mList.get(position).getResult(),mList.get(position ).getCode());
                        mTwoHolder.mRecyclerTwoTitle.setAdapter(adapter);
                        mTwoHolder.mTitleRl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LogUtil.i("one position = " + position);
                                Intent intent = new Intent(mContext, VideoListActivity.class);
                                intent.putExtra(VedioContants.TopicId,mList.get(position ).getCode());
                                intent.putExtra(VedioContants.TopicTitle,mList.get(position ).getMsg());
                                mContext.startActivity( intent);
                            }
                        });
                    }

                    break;
                case TYPE_STYLE_THREE:
                    if (mList  != null && mList.size() != 0 ){
                        mOneHolder.mOneTitle.setText(mList.get(position).getMsg());
                        mOneHolder.mRecommendMoreTv.setText("共" + mList.get(position).getPage().getTotal()+ "个视频");
                        mOneHolder.mOneType.setText((String)mList.get(position).getResult().get(0).getTypeName());

                        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
                        mOneHolder.mRecyclerOneTitle.setNestedScrollingEnabled(false);
                        mOneHolder.mRecyclerOneTitle.setLayoutManager(mLinearLayoutManager);
                        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                        RecommendTwoAdapter mAdapter = new RecommendTwoAdapter(mContext,mList.get(position).getResult(),mList.get(position).getCode());
//                        mOneHolder.mRecyclerOneTitle.setAdapter(mAdapter);
                        mOneHolder.mOneTitleRl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LogUtil.i("two position = " + position);
                                Intent intent = new Intent(mContext, VideoListActivity.class);
                                intent.putExtra(VedioContants.TopicId,mList.get(position ).getCode());
                                intent.putExtra(VedioContants.TopicTitle,mList.get(position ).getMsg());
                                mContext.startActivity( intent);
                            }
                        });
                    }

                    break;
            }

        return convertView;
    }

    class ViewHolder{

        private TextView mTitle;
        private TextView mMessage;
        private TextView mNum;
        private TextView mType;
        private ImageView mBackgroundImg;
        private XCRoundRectImageView image;

    }

    class ViewHolderOne{

        private TextView mOneTitle;
        private RecyclerView mRecyclerOneTitle;
        private TextView mRecommendMoreTv;
        private TextView mOneType;
        private RelativeLayout mOneTitleRl;


    }

    class ViewHolderTwo{

        private TextView mTwoTitle;
        private RecyclerView mRecyclerTwoTitle;
        private TextView mScrollViewNum;
        private TextView mType;
        private RelativeLayout mTitleRl;

    }

}
