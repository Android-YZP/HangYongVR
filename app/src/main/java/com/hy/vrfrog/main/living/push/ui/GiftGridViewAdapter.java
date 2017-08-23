package com.hy.vrfrog.main.living.push.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.GiftBean;
import com.hy.vrfrog.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * author：Administrator on 2016/12/26 15:03
 * description:文件说明
 * version:版本
 */
///定影GridView的Adapter
public class GiftGridViewAdapter extends BaseAdapter {
    private int page;
    private int count;
    private ArrayList<Gift> gifts;
    private List<GiftBean.ResultBean> giftArrayList;
    private Context context;
    private HashMap<Integer, Boolean> mGiftChecked;


    public void setGifts(List<GiftBean.ResultBean> gifts) {
        this.giftArrayList = gifts;
        notifyDataSetChanged();
    }

    public GiftGridViewAdapter(Context context, int page, int count, HashMap<Integer, Boolean> mGiftChecked) {
        this.page = page;
        this.count = count;
        this.context = context;
        this.mGiftChecked = mGiftChecked;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public GiftBean.ResultBean getItem(int position) {
        return giftArrayList.get(page * count + position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final GiftBean.ResultBean resultBean = giftArrayList.get(page * count + position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gift2, null);

            viewHolder.grid_fragment_home_item_img =
                    (ImageView) convertView.findViewById(R.id.grid_fragment_home_item_img);
            viewHolder.grid_fragment_home_item_txt =
                    (TextView) convertView.findViewById(R.id.grid_fragment_home_item_txt);
            viewHolder.llgiftView =
                    (LinearLayout) convertView.findViewById(R.id.ll_gift_root);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Glide.with(context).load(HttpURL.IV_GIFT_HOST + resultBean.getGif()).asBitmap().into(viewHolder.grid_fragment_home_item_img);
        viewHolder.grid_fragment_home_item_txt.setText(resultBean.getName());
        if (mGiftChecked.get(position)){
            viewHolder.llgiftView.setBackground(UIUtils.getDrawable(R.drawable.gift_checked));
        }else {
            viewHolder.llgiftView.setBackground(UIUtils.getDrawable(R.drawable.bg_giftlayout));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onGridViewClickListener != null) {
                    onGridViewClickListener.click(resultBean, position);
                }


                for (int i = 0; i <giftArrayList.size() ; i++) {
                    mGiftChecked.put(i,false);
                }
                mGiftChecked.put(position,true);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public ImageView grid_fragment_home_item_img;
        public TextView grid_fragment_home_item_txt;
        public LinearLayout llgiftView;
    }

    public OnGridViewClickListener onGridViewClickListener;

    public void setOnGridViewClickListener(OnGridViewClickListener onGridViewClickListener) {
        this.onGridViewClickListener = onGridViewClickListener;
    }

    public interface OnGridViewClickListener {
        void click(GiftBean.ResultBean gift, int position);
    }
}
