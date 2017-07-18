package com.jt.base.videos.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jt.base.R;
import com.jt.base.utils.UIUtils;

/**
 * Created by wzq930102 on 2017/7/17.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryReViewHolder> {
    public Activity context;

    public HistoryListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public HistoryReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_history_item, parent, false);
        return new HistoryReViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryReViewHolder holder, final int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showTip(position + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HistoryReViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layout;

        public HistoryReViewHolder(View itemView) {
            super(itemView);
            layout = ((RelativeLayout) itemView.findViewById(R.id.rl_history_list));
        }
    }
}
