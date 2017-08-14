package com.hy.vrfrog.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hy.vrfrog.main.home.fragments.PersonalLiveHomeFragment;

import org.xutils.common.util.LogUtil;

/**
 * Created by qwe on 2017/8/10.
 */

public class ItemDivider extends RecyclerView.ItemDecoration {
    private int space;


    public ItemDivider(int space) {
        this.space = space;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        LogUtil.i( "frist =" + parent.getChildPosition(view));

        if ( parent.getAdapter().getItemCount() - 1 == parent.getChildPosition(view)){
            LogUtil.i(  "last = " + "enter");
            outRect.top = 0;
            if (parent.getChildLayoutPosition(view) %2 != 0){
                outRect.top = space;
            }
        }else {
            outRect.top = space ;
        }

        //由于每行都只有2个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) %2 == 0) {
            outRect.right = space/2;
            outRect.left = space;
        }else {
            outRect.left = space/2;
            outRect.right = space;
        }

    }


}
