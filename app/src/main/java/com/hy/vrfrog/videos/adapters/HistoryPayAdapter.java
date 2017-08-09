package com.hy.vrfrog.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.responsebean.HistoryPayBean;
import com.hy.vrfrog.ui.XCRoundRectImageView;
import java.util.ArrayList;

/**
 * Created by qwe on 2017/8/7.
 */

public class HistoryPayAdapter extends RecyclerView.Adapter<HistoryPayAdapter.HistoryPayViewHolder>{
    private Context context;
    private ArrayList<HistoryPayBean>mList;


    public HistoryPayAdapter(Context context, ArrayList<HistoryPayBean> list) {
        this.context = context;
        this.mList = list;
    }


    @Override
    public HistoryPayAdapter.HistoryPayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_my_pay_item, parent, false);
        return new HistoryPayAdapter.HistoryPayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryPayAdapter.HistoryPayViewHolder holder, int position) {


        holder.mHistoryPayTitleTv.setText(mList.get(position).getTitle());
        holder.mHistoryPayObjectTv.setText(mList.get(position).getObject());
        holder.mHistoryPayTimeTv.setText(mList.get(position).getTime());


        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HistoryPayViewHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout relative;
        private final XCRoundRectImageView mIvImg;
        private final TextView mHistoryPayTitleTv;
        private final TextView mHistoryPayTimeTv;
        private final TextView mHistoryPayObjectTv;


        public HistoryPayViewHolder(View itemView) {
            super(itemView);
            relative = (RelativeLayout) itemView.findViewById(R.id.rl_my_pay_list);
            mIvImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_history_img);
            mHistoryPayTitleTv = (TextView) itemView.findViewById(R.id.tv_history_pay_title);
            mHistoryPayObjectTv = (TextView) itemView.findViewById(R.id.tv_history_pay_object);
            mHistoryPayTimeTv = (TextView) itemView.findViewById(R.id.tv_history_pay_time);


        }
    }
}
