package com.hy.vrfrog.main.living.push;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hy.vrfrog.R;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.push.ui.BeautyDialogFragment;
import com.hy.vrfrog.main.living.push.ui.TCAudioControl;
import com.hy.vrfrog.main.living.push.utils.TCUtils;
import com.hy.vrfrog.utils.UIUtils;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class PushActivity extends AppCompatActivity implements ITXLivePushListener, View.OnClickListener, BeautyDialogFragment.OnBeautyParamsChangeListener {
    protected String mPushUrl;
    private String mRoomId;
    protected String mUserId;
    private String mTitle;
    private String mCoverPicUrl;
    private String mHeadPicUrl;
    private String mNickName;
    private String mLocation;
    private TXCloudVideoView mTXCloudVideoView;
    private TXLivePusher mTXLivePusher;
    protected TXLivePushConfig mTXPushConfig = new TXLivePushConfig();
    private BeautyDialogFragment.BeautyParams mBeautyParams = new BeautyDialogFragment.BeautyParams();
    private boolean mFlashOn;
    private Button mFlashView;
    private BeautyDialogFragment mBeautyDialogFragment;
    private TCAudioControl mAudioCtrl;
    private Button mBtnAudioCtrl;
    private Button mBtnAudioEffect;
    private Button mBtnAudioClose;
    private LinearLayout mAudioPluginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(TCConstants.USER_ID);
        mPushUrl = intent.getStringExtra(TCConstants.PUBLISH_URL);
        mTitle = intent.getStringExtra(TCConstants.ROOM_TITLE);
        mCoverPicUrl = intent.getStringExtra(TCConstants.COVER_PIC);
        mHeadPicUrl = intent.getStringExtra(TCConstants.USER_HEADPIC);
        mNickName = intent.getStringExtra(TCConstants.USER_NICK);
        mLocation = intent.getStringExtra(TCConstants.USER_LOC);
        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startPublish();
    }

    private void initView() {
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        mFlashView = (Button) findViewById(R.id.flash_btn);
        mBtnAudioEffect = (Button) findViewById(R.id.btn_audio_effect);
        mBtnAudioClose = (Button) findViewById(R.id.btn_audio_close);
        mBtnAudioCtrl = (Button) findViewById(R.id.btn_audio_ctrl);
         mAudioCtrl = (TCAudioControl) findViewById(R.id.layoutAudioControlContainer);
        mAudioPluginLayout = (LinearLayout) findViewById(R.id.audio_plugin);
    }

    private void initData() {
        mBeautyDialogFragment = new BeautyDialogFragment();
        mBeautyDialogFragment.setBeautyParamsListner(mBeautyParams, this);
        //AudioControl
        mAudioCtrl.setPluginLayout(mAudioPluginLayout);
        startPublish();
    }


    protected void startPublish() {
        if (mTXLivePusher == null) {
            mTXLivePusher = new TXLivePusher(PushActivity.this);

            mTXLivePusher.setPushListener(this);
            mTXPushConfig.setAutoAdjustBitrate(false);

            //切后台推流图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pause_publish, options);
            mTXPushConfig.setPauseImg(bitmap);
            mTXPushConfig.setPauseFlag(TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO | TXLiveConstants.PAUSE_FLAG_PAUSE_AUDIO);
            mTXPushConfig.setBeautyFilter(mBeautyParams.mBeautyProgress, mBeautyParams.mWhiteProgress);
            mTXPushConfig.setFaceSlimLevel(mBeautyParams.mFaceLiftProgress);
            mTXPushConfig.setEyeScaleLevel(mBeautyParams.mBigEyeProgress);
            mTXLivePusher.setConfig(mTXPushConfig);
        }

        mAudioCtrl.setPusher(mTXLivePusher);

        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.setVisibility(View.VISIBLE);
            mTXCloudVideoView.clearLog();
        }
