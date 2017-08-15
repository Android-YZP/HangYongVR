package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import java.util.List;

/**
 * Created by wzq930102 on 2017/8/9.
 */
public class EnterpriseOnLiveAdapter extends RecyclerView.Adapter<EnterpriseOnLiveAdapter.Enterprise1LiveHolder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private static final String ACTION = "com.jt.base.SENDBROADCAST";
    //获取从Activity中传递过来每个item的数据集合
    private List<String> mDatas;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;
    private Context context;
    private List<GetLiveHomeBean.ResultBean> resultBean;

    public EnterpriseOnLiveAdapter(Context context, List<GetLiveHomeBean.ResultBean> resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }


    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }


    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0 && mHeaderView != null) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1 && mFooterView != null) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public EnterpriseOnLiveAdapter.Enterprise1LiveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new EnterpriseOnLiveAdapter.Enterprise1LiveHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new EnterpriseOnLiveAdapter.Enterprise1LiveHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_enterprise, parent, false);
        return new EnterpriseOnLiveAdapter.Enterprise1LiveHolder(layout);
    }

    @Override
    public void onBindViewHolder(Enterprise1LiveHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL){
            holder.mEnterTitleTv.setText(resultBean.get(position).getChannelName());
            holder.mRnterpriseLiveTvName.setText(String.valueOf(resultBean.get(position).getUsername()));
//            Glide.with(context).load(HttpURL.IV_HOST+resultBean.get(position).getImg()).asBitmap().into(holder.mIvImg);
            holder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    public class Enterprise1LiveHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linear;
        private final XCRoundRectImageView mIvImg;
        private final TextView mEnterTitleTv;
        private final TextView mEnterTvNum;
        private final TextView mEnterZhiBoTv;
        private final TextView mRnterpriseLiveTvName;
        private final ImageView mRnterprise1LiveIv;


        public Enterprise1LiveHolder(View itemView) {
            super(itemView);
            linear = (LinearLayout) itemView.findViewById(R.id.ll_enter_title);
            mIvImg = (XCRoundRectImageView) itemView.findViewById(R.id.xri_enter_img);
            mEnterTitleTv = (TextView) itemView.findViewById(R.id.tv_enter_home_title);
            mEnterTvNum = (TextView) itemView.findViewById(R.id.tv_enter_home_people_number);
            mEnterZhiBoTv = (TextView) itemView.findViewById(R.id.tv_enter_home_live_state);
            mRnterpriseLiveTvName = (TextView) itemView.findViewById(R.id.tv_enter_home_name);
            mRnterprise1LiveIv = (ImageView) itemView.findViewById(R.id.img_enter_home_play_state);

        }
    }


    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return resultBean.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return resultBean.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return resultBean.size() + 1;
        } else {

            return resultBean.size() + 2;
        }
    }

}