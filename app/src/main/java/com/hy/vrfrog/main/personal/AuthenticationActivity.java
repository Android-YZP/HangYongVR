package com.hy.vrfrog.main.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.ImageView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.ui.SwipeBackActivity;

/**
 * Created by wzq930102 on 2017/8/11.
 */
public class AuthenticationActivity extends AppCompatActivity{

    private Button mButtonNext;
    private ImageView mIvReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_information);
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
                startActivity(new Intent(getApplicationContext(),ReleaseLiveActivity.class));
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
    }
}
