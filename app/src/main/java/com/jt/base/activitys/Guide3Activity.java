package com.jt.base.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jt.base.R;

public class Guide3Activity extends AppCompatActivity {
    private ImageView mIvguidegif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide3);
        overridePendingTransition(R.anim.fade,R.anim.hold);
        mIvguidegif = (ImageView) findViewById(R.id.iv_guide_gif);
        Glide.with(Guide3Activity.this)
                .load(R.mipmap.guide3)
                .into(mIvguidegif);
        mIvguidegif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Guide3Activity.this, Guide4Activity.class));
            }
        });
    }
}
