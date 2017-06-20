package com.jt.base.videoDetails.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jt.base.HomeActivity;
import com.jt.base.R;
import com.jt.base.utils.UIUtils;

import java.util.List;

/**
 * Created by Smith on 2017/6/19.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    private Context context;
    List<String> mDatas;


    public RvAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_detail_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(mDatas.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               payDialog();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * 对话框
     */

    private void payDialog() {
        Builder mPayDialog = new AlertDialog.Builder(context,R.style.MyDialogStyle);
        final View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_pay_item, null);
        mPayDialog.setView(dialogView);
        mPayDialog.show();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.title);
        }
    }

}
