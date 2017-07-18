package com.jt.base.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jt.base.R;

public class Guide1Activity extends AppCompatActivity {

    private ImageView mIvguidegif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide1);
        mIvguidegif = (ImageView) findViewById(R.id.iv_guide_gif);
        Glide.with(Guide1Activity.this)
                .load(R.mipmap.guide1)
                .into(mIvguidegif);
    }
}
