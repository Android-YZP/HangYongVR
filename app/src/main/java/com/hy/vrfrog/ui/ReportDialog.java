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
 * Created by qwe on 2017/8/3.
 */

public class ReportDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView mReportRumorTv;
    private TextView mReportLewdTv;
    private TextView mReportMinorTv;
    private TextView mReportSmokeTv;
    private TextView mReportAdvertisementTv;
    private TextView mReportOtherTv;
    private TextView mReportCancel;


    public ReportDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ReportDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_report,null);

        // 获取自定义Dialog布局中的控件

        mReportRumorTv = (TextView) view.findViewById(R.id.tv_report_rumor);
        mReportLewdTv = (TextView) view.findViewById(R.id.tv_report_lewd);
        mReportMinorTv = (TextView) view.findViewById(R.id.tv_report_minor);
        mReportSmokeTv = (TextView) view.findViewById(R.id.tv_report_smoke);
        mReportAdvertisementTv = (TextView) view.findViewById(R.id.tv_report_advertisement);
        mReportOtherTv = (TextView) view.findViewById(R.id.tv_report_other);
        mReportRumorTv = (TextView) view.findViewById(R.id.tv_report_rumor);
        mReportCancel = (TextView)view.findViewById(R.id.tv_report_cancel);

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

    public ReportDialog setRumorListener(final View.OnClickListener listener) {

        mReportRumorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ReportDialog setLewdListener(final View.OnClickListener listener) {

        mReportLewdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ReportDialog setMinorListener(final View.OnClickListener listener) {

        mReportMinorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }


    public ReportDialog setSmokeListener(final View.OnClickListener listener) {

        mReportSmokeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ReportDialog setAdvertisementListener(final View.OnClickListener listener) {

        mReportAdvertisementTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ReportDialog setOtherListener(final View.OnClickListener listener) {

        mReportOtherTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ReportDialog setCancelListener(final View.OnClickListener listener) {

        mReportCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public ReportDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ReportDialog show() {
        dialog.show();
        return this ;
    }

    public ReportDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }
}
