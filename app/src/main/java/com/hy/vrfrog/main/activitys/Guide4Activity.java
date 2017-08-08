package com.hy.vrfrog.main.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.vrfrog.R;
import com.hy.vrfrog.utils.SPUtil;

public class Guide4Activity extends AppCompatActivity {
    private ImageView mIvguidegif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide4);
        overridePendingTransition(R.anim.fade,R.anim.hold);
        mIvguidegif = (ImageView) findViewById(R.id.iv_guide_gif);
        Glide.with(Guide4Activity.this)
                .load(R.mipmap.guide4)
                .into(mIvguidegif);
        mIvguidegif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SPUtil.put(Guide4Activity.this,"Guide3",true);
            }
        });
    }
}