//        mBeautySeekBar.setProgress(100);

        //设置视频质量：高清
        mTXLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION);
        mTXCloudVideoView.enableHardwareDecode(true);
        mTXLivePusher.startCameraPreview(mTXCloudVideoView);
        mTXLivePusher.setMirror(true);
        mTXLivePusher.startPusher(mPushUrl);
    }

    protected void stopPublish() {
        if (mTXLivePusher != null) {
            mTXLivePusher.stopCameraPreview(false);
            mTXLivePusher.setPushListener(null);
            mTXLivePusher.stopPusher();
        }
        if (mAudioCtrl != null) {
            mAudioCtrl.unInit();
            mAudioCtrl = null;
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.switch_cam:
                mTXLivePusher.switchCamera();
                break;
            case R.id.flash_btn:
                if (!mTXLivePusher.turnOnFlashLight(!mFlashOn)) {
                    Toast.makeText(getApplicationContext(), "打开闪光灯失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                mFlashOn = !mFlashOn;
                mFlashView.setBackgroundDrawable(mFlashOn ?
                        getResources().getDrawable(R.drawable.icon_flash_pressed) :
                        getResources().getDrawable(R.drawable.icon_flash));

                break;
            case R.id.beauty_btn:
                if (mBeautyDialogFragment.isAdded())
                    mBeautyDialogFragment.dismiss();
                else
                    mBeautyDialogFragment.show(getFragmentManager(), "");
                break;
            case R.id.btn_close:
                showComfirmDialog(TCConstants.TIPS_MSG_STOP_PUSH, false);
//                for(int i = 0; i< 100; i++)
//                    mHeartLayout.addFavor();
                break;

            case R.id.btn_audio_ctrl:
                if (null != mAudioCtrl) {
                    mAudioCtrl.setVisibility(mAudioCtrl.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    /****************************PushListener接口***************************************/
    @Override
    public void onPushEvent(int event, Bundle bundle) {
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.setLogText(null, bundle, event);
        }
        if (event < 0) {
            if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT) {//网络断开，弹对话框强提醒，推流过程中直播中断需要显示直播信息后退出
//                showComfirmDialog(TCConstants.ERROR_MSG_NET_DISCONNECTED, true);
                UIUtils.showTip(TCConstants.ERROR_MSG_NET_DISCONNECTED);
            } else if (event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL) {//未获得摄像头权限，弹对话框强提醒，并退出
//                showErrorAndQuit(TCConstants.ERROR_MSG_OPEN_CAMERA_FAIL);
                UIUtils.showTip(TCConstants.ERROR_MSG_OPEN_CAMERA_FAIL);
            } else if (event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL || event == TXLiveConstants.PUSH_ERR_MIC_RECORD_FAIL) { //未获得麦克风权限，弹对话框强提醒，并退出
            } else {
                //其他错误弹Toast弱提醒，并退出
                Toast.makeText(getApplicationContext(), bundle.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
                mTXCloudVideoView.onPause();
//                stopRecordAnimation();
                finish();
            }
        }

        if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
//            Log.d(TAG, "当前机型不支持视频硬编码");
            mTXPushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640);
            mTXPushConfig.setVideoBitrate(700);
            mTXPushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
            mTXLivePusher.setConfig(mTXPushConfig);
        }

        if (event == TXLiveConstants.PUSH_EVT_PUSH_BEGIN) {
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    /**
     * 显示确认消息
     *
     * @param msg     消息内容
     * @param isError true错误消息（必须退出） false提示消息（可选择是否退出）
     */
    public void showComfirmDialog(String msg, Boolean isError) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ConfirmDialogStyle);
        builder.setCancelable(true);
        builder.setTitle(msg);

        if (!isError) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            //当情况为错误的时候，直接停止推流

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBeautyParamsChange(BeautyDialogFragment.BeautyParams params, int key) {
        switch (key) {
            case BeautyDialogFragment.BEAUTYPARAM_BEAUTY:
            case BeautyDialogFragment.BEAUTYPARAM_WHITE:
                if (mTXLivePusher != null) {
                    mTXLivePusher.setBeautyFilter(params.mBeautyProgress, params.mWhiteProgress);
                }
                break;
            case BeautyDialogFragment.BEAUTYPARAM_FACE_LIFT:
                if (mTXLivePusher != null) {
                    mTXLivePusher.setFaceSlimLevel(params.mFaceLiftProgress);
                }
                break;
            case BeautyDialogFragment.BEAUTYPARAM_BIG_EYE:
                if (mTXLivePusher != null) {
                    mTXLivePusher.setEyeScaleLevel(params.mBigEyeProgress);
                }
                break;
            case BeautyDialogFragment.BEAUTYPARAM_FILTER:
                if (mTXLivePusher != null) {
                    mTXLivePusher.setFilter(TCUtils.getFilterBitmap(getResources(), params.mFilterIdx));
                }
                break;
            case BeautyDialogFragment.BEAUTYPARAM_MOTION_TMPL:
                if (mTXLivePusher != null) {
                    mTXLivePusher.setMotionTmpl(params.mMotionTmplPath);
                }
                break;
            case BeautyDialogFragment.BEAUTYPARAM_GREEN:
                if (mTXLivePusher != null) {
                    mTXLivePusher.setGreenScreenFile(TCUtils.getGreenFileName(params.mGreenIdx));
                }
                break;
            default:
                break;
        }
    }
}
