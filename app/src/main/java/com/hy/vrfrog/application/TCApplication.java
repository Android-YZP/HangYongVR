package com.hy.vrfrog.application;

/**
 * Created by 姚中平 on 2017/8/2.
 */

import com.hy.vrfrog.timroomchat.TimConfig;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;


/**
 * 小直播应用类，用于全局的操作，如
 * sdk初始化,全局提示框
 */
public class TCApplication extends VrApplication {

//    private RefWatcher mRefWatcher;

    private static TCApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initSDK();

    }

    public static TCApplication getApplication() {
        return instance;
    }


    public void initSDK() {
        //初始化SDK基本配置
        TIMSdkConfig config = new TIMSdkConfig(TimConfig.Appid)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG);

        //初始化SDK
        TIMManager.getInstance().init(getApplicationContext(), config);
    }

}


