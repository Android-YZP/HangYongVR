package com.hy.vrfrog.main.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GetLiveHomeBean;
import com.hy.vrfrog.http.responsebean.PayStatus;
import com.hy.vrfrog.main.home.activitys.VedioDeatilsActivity;
import com.hy.vrfrog.main.home.fragments.EnterpriseLiveHomeFragment;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.vrplayer.Definition;
import com.hy.vrfrog.vrplayer.PlayActivity;
import com.hy.vrfrog.vrplayer.VideoPlayActivity;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.snail.media.player.SnailWhiteList.mList;

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
    private IEnterpriseLiveAdapter mCallback;

    public void setEnterpriseLiveListener(IEnterpriseLiveAdapter listener){
        this.mCallback = listener;
    }

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
    public void onBindViewHolder(Enterprise1LiveHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {


            if (resultBean.get(position).getLvbStatus() == 1){
                holder.mEnterZhiBoTv.setText("直播中");
                holder.mRnterprise1LiveImg.setImageResource(R.drawable.live_icon_play);
            }else if (resultBean.get(position).getLvbChannelRecords().size() != 0){
//            holder.mLiveHomePlayStateTv.setText("回放");
//            holder.mLiveHomePlayStateImg.setImageResource(R.drawable.live_icon_look_circle);
                holder.mEnterZhiBoTv.setVisibility(View.GONE);
                holder.mRnterprise1LiveImg.setVisibility(View.GONE);
            }else {
                holder.mEnterZhiBoTv.setVisibility(View.GONE);
                holder.mRnterprise1LiveImg.setVisibility(View.GONE);
            }

            holder.mEnterTitleTv.setText(resultBean.get(position).getChannelName());
            holder.mRnterpriseLiveTvName.setText(String.valueOf(resultBean.get(position).getUsername()));
            Glide.with(context).load(HttpURL.IV_HOST + resultBean.get(position).getImg1()).asBitmap().into(holder.mIvImg);
            holder.mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (resultBean.get(position).getLvbStatus() == 1){
                        if(resultBean.get(position).getPrice() != 0){
                            if (SPUtil.getUser() == null) {
                                UIUtils.showTip("请登录后付费体验");
                            } else {
                                //是否还要付费接口
                                HttpPayStatus(resultBean.get(position).getId() + "", SPUtil.getUser().getResult().getUser().getUid() + "",position);
                            }
                        }else {
                            Intent i = new Intent(context, PlayActivity.class);
                            int isall = resultBean.get(position).getIsall();
                            if (isall == VedioContants.TWO_D_VEDIO) {
                                i.putExtra(Definition.PLEAR_MODE, VedioContants.TWO_D_VEDIO);
                            } else if (isall == VedioContants.ALL_VIEW_VEDIO) {
                                i.putExtra(Definition.PLEAR_MODE, VedioContants.ALL_VIEW_VEDIO);
                            }
                            LogUtil.i(resultBean.get(position).getRtmpDownstreamAddress() + "");
                            i.putExtra(VedioContants.PlayUrl, resultBean.get(position).getRtmpDownstreamAddress() + "");
                            i.putExtra(VedioContants.KEY_PLAY_HEAD, HttpURL.IV_USER_HOST + resultBean.get(position).getHead() + "");
                            i.putExtra(VedioContants.KEY_PLAY_USERNAME, resultBean.get(position).getUsername() + "");
                            i.putExtra(VedioContants.KEY_PLAY_ID, resultBean.get(position).getId() + "");
                            if (NetUtil.isOpenNetwork()) {
                                context.startActivity(i);
                            } else {
                                UIUtils.showTip("请连接网络");
                            }
                        }

                    }else {
                        UIUtils.showTip("不能开播");
                    }

                }
            });
        }
    }


    /**
     * 是否还要付费
     */
    private void HttpPayStatus(String vid, String uid, final int position) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.payStatus);
        requestParams.addHeader("token", SPUtil.getUser().getResult().getToken());
        //包装请求参数
        requestParams.addBodyParameter("vid", vid);//用户名
        requestParams.addBodyParameter("uid", uid);//用户名
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i("是否还要付费=====================" + result);
                PayStatus payStatus = new Gson().fromJson(result, PayStatus.class);
                if (payStatus.getCode() == 0) {
                    if (payStatus.isResult()){//钱付过了
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

                    }else {
                        mCallback.onPayMoney(position);
                    }

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {

            }

        });

    }


    public class Enterprise1LiveHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linear;
        private final XCRoundRectImageView mIvImg;
        private final TextView mEnterTitleTv;
        private final TextView mEnterTvNum;
        private final TextView mEnterZhiBoTv;
        private final TextView mRnterpriseLiveTvName;
        private final ImageView mRnterprise1LiveImg;


        public Enterprise1LiveHolder(View itemView) {
            super(itemView);
            linear = (LinearLayout) itemView.findViewById(R.id.ll_enter_title);
            mIvImg = (XCRoundRectImageView) itemView.findViewById(R.id.xri_enter_img);
            mEnterTitleTv = (TextView) itemView.findViewById(R.id.tv_enter_home_title);
            mEnterTvNum = (TextView) itemView.findViewById(R.id.tv_enter_home_people_number);
            mEnterZhiBoTv = (TextView) itemView.findViewById(R.id.tv_enter_home_live_state);
            mRnterpriseLiveTvName = (TextView) itemView.findViewById(R.id.tv_enter_home_name);
            mRnterprise1LiveImg = (ImageView) itemView.findViewById(R.id.img_enter_home_play_state);

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

    public interface IEnterpriseLiveAdapter{


        void onPayMoney(int position);

        void onReViewPLay(int position);

    }

}