package com.hy.vrfrog.main.personal;




import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.hy.vrfrog.R;
import com.hy.vrfrog.base.BaseActivity;

/**
 * Created by qwe on 2017/8/15.
 */

public class ReleaseLiveActivity extends AppCompatActivity {

    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_release_live);
        initView();
        initListener();
    }



    private void initView() {

        mBack = (ImageView)findViewById(R.id.img_release_live_return);

    }

    private void initListener() {

    }

}
