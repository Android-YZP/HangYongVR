package com.hy.vrfrog.main.living.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.SearchTopicBean;
import com.hy.vrfrog.main.activitys.Main2Activity;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

public class PushSettingActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_setting);
        Button btngoliving = (Button) findViewById(R.id.btn_go_living);

        user = SPUtil.getUser();


        btngoliving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (user != null) {
                    int uid = user.getResult().getUser().getUid();
                    HttpCreatRoom(uid + "");

//                } else {
//                    UIUtils.showTip("请先登录");
//                }
            }
        });


    }


    /**
     * 根据关键词搜索话题
     */
    private void HttpCreatRoom(final String UID) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.createRoom);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("uid", UID);

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("-----------", result);

                Intent intent = new Intent(PushSettingActivity.this, PushActivity.class);
                String PushUrl = "rtmp://9250.livepush.myqcloud.com/live/9250_erte?bizid=9250&txSecret=1f728c3719bc7a51cd38de46bcbff49c&txTime=598B317F";
                intent.putExtra(TCConstants.PUBLISH_URL, PushUrl);
                startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
        });
    }


}
