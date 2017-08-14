package com.hy.vrfrog.main.home.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.RecommendBean;
import com.hy.vrfrog.http.responsebean.TopicBean;
import com.hy.vrfrog.main.home.activitys.VideoDetialActivity;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by Smith on 2017/6/29.
 */

public class RecommendOneAdapter extends RecyclerView.Adapter<RecommendOneAdapter.ListHolder> {



    //获取从Activity中传递过来每个item的数据集合
    private Activity context;
    private int position;
    private List<RecommendBean.ResultBeanX.ResultBean> resultBean;
    private int TopicId;

    //构造函数

    public RecommendOneAdapter(Activity context, List<RecommendBean.ResultBeanX.ResultBean> resultBean, int TopicId) {
        this.context = context;
        this.resultBean = resultBean;
        this.TopicId = TopicId;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public  RecommendOneAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_one, parent, false);
        return new  RecommendOneAdapter.ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecommendOneAdapter.ListHolder holder, final int position) {


        if ( resultBean.size() != 0 ){
            LogUtil.i( "tag is  = " +  resultBean.get(position).getImg1() );
            Glide.with(context)
                    .load(HttpURL.IV_HOST + resultBean.get(position).getImg1())
                    .asBitmap()
                    .error(R.mipmap.camera_off)
                    .into(holder.image);
            //设置视频的描述信息
            holder.mMessage.setText((String)resultBean.get(position).getIntroduce());

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoDetialActivity.class);
                    intent.putExtra(VedioContants.Position, position);//首次显示在哪一个封面
                    intent.putExtra(VedioContants.TopicId, TopicId);//哪个话题
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
                }
            });
        }else {
            LogUtil.i( "tag is  = " +  "enter" );
            holder.image.setImageResource(R.mipmap.img1);
        }

    }


    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder {

        private TextView mMessage;
        private XCRoundRectImageView image;

        public ListHolder(View itemView) {
            super(itemView);

            mMessage = (TextView)itemView.findViewById(R.id.tv_main_name);
            image = (XCRoundRectImageView)itemView.findViewById(R.id.xri_main_img);
        }
    }


    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (resultBean.size() > 1){
            return 1;
        }else {
            return resultBean .size();
        }
    }

}
