package com.jt.base.videos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jt.base.R;

import java.util.List;

import static android.media.CamcorderProfile.get;

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
        // 给ViewHolder设置布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.vedio_list_activity_item, parent, false);
        return new MyVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVideoHolder holder, int position) {
        // 给ViewHolder设置元素
//        holder..(list.get(position));
        holder.text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return list.size();
    }
    // 重写的自定义ViewHolder
    public static class MyVideoHolder extends RecyclerView.ViewHolder{
//        private final ImageView  ;
        private final TextView text;

        public MyVideoHolder(View itemView){
            super(itemView);
//            = (ImageView) itemView.findViewById(R.id.img_video_item);
            text = (TextView) itemView.findViewById(R.id.tv_video_item);
        }
    }
}
