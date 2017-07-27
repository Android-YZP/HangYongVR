package com.jt.base.updtaeapk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.UpdateVersionBean;
import com.jt.base.utils.DialogUtils;
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

import static com.jt.base.R.drawable.close;

/**
 * Created by qtfreet on 2016/1/5.
 */
public class CheckUpdate {

    private static final int HTTP_SUCCESS = 0;
    private Button mUpdate;
    private Button mClose;

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
        WindowManager wm = (WindowManager) mcontext
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        int versionCode = getVerCode(mcontext);

        if (newVersion > versionCode) {
            final DialogUtils dialogUtils = new DialogUtils(mcontext);

            View contentView = LayoutInflater.from(mcontext).inflate(
                    R.layout.update, null);
            dialogUtils.setContentView(contentView).setContentViewSize(width * 2 / 3, height * 3 / 4);//ps获取议一下屏幕宽和高，然后通过设置百分比形式适配屏幕
            dialogUtils.setXY(0, height / 4);
            dialogUtils.setGravity(Gravity.CENTER_HORIZONTAL);
            dialogUtils.show();
            mUpdate = (Button) contentView.findViewById(R.id.btn_update);
            mClose = (Button) contentView.findViewById(R.id.btn_update_close);
            TextView mUpdateContent = (TextView) contentView.findViewById(R.id.tv_update_content);
            mUpdateContent.setText(intro);
            mClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogUtils.getBaseDialog().dismiss();
                }
            });
            mUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, DownloadService.class);
                    intent.putExtra("url", url);
                    mcontext.startService(intent);
                    dialogUtils.getBaseDialog().dismiss();
                }
            });
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
//                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
            }

        });
    }

}
