package com.jt.base.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jt.base.R;
import com.jt.base.utils.SPUtil;

public class Guide2Activity extends AppCompatActivity {
    private ImageView mIvguidegif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);
        overridePendingTransition(R.anim.fade,R.anim.hold);
        mIvguidegif = (ImageView) findViewById(R.id.iv_guide_gif);
        Glide.with(Guide2Activity.this)
                .load(R.mipmap.guide2)
                .into(mIvguidegif);
        mIvguidegif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.put(Guide2Activity.this,"Guide1",true);
                finish();
            }
        });
    }
}
