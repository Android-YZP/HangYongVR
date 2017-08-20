package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.http.responsebean.RechargeBean;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.ui.DemandPayDialog;
import com.hy.vrfrog.ui.LoadingDataUtil;
import com.hy.vrfrog.ui.PaySuccessDialog;
import com.hy.vrfrog.ui.RechargeDialog;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.ToolToast;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by qwe on 2017/8/3.
 */

public class PersonalLiveHomeAdapter extends RecyclerView.Adapter<PersonalLiveHomeAdapter.LiveHomeAdapterHolder> {

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
    private IPersonalLiveAdapter mCallback;
    private Runnable toDo;
    private int mPosition;


    public void setListener(IPersonalLiveAdapter listener) {

        this.mCallback = listener;

    }

    public PersonalLiveHomeAdapter(Context context, List<GetLiveHomeBean.ResultBean> resultBean) {
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
    public PersonalLiveHomeAdapter.LiveHomeAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new PersonalLiveHomeAdapter.LiveHomeAdapterHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new PersonalLiveHomeAdapter.LiveHomeAdapterHolder(mFooterView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_home, parent, false);
        return new PersonalLiveHomeAdapter.LiveHomeAdapterHolder(layout);
    }

    @Override
    public void onBindViewHolder(PersonalLiveHomeAdapter.LiveHomeAdapterHolder holder, final int position) {

        mPosition = position;

        if (resultBean.get(position).getPrice() != 0) {
            holder.mPayTv.setVisibility(View.VISIBLE);
        } else {
            holder.mPayTv.setVisibility(View.GONE);
        }
        if (getItemViewType(position) == TYPE_NORMAL) {
            holder.mLiveHomeTitleTv.setText(resultBean.get(position).getChannelName());
            holder.mLiveHomeHeadNameTv.setText(String.valueOf(resultBean.get(position).getUsername()));
            Glide.with(context).load(HttpURL.IV_PERSON_HOST + resultBean.get(position).getImg()).asBitmap().into(holder.mXcImg);

            holder.mXcImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (resultBean.get(position).getPrice() != 0) {
                        mCallback.onPayMoney(position);
                    } else {
                        Intent intent = new Intent(context, LivingPlayActivity.class);
                        intent.putExtra(VedioContants.LivingPlayUrl, resultBean.get(position).getRtmpDownstreamAddress());
                        intent.putExtra(VedioContants.ChannelId, resultBean.get(position).getChannelId());
                        intent.putExtra(VedioContants.GroupID, resultBean.get(position).getAlipay() + "");
                        intent.putExtra(VedioContants.HeadFace, HttpURL.IV_USER_HOST + resultBean.get(position).getHead() + "");
                        intent.putExtra(VedioContants.ChannelName, resultBean.get(position).getChannelName());
                        intent.putExtra(VedioContants.RoomImg, HttpURL.IV_PERSON_HOST + resultBean.get(position).getImg());
                        intent.putExtra(VedioContants.GiftGroup, resultBean.get(position).getGiftGroup());
                        intent.putExtra(VedioContants.Vid, resultBean.get(position).getId());
                        intent.putExtra(VedioContants.Yid, resultBean.get(position).getUid());
                        LogUtil.e(resultBean.get(position).getId() + "<" + VedioContants.Yid + resultBean.get(position).getUid());
                        LogUtil.e(HttpURL.IV_PERSON_HOST + resultBean.get(position).getImg() + "________");
                        context.startActivity(intent);
                    }

                }
            });
        }


    }


    public class LiveHomeAdapterHolder extends RecyclerView.ViewHolder {

        private TextView mLiveHomeTitleTv;
        private TextView mLiveHomePeopleNumberTv;
        private TextView mLiveHomePlayStateTv;
        private TextView mLiveHomeHeadNameTv;
        private ImageView mLiveHomePlayStateImg;
        private ImageView mLiveHomeHeadImg;
        private ImageView mXcImg;
        private TextView mPayTv;


        public LiveHomeAdapterHolder(View itemView) {
            super(itemView);

            mLiveHomeTitleTv = (TextView) itemView.findViewById(R.id.tv_live_home_title);
            mLiveHomePeopleNumberTv = (TextView) itemView.findViewById(R.id.tv_live_home_people_number);
            mLiveHomePlayStateTv = (TextView) itemView.findViewById(R.id.tv_live_home_live_state);
            mLiveHomeHeadNameTv = (TextView) itemView.findViewById(R.id.tv_live_home_name);

            mLiveHomePlayStateImg = (ImageView) itemView.findViewById(R.id.img_live_home_play_state);
            mLiveHomeHeadImg = (ImageView) itemView.findViewById(R.id.img_live_home_head);

            mXcImg = (ImageView) itemView.findViewById(R.id.img_xc_personal);
            mPayTv = (TextView) itemView.findViewById(R.id.tv_personal_live_pay);

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

    public interface IPersonalLiveAdapter {

        void onPayMoney(int position);

    }

}
