package com.jt.base.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Window;
import android.view.WindowManager;


/**
 * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
 * 可以直接调用getWindow(),表示获得这个Activity的Window
 * 对象,这样这可以以同样的方式改变这个Activity的属性.
 */
//        Window dialogWindow = dialog.getWindow();
//                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         *
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
//                lp.x = 100; // 新位置X坐标
//                lp.y = 100; // 新位置Y坐标
//                lp.width = 300; // 宽度
//                lp.height = 300; // 高度
//                lp.alpha = 0.7f; // 透明度

                // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
                // dialog.onWindowAttributesChanged(lp);
//                dialogWindow.setAttributes(lp);

public class BaseDialog extends Dialog{

    private Context context;

    private Window dialogWindow;

    private WindowManager.LayoutParams dialogLayoutParams;

    public BaseDialog(@NonNull Context context){
        super(context);
        this.context = context;
        init();
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }


    private void init(){
        dialogWindow = this.getWindow();
        dialogLayoutParams = dialogWindow.getAttributes();
        setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
    }

    public void setGravity(int gravity){
        dialogWindow.setGravity(gravity);
    }

    public Window getDialogWindow(){
        return dialogWindow;
    }

    public WindowManager.LayoutParams getDialogLayoutParams(){
        return dialogLayoutParams;
    }
}
