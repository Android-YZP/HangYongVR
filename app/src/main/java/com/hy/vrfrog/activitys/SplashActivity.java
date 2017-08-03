package com.hy.vrfrog.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hy.vrfrog.R;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {

    private Button btn;
    private Timer mTimer;
    private TimerTask task;
    private int recLen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
        getHomeActivity();
        timekeeping();
    }
    private void getHomeActivity() {
        Timer timer=new Timer();
        TimerTask task=new TimerTask(){
            public void run(){
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        };
        timer.schedule(task, 3000);
    }
    private void initUI() {
        btn = (Button) findViewById(R.id.btn_splash);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,R.anim.fade_out);
            }
        });
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade,R.anim.hold);
    }


    @Override
    public void onBackPressed() {
    }
    /**
     * 计时重新发送验证码
     */
    private void timekeeping() {
        recLen = 4;
        mTimer = new Timer();
        // UI thread
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        recLen--;
                        btn.setText(recLen + "跳过" );
                        if (recLen < 1) {
                            mTimer.cancel();

                        }
                    }
                });
            }
        };
        //从现在起过10毫秒以后，每隔1000毫秒执行一次。
        mTimer.schedule(task, 10, 1000);    // timeTask
    }
}
