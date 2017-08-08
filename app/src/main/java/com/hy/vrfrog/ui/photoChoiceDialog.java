package com.hy.vrfrog.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hy.vrfrog.R;

/**
 * Created by zhengshicheng on 10/23/15.
 */
public class photoChoiceDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView mAlbumTv;
    private TextView mCameraTv;
    private TextView mCanceltv;

    public photoChoiceDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public photoChoiceDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_photo,null);

        // 获取自定义Dialog布局中的控件

        mAlbumTv = (TextView) view.findViewById(R.id.tv_album);
        mCameraTv = (TextView)view.findViewById(R.id.tv_camera);
        mCanceltv = (TextView)view.findViewById(R.id.tv_photo_cancel);

        mCanceltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public photoChoiceDialog setAlbumListener(String text, final View.OnClickListener listener) {

        mAlbumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public photoChoiceDialog setCameraListener(String text, final View.OnClickListener listener) {

        mCameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public photoChoiceDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public photoChoiceDialog show() {
        dialog.show();
        return this ;
    }

    public photoChoiceDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }


}
