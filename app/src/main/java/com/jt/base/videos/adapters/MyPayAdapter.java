package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jt.base.R;
import com.jt.base.http.responsebean.GetHistoryBean;
import com.jt.base.personal.HistoryActivity;
import com.jt.base.personal.HistoryPayActivity;
import com.jt.base.ui.XCRoundRectImageView;

import java.util.List;


/**
 * Created by wzq930102 on 2017/7/31.
 */

public class MyPayAdapter extends RecyclerView.Adapter<MyPayAdapter.MyPayViewHolder>{
    private Context context;
    private List<String> list;


    public MyPayAdapter(HistoryActivity context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyPayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_my_pay_item, parent, false);
        return new MyPayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPayViewHolder holder, int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyPayViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layout;
        private final XCRoundRectImageView mIvImg;
        private final TextView mTvtitle;
        private final TextView mTvpersent;
        private final TextView mTvshijian;

        public MyPayViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_my_pay_list);
            mIvImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_my_pay_img);
            mTvtitle = (TextView) itemView.findViewById(R.id.tv_my_pay_title);
            mTvpersent = (TextView) itemView.findViewById(R.id.tv_my_pay_persent);
            mTvshijian = (TextView) itemView.findViewById(R.id.tv_my_pay_shijian);
        }
    }
}
