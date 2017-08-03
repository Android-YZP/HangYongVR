package com.hy.vrfrog.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hy.vrfrog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengshicheng on 10/23/15.
 */
public class ActionSheetDialog {
    private Context context;
    private Dialog dialog;
    private TextView text_title;
    private RelativeLayout cancle ;
    private TextView text_confirm;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    private Display display;
    private RelativeLayout relativeLayout ;
    private  OnConfirmClickListener mCallback;
    private List<Integer> mUnclickAble = new ArrayList<>();
    private boolean mAllClickAble = true;

    public void setListener(OnConfirmClickListener callback) {
        this.mCallback = callback;
    }

    private ArrayList<TextView> list =new ArrayList<>();

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_photo,null);

        // 获取自定义Dialog布局中的控件

        lLayout_content = (LinearLayout) view
                .findViewById(R.id.lLayout_content);
        text_confirm = (TextView) view.findViewById(R.id.text_confirm);

        text_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == null){
                    return;
                }
                int position =(Integer) v.getTag();
                    mCallback.onConfirm(position);

            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.DialogTheme);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setTitle(String title ) {
        showTitle = true;
        text_title.setVisibility(View.VISIBLE);
        text_title.setText(title);
        return this;
    }

    public ActionSheetDialog setTitleBackground(int color){
        relativeLayout.setBackgroundResource(color);
        return this ;
    }

    public ActionSheetDialog setTitleColor(int color){
        text_title.setTextColor(color);
        return this ;
    }
    public ActionSheetDialog setTitleSize(float size){
        text_title.setTextSize(size);
        return this ;
    }

    public ActionSheetDialog setConfirmBackground(int color){
        text_confirm .setBackgroundResource(color);
        return this ;
    }

    public ActionSheetDialog setConfirmTextColor(int color){
        text_confirm.setTextColor(color);
        return this ;
    }

    public ActionSheetDialog setConfirmSize (float size){
        text_confirm.setTextSize(size);
        return this ;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     *
     * @param strItem
     *            条目名称
     * @param color
     *            条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,
                                          OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(strItem, color, listener));

        return this;
    }

    /** 设置条目布局 */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sLayout_content
                    .getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }

        // 循环添加条目
        for (int i = 1; i <= size; i++) {

            final  int index = i ;
            SheetItem sheetItem = sheetItemList.get(i-1);
            final String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener  listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            final TextView textView = new TextView(context);
                textView.setText(strItem);
                textView.setTextSize(16);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor(SheetItemColor.Red
                        .getName()));

                list.add(index-1,textView);

                for (Integer ii : mUnclickAble) {
                    if (index == ii) {
                        textView.setTextColor(Color.parseColor("#8B8378"));
                        textView.setEnabled(false);
                    }
                }

                if ( !mAllClickAble ){
                    for(TextView mAllUnClickAble : list){
                        mAllUnClickAble.setTextColor(Color.parseColor("#8B8378"));
                        mAllUnClickAble.setEnabled(false);
                    }
                }

            // 高度
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)textView.getLayoutParams();
            lp.setMargins(0,2,0,0);

            textView.setLayoutParams(lp);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    text_confirm.setTag(index);
                    dissmiss();

                }

            });
            lLayout_content.addView(textView);

        }
    }

    public ArrayList<TextView> getTextView (){
          return list;
    }

    public ActionSheetDialog show() {
        setSheetItems();
        dialog.show();
        return this ;
    }

    public ActionSheetDialog dissmiss() {
        dialog.dismiss();
        return this ;
    }

    public ActionSheetDialog setItemClickable(int index, boolean clickable) {
        if (!clickable) {
            mUnclickAble.add(index);
        }
        return  this ;
    }

    public ActionSheetDialog setAllItemClickable(boolean clickable ){
            mAllClickAble = clickable ;
        return this ;
    }


    public interface OnSheetItemClickListener {
        void onClick(int which);
    }
    public  interface OnConfirmClickListener{
        void onConfirm(int which);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;
        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public enum SheetItemColor {

        Blue("#1874CD"), Red("#000000");
        private String name;
        private SheetItemColor(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }
}
