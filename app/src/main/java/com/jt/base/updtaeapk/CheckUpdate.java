package com.jt.base.updtaeapk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.UpdateVersionBean;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by qtfreet on 2016/1/5.
 */
public class CheckUpdate {

    private static final int HTTP_SUCCESS = 0;

    //单例化检查更新类
    private CheckUpdate() {
    }

    private Context mcontext;
    private boolean isAutoCheck;
    private static CheckUpdate checkUpdate = null;

    public static CheckUpdate getInstance() {
        if (checkUpdate == null) {
            checkUpdate = new CheckUpdate();
        }
        return checkUpdate;
    }


    public void startCheck(Context context, boolean isAutoCheck) {
        mcontext = context;
        this.isAutoCheck = isAutoCheck;
        HttpUpdateVersion("222");
    }


    private void compareVersion(int newVersion, String intro, final String url) {
        int versionCode = getVerCode(mcontext);

        if (newVersion > versionCode) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            builder.setTitle("发现更新");
            builder.setMessage(intro);
            builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(mcontext, DownloadService.class);
                    intent.putExtra("url", url);
                    mcontext.startService(intent);
                    LogUtil.i(url + "");
                }
            });
            builder.setNegativeButton("退出", null);
            builder.show();
        } else {
            if (!isAutoCheck) {
                Toast.makeText(mcontext, "当前已是最新版本", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private int getVerCode(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }


    /**
     * 检查更新版本
     */
    private void HttpUpdateVersion(String sourceNum) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.UpdateVersion);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("sourceNum", sourceNum);//用户名
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                UpdateVersionBean updateVersionBean = new Gson().fromJson(result, UpdateVersionBean.class);
                if (updateVersionBean.getCode() == HTTP_SUCCESS) {
                    int version = updateVersionBean.getResult().getVersionNum();

                    compareVersion(version, updateVersionBean.getResult().getIntroduce(), HttpURL.APK_HOST + updateVersionBean.getResult().getUrl()); //与本地版本进行比较
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
            }

        });
    }

}
