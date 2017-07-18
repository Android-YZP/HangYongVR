package com.jt.base.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jt.base.R;


/**
 * dialog工具类
 */

public class DialogUtils {

    private Context context;
    private BaseDialog baseDialog;

    private WindowManager.LayoutParams layoutParams;

    public DialogUtils(Context context){
        this.context = context;
        baseDialog = new BaseDialog(context, R.style.NoTitleDialog);
        layoutParams = baseDialog.getDialogLayoutParams();
    }

    public DialogUtils(Context context,int mode){
        this.context = context;
        if(mode == 0){
            baseDialog = new BaseDialog(context, R.style.NoTitleDialog);
        }else if(mode == 1){
            baseDialog = new BaseDialog(context, R.style.NoTitleTransparentDialog);
        }
        layoutParams = baseDialog.getDialogLayoutParams();
    }

    public DialogUtils setContentView(View view){
        baseDialog.setContentView(view);
        layoutParams = baseDialog.getDialogLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        baseDialog.getDialogWindow().setAttributes(layoutParams);
        return this;
    }

    public DialogUtils setContentViewSize(double width, double height){
        if(width != 0){
            layoutParams.width = (int) width;
        }
        if(height != 0){
            layoutParams.height = (int) height;
        }
        baseDialog.getDialogWindow().setAttributes(layoutParams);
        return this;
    }

    public DialogUtils setXY(int x, int y){
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.x = x;
        layoutParams.y = y;
        baseDialog.getDialogWindow().setAttributes(layoutParams);
        return this;
    }

    public DialogUtils setGravity(int gravity){
        baseDialog.setGravity(gravity);
        return this;
    }

    public void show(){
        baseDialog.show();
    }

    public BaseDialog getBaseDialog(){
        return baseDialog;
    }
}
