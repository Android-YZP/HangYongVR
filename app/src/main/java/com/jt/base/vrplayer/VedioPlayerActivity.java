package com.jt.base.vrplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import com.jt.base.R;
import com.jt.base.utils.PlayUtils;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.snail.media.player.ISnailPlayer;
import org.xutils.common.util.LogUtil;

public class VedioPlayerActivity extends AppCompatActivity {
    private static final int GESTURE_TYPE_NO = 0;
    private static final int GESTURE_TYPE_HRO = 1;
    private static final int GESTURE_TYPE_VER = 2;
    private static final int SNVR_PROJ_PLANE = 0;
    private static final int SNVR_PROJ_SPHERE = 1;
    private static final int SNVR_VIDEO_SPLICE_FMT_2D = 0;
    public static final int SNVR_NAVIGATION_SENSOR = 0;
    private int mDuration;//内容时长
    public static final int SCALE_10 = 1;
    public static final int SNVR_SINGLE_EYES_MODE = 0;
    private SnailPlayerVideoView mVideoView;
    private int mFov = 85;
    private int mProjectionType = SNVR_PROJ_PLANE;
    private int mVideoSpliceFormat = SNVR_VIDEO_SPLICE_FMT_2D;
    private int mNavigationMode = PlayActivity.SNVR_NAVIGATION_BOTH;
    private int mEyesMode = PlayActivity.SNVR_DOUBLE_EYES_MODE;
    private String mPlayUrl;
    private int mScale = SCALE_10;
    private SnailNetReceiver mNetReceiver;
    private SnailNetReceiver.NetStateChangedListener mNetChangedListener;
    private AlertDialog show;
    private RelativeLayout mBufferingView;
    private GestureDetector mGestureDetector = null;
    private int cur_gesture_type = GESTURE_TYPE_NO;
    private SeekBar mSbPlayerControl;
    private AudioManager mAudioManager;
    boolean isShowControl = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
    };
    private RelativeLayout mPlayerControl;
    private RelativeLayout mRlPlayerControl;
    private ImageButton mPlayerControlStart;
    boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //直播界面是竖屏显示,//播放器界面是横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setContentView(R.layout.activity_vedio_player);

        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        mPlayUrl = getIntent().getStringExtra(Definition.KEY_PLAY_URL);
        initView();
        initPlayerListener();
        initListener();
        initplayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //网络的广播监听
        mNetReceiver.registNetBroadCast(this);
        mNetReceiver.addNetStateChangeListener(mNetChangedListener);
        if (mVideoView != null) {
            mVideoView.setPlayFov(mFov);
            mVideoView.setScale(mScale);
            if (mVideoView.IsSurfaceHolderValid()) {
                mVideoView.resetUrl();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mNetReceiver.remoteNetStateChangeListener(mNetChangedListener);
        mNetReceiver.unRegistNetBroadCast(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stop();
            mVideoView.stopPlayback();
            mVideoView = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void initView() {
        mVideoView = (SnailPlayerVideoView) findViewById(R.id.id_videoview);
        mBufferingView = (RelativeLayout) findViewById(R.id.id_mediaplay_buffering_view);
        mSbPlayerControl = (SeekBar) findViewById(R.id.sb_video_player_control);
        mPlayerControl = (RelativeLayout) findViewById(R.id.video_player_control);
        mPlayerControlStart = (ImageButton) findViewById(R.id.video_player_start);

    }


    private void initListener() {

        mPlayerControlStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPlaying) {
                    mPlayerControlStart.setBackground(UIUtils.getDrawable(R.mipmap.video_player_stop));
                    isPlaying = false;
                    mVideoView.pause();
                } else {
                    mPlayerControlStart.setBackground(UIUtils.getDrawable(R.mipmap.video_player_start));
                    isPlaying = true;
                    mVideoView.start();
                }
            }
        });


        /**
         *  得到视频的长度(ms)/100 = 每一份的长度
         *  这个长度再乘以进度条的百分比.
         */

        //拖动播放器进度条
        mSbPlayerControl.setThumbOffset(1);
        mSbPlayerControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtil.i(seekBar.getProgress() + "");
                mVideoView.seekTo(seekBar.getProgress());
                String time = PlayUtils.generateTime(seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }


    private void initplayer() {
        mVideoView.setVideoPlayerType(ISnailPlayer.PlayerType.PLAYER_TYPE_SNAIL_VR);
        mVideoView.setPlayFov(mFov);
        mVideoView.setProjectionType(mProjectionType);
        mVideoView.setNavigationmode(mNavigationMode);
        mVideoView.setVideoSpliceFormat(mVideoSpliceFormat);
        mVideoView.setScale(SCALE_10);
        mVideoView.setVideoPath(mPlayUrl);
        initPlayMode();
    }


    //初始化播放器模式
    private void initPlayMode() {
        int vedioode = getIntent().getIntExtra(Definition.PLEAR_MODE, 4);
        if (vedioode == VedioContants.TWO_D_VEDIO) {
            mEyesMode = SNVR_SINGLE_EYES_MODE;
            mProjectionType = SNVR_PROJ_PLANE;
        } else if (vedioode == VedioContants.ALL_VIEW_VEDIO) {

            mEyesMode = SNVR_SINGLE_EYES_MODE;
            mProjectionType = SNVR_PROJ_SPHERE;
        }
        mVideoView.setProjectionType(mProjectionType);
        mVideoView.setEyesMode(mEyesMode);
    }

    /**
     * 播放器的各种监听
     */
    private void initPlayerListener() {


        //播放去状态监听
        mVideoView.setOnStatListener(new ISnailPlayer.ISnailPlayerStateChangeNotification() {
            @Override
            public void notify(ISnailPlayer player, ISnailPlayer.State state) {

                if (state == ISnailPlayer.State.PLAYER_STARTED) {
                    mVideoView.start();
                    mDuration = mVideoView.getDuration();


                    if (mDuration == 0) {
                        mSbPlayerControl.setEnabled(false);
                    } else {
                        mSbPlayerControl.setEnabled(true);
                        mSbPlayerControl.setMax(mDuration);
                    }
                } else if (state == ISnailPlayer.State.PLAYER_STARTING) {

                } else if (state != ISnailPlayer.State.PLAYER_STOP) {

                }

            }
        });
        //播放器错误监听
        mVideoView.setOnErrorListener(new ISnailPlayer.ISnailPlayerErrorNotification() {

            @Override

            public void onError(ISnailPlayer mp, ISnailPlayer.ErrorType error, int extra) {

                Toast.makeText(VedioPlayerActivity.this,
                        "error code:(" + error + "," + extra + ")",
                        Toast.LENGTH_LONG).show();
            }
        });

        //播放器事件通知
        mVideoView.setOnEventListener(new ISnailPlayer.ISnailPlayerEventNotification() {

            @Override
            public boolean notify(ISnailPlayer mp, ISnailPlayer.EventType what, int extra) {
                if (what == ISnailPlayer.EventType.PLAYER_EVENT_BUFFERING) {
                    mBufferingView.setVisibility(View.VISIBLE);
                } else if (what == ISnailPlayer.EventType.PLAYER_EVENT_BUFFERED) {//缓冲结束
                    mBufferingView.setVisibility(View.GONE);
                } else if (what == ISnailPlayer.EventType.PLAYER_EVENT_FINISHED) {//播放结束
                    mBufferingView.setVisibility(View.GONE);
                    UIUtils.showTip("播放结束");
                    mVideoView.stop();
                }
                return true;
            }
        });


        mNetReceiver = SnailNetReceiver.getInstance();
        mNetChangedListener = new SnailNetReceiver.NetStateChangedListener() {

            @Override
            public void onNetStateChanged(SnailNetReceiver.NetState netCode) {
                switch (netCode) {
                    case NET_2G:
                    case NET_3G:
                    case NET_4G:
                        UIUtils.showTip("当前在非wifi状态下,注意流量~>_<~");
                        break;
                    case NET_UNKNOWN:
                        // Log.i(Constants.LOG_TAG, "未知网络");
                        UIUtils.showTip("未知网络");
                        break;
                    case NET_NO://没有网络
                        showNetErrorDialog();
                        break;
                    default:
                }
            }
        };

        initGesture();
    }

    /**
     * 初始化手势
     */
    private void initGesture() {
        /**
         * 屏幕的手势识别
         */
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                toggleMediaControlsVisiblity();
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float nFristX = e1.getX();
                float nFristY = e1.getY();
                int video_width = mVideoView.getWidth();
                int video_height = mVideoView.getHeight();
                float nCurrentX = e2.getRawX();
                float nCurrentY = e2.getRawY();

                int movePosX = (int) Math.abs(distanceX);
                int movePosY = (int) Math.abs(distanceY);

                if (mNavigationMode != SNVR_NAVIGATION_SENSOR) {
                    float phi = distanceX * 360 / video_width;
                    float theta = distanceY * mFov / video_height;
                    mVideoView.setTouchInfo(phi, theta);

                } else {
                    getGestureDirection(movePosX, movePosY);

                    if (cur_gesture_type == GESTURE_TYPE_VER) {
                        float _percent = (nFristY - nCurrentY) / video_height;

                        if (nFristX > video_width / 2) {
                            UIUtils.showTip("right");
                            onVolumeSlide(_percent);
                        } else {
                            UIUtils.showTip("Left");
                        }
                    } else if (cur_gesture_type == GESTURE_TYPE_HRO) {
                        UIUtils.showTip("横向移动");
                    }
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                return false;
            }
        });
    }

    private void endGesture() {
//        mVolume = -1;
//        mBrightness = -1f;
        cur_gesture_type = GESTURE_TYPE_NO;
//        mOperLayout.setVisibility(View.GONE);
    }

    //网络错误对话框
    private void showNetErrorDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(VedioPlayerActivity.this, R.style.MyDialogStyle);
        normalDialog.setCancelable(false);
        normalDialog.setMessage("网络被拐走了...");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.dismiss();
                        VedioPlayerActivity.this.finish();
                    }
                });
        show = normalDialog.show();
    }


    private void getGestureDirection(float nX, float nY) {

        if (cur_gesture_type == GESTURE_TYPE_NO) {
            if (nX == 0) {
                if (nY > 15) {
                    cur_gesture_type = GESTURE_TYPE_VER;
                }
            } else if (nY == 0) {
                if (nX > 5) {
                    cur_gesture_type = GESTURE_TYPE_HRO;
                }
            } else {
                if (nX / nY > 3) {
                    cur_gesture_type = GESTURE_TYPE_HRO;
                } else if (nY / nX > 3) {
                    cur_gesture_type = GESTURE_TYPE_VER;
                }
            }
        }
    }


    /**
     * 滑动改变声音大小
     */
    private void onVolumeSlide(float percent) {
//        if (mVolume == -1) {
//            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//            if (mVolume < 0)
//                mVolume = 0;
//            // 显示
//            mOperationBg.setImageResource(R.drawable.video_player_voice);
//            mOperLayout.setVisibility(View.VISIBLE);
//        }
//
//        int index = (int) (percent * mMaxVolume) + mVolume;
//        if (index > mMaxVolume)
//            index = mMaxVolume;
//        else if (index < 0)
//            index = 0;
//        // 变更声音
//        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
//
//        // 变更进度条
//        int present = index * 100 / mMaxVolume;
//        Log.i(TAG, "present is:" + present);
//        mOperTextView.setText(String.valueOf(present) + "%");
    }


    private void toggleMediaControlsVisiblity() {
        if (mPlayerControl.getVisibility() == View.VISIBLE) {
            mPlayerControl.setVisibility(View.GONE);
        } else {
            mPlayerControl.setVisibility(View.VISIBLE);
        }
    }
}
