package com.mytv365.view;

import android.content.Intent;

import com.fhrj.library.MApplication;
import com.fhrj.library.tools.ToolHTTP;
import com.mytv365.common.MyNetworkListener;

/***
 * AjavaSample全局的Application
 * @author Administrator
 */
public class GlobalApplication extends MApplication {


	@Override
	public void onCreate() {
		super.onCreate();
		//启动Service
		Intent mIntent = new Intent(this, MyNetworkListener.class);
		startService(mIntent);
	}
	/**
	 * 退出APP时手动调用
	 */
	@Override
	public void exit() {
		try {
			//停止网络监听
			Intent mIntent = new Intent(this, MyNetworkListener.class);
			stopService(mIntent);
			//取消所有请求
			ToolHTTP.stopAllRequest();
			//关闭所有Activity
			removeAll();
			//退出进程
			System.exit(0);
		} catch (Exception ignored) {
		}
	}
	

}