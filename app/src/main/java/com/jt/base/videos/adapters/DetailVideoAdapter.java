package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jt.base.R;

import java.util.List;

/**
 * Created by wzq930102 on 2017/7/13.
 */

public class DetailVideoAdapter extends RecyclerView.Adapter<DetailVideoAdapter.MyVideoHolder>{
    private Context context;
    private List<String> list;

    public DetailVideoAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vedio_list_activity_item, parent, false);
        return new MyVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVideoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyVideoHolder extends RecyclerView.ViewHolder{
        private final LinearLayout linear;

        public MyVideoHolder(View itemView){
            super(itemView);
            linear = ((LinearLayout) itemView.findViewById(R.id.ll_deatils_more));
        }
    }
}
