package com.hy.vrfrog.main.living.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
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
        Button btngoliving2 = (Button) findViewById(R.id.btn_go_living2);
        Button btngoliving = (Button) findViewById(R.id.btn_go_living);

        user = SPUtil.getUser();
        btngoliving2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btngoliving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PushSettingActivity.this, PushActivity.class);
                String PushUrl = "rtmp://9250.livepush.myqcloud.com/live/9250_e75874b6?bizid=9250&txSecret=24c3c5841d23c03031f03a10c8149f18&txTime=59931A7F";
                intent.putExtra(TCConstants.PUBLISH_URL, PushUrl);
                startActivity(intent);

//                if (user != null) {
//                    int uid = user.getResult().getUser().getUid();
//                    HttpCreatRoom(uid + "");
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
                String PushUrl = "rtmp://10263.livepush.myqcloud.com/live/10263_56784498183c4d9d8211c243e60e1f71?bizid=10263&txSecret=951b9be9b06649882126345138166b7b&txTime=5991C8FF";
                intent.putExtra(TCConstants.PUBLISH_URL, PushUrl);
                startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
        });
    }


}
