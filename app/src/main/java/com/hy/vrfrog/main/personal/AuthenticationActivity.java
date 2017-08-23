package com.hy.vrfrog.main.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.CertificationBean;
import com.hy.vrfrog.utils.BasePreferences;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by wzq930102 on 2017/8/11.
 */
public class AuthenticationActivity extends AppCompatActivity{

    private Button mButtonNext;
    private ImageView mIvReturn;
    private EditText mName;
    private EditText mAutherId;
    private User user;
    private CheckBox mInformastionCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_information);
        user = SPUtil.getUser();
        initUI();
        initListener();
    }

    private void initListener() {
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), UploadingDocumentsActivity.class));
//                overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
//                finish();
                if (user != null) {
                    int uid = user.getResult().getUser().getUid();
                    HttpCreatRoom(uid + "");
                    startActivity(new Intent(AuthenticationActivity.this,ReleaseLiveActivity.class));
                    BasePreferences basePreferences = new BasePreferences(AuthenticationActivity.this);
                    basePreferences.setPrefBoolean("certificate",true);
                } else {
                    UIUtils.showTip("请先登录");
                }


            }
        });
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initUI() {
        mButtonNext = (Button) findViewById(R.id.btn_information_next);
        mIvReturn = (ImageView) findViewById(R.id.tv_information_return);
        mName = (EditText)findViewById(R.id.et_information_name);
        mAutherId = (EditText)findViewById(R.id.et_information_num);
        mInformastionCb = (CheckBox)findViewById(R.id.cb_information_agree);
    }

    //实名认证，创建房间

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
                LogUtil.i("实名认证 = " + result);
                CertificationBean certificationBean = new Gson().fromJson(result,CertificationBean.class);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                super.onFinished();

            }
        });
    }

}
