package com.hy.vrfrog.main.living.livingplay;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hy.vrfrog.R;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.livingplay.ui.TCBaseActivity;
import com.hy.vrfrog.main.living.livingplay.ui.TCInputTextMsgDialog;
import com.hy.vrfrog.utils.UIUtils;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivingPlayActivity extends TCBaseActivity implements ITXLivePlayListener {
    private static final String TAG = "--------------------";
    private TXLivePlayer mTXLivePlayer;
    private TXCloudVideoView mTXCloudVideoView;
    private TXLivePlayConfig mTXPlayConfig = new TXLivePlayConfig();
    private String mPusherNickname;
    protected String mPusherId;
    //    protected String mPlayUrl = "rtmp://10263.liveplay.myqcloud.com/live/10263_90cc6e99e089450fb5e2c5acc57574b4";
    protected String mPlayUrl = "rtmp://10263.liveplay.myqcloud.com/live/10263_56784498183c4d9d8211c243e60e1f71";
    private String mGroupId = "";
    private String mFileId = "";
    protected String mUserId = "";
    protected String mNickname = "";
    protected String mHeadPic = "";
    private int mUrlPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;      //根据mIsLivePlay和url判断出的播放类型，更加具体
    private boolean mPlaying = false;
    private TCInputTextMsgDialog mInputTextMsgDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_living_play);
        intent = getIntent();
        initPlay();
    }

    private void initPlay() {
        //mPlayerView即step1中添加的界面view
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        //创建player对象
        TXLivePlayer mLivePlayer = new TXLivePlayer(this);
        //关键player对象与界面view
        mLivePlayer.setPlayListener(this);
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setPlayerView(mTXCloudVideoView);
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);//铺满全屏
        mLivePlayer.enableHardwareDecode(false);

        TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();
        //自动模式
        mPlayConfig.setAutoAdjustCacheTime(true);
        mPlayConfig.setMinAutoAdjustCacheTime(1);
        mPlayConfig.setMaxAutoAdjustCacheTime(5);
        mLivePlayer.setConfig(mPlayConfig);

        mLivePlayer.startPlay(mPlayUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);//推荐FLV
    }


//    PhoneStateListener listener = new PhoneStateListener() {
//        @Override
//        public void onCallStateChanged(int state, String incomingNumber) {
//            super.onCallStateChanged(state, incomingNumber);
//            switch (state) {
//                //电话等待接听
//                case TelephonyManager.CALL_STATE_RINGING:
//                    if (mTXLivePlayer != null) mTXLivePlayer.setMute(true);
//                    break;
//                //电话接听
//                case TelephonyManager.CALL_STATE_OFFHOOK:
//                    if (mTXLivePlayer != null) mTXLivePlayer.setMute(true);
//                    break;
//                //电话挂机
//                case TelephonyManager.CALL_STATE_IDLE:
//                    if (mTXLivePlayer != null) mTXLivePlayer.setMute(false);
//                    break;
//            }
//        }
//    };


    protected void startPlay() {

        if (mTXLivePlayer == null) {
            mTXLivePlayer = new TXLivePlayer(this);
        }
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.clearLog();
        }
        mTXLivePlayer.setPlayerView(mTXCloudVideoView);
        mTXLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mTXLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mTXLivePlayer.setPlayListener(this);
        mTXLivePlayer.setConfig(mTXPlayConfig);

        int result;
        result = mTXLivePlayer.startPlay(mPlayUrl, mUrlPlayType);

        if (0 != result) {

        } else {
            mPlaying = true;
        }
    }


    protected void stopPlay(boolean clearLastFrame) {
        if (mTXLivePlayer != null) {
            mTXLivePlayer.setPlayListener(null);
            mTXLivePlayer.stopPlay(clearLastFrame);
            mPlaying = false;
        }
    }


    /**
     * 发消息弹出框
     */
    private void showInputMsgDialog() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mInputTextMsgDialog.getWindow().getAttributes();

        lp.width = (display.getWidth()); //设置宽度
        mInputTextMsgDialog.getWindow().setAttributes(lp);
        mInputTextMsgDialog.setCancelable(true);
        mInputTextMsgDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mInputTextMsgDialog.show();
    }


    @Override
    public void onPlayEvent(int i, Bundle bundle) {
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }
}
