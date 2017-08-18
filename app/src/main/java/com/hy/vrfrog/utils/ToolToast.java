package com.hy.vrfrog.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.vrfrog.R;
import com.hy.vrfrog.application.VrApplication;


/***
 * 自定义Toast控件
 */
public class ToolToast {
	
	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	}; 
	
	/**
	 * 弹出较长时间提示信息
	 * @param context 上下文对象
	 * @param msg 要显示的信息
	 */
	public static void showLong(Context context, String msg){
		buildToast(context,msg, Toast.LENGTH_LONG);
	}
	
	/**
	 * 弹出较长时间提示信息
	 * @param msg 要显示的信息
	 */
	public static void showLong(String msg){
		buildToast(VrApplication.getContext(),msg, Toast.LENGTH_LONG);
	}
	
	/**
	 * 弹出较短时间提示信息
	 * @param context 上下文对象
	 * @param msg 要显示的信息
	 */
	public static void showShort(Context context, String msg){
		buildToast(context,msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 弹出较短时间提示信息
	 * @param msg 要显示的信息
	 */
	public static void showShort(String msg){
		buildToast(VrApplication.getContext(),msg, Toast.LENGTH_SHORT);
	}
	
	/**
	 * 构造Toast
	 * @param context 上下文
	 * @return
	 */
	public static Toast buildToast(Context context, String msg, int duration){
		return buildToast(context,msg,duration,"#D8000000",16);
	}


	/**
	 * 构造Toast
	 * @param context 上下文
	 * @param msg 消息
	 * @param duration 显示时间
	 * @param bgColor 背景颜色
	 * @return
	 */
	public static Toast buildToast(Context context, String msg, int duration, String bgColor){
		return buildToast(context,msg,duration,bgColor,16);
	}
	
	
	/**
	 * 构造Toast
	 * @param context 上下文
	 * @param msg	消息
	 * @param duration 显示时间
	 * @param bgColor 背景颜色
	 * @param textSp  文字大小
	 * @return
	 */
	public static Toast buildToast(Context context, String msg, int duration, String bgColor, int textSp){
		return buildToast(context,msg,duration,bgColor,textSp,10);
	}
	
	/**
	 * 构造Toast
	 * @param context 上下文
	 * @param msg	消息
	 * @param duration 显示时间
	 * @param bgColor 背景颜色
	 * @param textSp  文字大小
	 * @param cornerRadius  四边圆角弧度
	 * @return
	 */
	@SuppressLint({ "NewApi", "InlinedApi", "ShowToast" })
	public static Toast buildToast(final Context context, final String msg, final int duration, final String bgColor, final int textSp, final int cornerRadius){
		if(null == mToast){
			//构建Toast
			mToast = Toast.makeText(context, null, duration);
		}

		   mToast.setGravity(Gravity.CENTER,0,0);
		   View view = LayoutInflater.from(context).inflate(
				R.layout.pay_success,null);
			mToast.setView(view);
		mToast.show();

		return mToast;
	}
}
