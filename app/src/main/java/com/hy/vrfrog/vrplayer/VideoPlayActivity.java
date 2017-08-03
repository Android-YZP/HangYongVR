package com.hy.vrfrog.vrplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.vrplayer.SnailNetReceiver.NetStateChangedListener;
import com.hy.vrfrog.vrplayer.seekbar.DiscreteSeekBar;
import com.hy.vrfrog.vrplayer.utils.DialogUtils;
import com.snail.media.player.ISnailPlayer;
import com.snail.media.player.ISnailPlayer.EventType;
import com.snail.media.player.ISnailPlayer.ISnailPlayerErrorNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerEventNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerStateChangeNotification;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 姚中平
 * @version 2.0
 * @date 创建于 2017/7/27
 * @description
 */
public class VideoPlayActivity extends AppCompatActivity {
    private static final String TAG = "PlayActivity";
    private static final int HTTP_SUCCESS = 0;

    private SnailPlayerVideoView mVideoView;

    private ImageView mImageView_Back;

    private ImageView mImageView_PlayPause;
    private ImageView mImageView_Reload;

    private RelativeLayout mBufferingView;
    private TextView mTextViewBufferPercent;

    private final String mUrl = "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8";
    private String mPlayUrl = "";

    private GestureDetector mGestureDetector = null;

    private static final int GESTURE_TYPE_NO = 0;
    private static final int GESTURE_TYPE_HRO = 1;
    private static final int GESTURE_TYPE_VER = 2;

    private static final int SNVR_PROJ_PLANE = 0;
    private static final int SNVR_PROJ_SPHERE = 1;
    private static final int SNVR_PROJ_DOME = 2;

    private static final int SNVR_VIDEO_SPLICE_FMT_2D = 0;
    private static final int SNVR_VIDEO_SPLICE_FMT_3D_SBS = 1;
    private static final int SNVR_VIDEO_SPLICE_FMT_3D_OVU = 2;

    public static final int SNVR_NAVIGATION_SENSOR = 0;
    public static final int SNVR_NAVIGATION_TOUCH = 1;
    public static final int SNVR_NAVIGATION_BOTH = 2;


    public static final int SCALE_05 = 0;
    public static final int SCALE_10 = 1;
    public static final int SCALE_20 = 2;

    public static final int SNVR_SINGLE_EYES_MODE = 0;
    public static final int SNVR_DOUBLE_EYES_MODE = 1;

    private int cur_gesture_type = GESTURE_TYPE_NO;
    private int cur_aspect_type = SnailPlayerVideoView.ASPECT_TYEP_AUTO_FIT;

    private boolean mIsPrepared = false;

    private boolean mIsVRMode = true;

    private boolean mIsLive = true;

    private boolean mShowing = true;

    private AudioManager mAudioManager;

    private static final int SHOW_PROGRESS = 2;
    int mReloading = 1;

    /**
     * 最大声音
     */
    private int mMaxVolume;
    /**
     * 当前声音
     */
    private int mVolume = -1;
    /**
     * 当前亮度
     */
    private float mBrightness = -1f;

    /**
     * 调节亮度和声音的控件
     */
    private RelativeLayout mOperLayout;
    private ImageView mOperationBg;
    private TextView mOperTextView;
    private RelativeLayout mLayoutPlayerControllerFull;
    private SeekBar mSeekBar;
    private TextView mCurrentTime;
    private TextView mEndTime;
    private int mDuration;
    private boolean mDragging;
    private TimerTask task;
    private int recLen;
    private Timer mTimer;
    private int mFov = 87;
    private int mProjectionType = SNVR_PROJ_PLANE;
    private int mVideoSpliceFormat = SNVR_VIDEO_SPLICE_FMT_2D;
    private int mNavigationMode = SNVR_NAVIGATION_BOTH;
    private int mEyesMode = SNVR_SINGLE_EYES_MODE;
    private int mScale = SCALE_10;
    private int pausePostion;
    public static final int FOV_DEFAULT = 90;
    private SnailNetReceiver mNetReceiver;
    private NetStateChangedListener mNetChangedListener;
    private AlertDialog show;
    private ImageButton mIbBack;
    private String desc;
    private int vedioode;
    private int playType;
    private int mVid;
    private int mVideoPosition;
    private boolean isRestart = false;
    private boolean isBack = false;
    private ImageButton mIbDoubleEye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_video_play);
        initPlayView();
        playControll();
        playGesture();
        LogUtil.i(mPlayUrl + "");
        initPlayMode();
    }


    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/7/27
     * @description 播放器控制器
     */
    private void playControll() {
        mLayoutPlayerControllerFull = (RelativeLayout) findViewById(R.id.id_mediaplayer_controller);
        mSeekBar = (SeekBar) findViewById(R.id.id_video_player_seekbar);
        mCurrentTime = (TextView) findViewById(R.id.id_video_player_current_time);
        mEndTime = (TextView) findViewById(R.id.id_video_player_total_time);
        mIbBack = (ImageButton) findViewById(R.id.ib_play_back);
        mIbDoubleEye = (ImageButton) findViewById(R.id.ib_double_eye);
        mSeekBar.setThumbOffset(1);
        mIbBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBack) {
                    isBack = false;
                    Intent intent = new Intent();
                    intent.putExtra("position", currentPersent());
                    setResult(101, intent);
                    VideoPlayActivity.this.finish();
                }
            }
        });
        //双眼模式
        mIbDoubleEye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEyesMode == SNVR_DOUBLE_EYES_MODE) {
                    mEyesMode = SNVR_SINGLE_EYES_MODE;
                } else if (mEyesMode == SNVR_SINGLE_EYES_MODE) {
                    mEyesMode = SNVR_DOUBLE_EYES_MODE;
                }
                mVideoView.setEyesMode(mEyesMode);
            }
        });

        timekeeping();
        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mVideoView.seekTo(seekBar.getProgress());
                mHandler.removeMessages(SHOW_PROGRESS);
                mDragging = false;
                mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);
                mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (!fromUser)
                    return;
                //int newposition = (mDuration * progress) / 1000;
                String time = generateTime(progress);
                if (mCurrentTime != null)
                    mCurrentTime.setText(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeMessages(SHOW_PROGRESS);
                mDragging = true;
                mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            }
        });
        mImageView_PlayPause = (ImageView) findViewById(R.id.id_imageview_play_pause_full);
        mImageView_PlayPause.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mImageView_PlayPause
                            .setBackgroundResource(R.drawable.btn_selector_player_play_big);
                } else {
                    mVideoView.start();
                    mImageView_PlayPause
                            .setBackgroundResource(R.drawable.btn_selector_player_pause_big);
                }
            }
        });
        mBufferingView = (RelativeLayout) findViewById(R.id.id_mediaplay_buffering_view);
        mTextViewBufferPercent = (TextView) findViewById(R.id.tv_buffering);
        mBufferingView.setVisibility(View.GONE);
        // 声音和亮度调节图标
        mOperLayout = (RelativeLayout) findViewById(R.id.layout_volume_bright_transparent);
        mOperationBg = (ImageView) findViewById(R.id.video_player_voiceortranparent_img);
        mOperTextView = (TextView) findViewById(R.id.video_player_voiceortranparent_value);
    }

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/7/27
     * @description 初始化播放器
     */
    private void initPlayView() {
        mVideoView = (SnailPlayerVideoView) findViewById(R.id.id_videoview);
        mVideoView.setVideoPlayerType(ISnailPlayer.PlayerType.PLAYER_TYPE_SNAIL_VR);
        mVideoView.setPlayFov(mFov);
        mVideoView.setProjectionType(mProjectionType);
        mVideoView.setNavigationmode(mNavigationMode);
        mVideoView.setVideoSpliceFormat(mVideoSpliceFormat);
        mVideoView.setScale(SCALE_10);
        mVideoView.setOnStatListener(new ISnailPlayerStateChangeNotification() {
            @Override
            public void notify(ISnailPlayer player, ISnailPlayer.State state) {
                if (state == ISnailPlayer.State.PLAYER_STARTED) {
                    mVideoView.start();
                    mImageView_PlayPause
                            .setBackgroundResource(R.drawable.btn_selector_player_pause_big);
                    mBufferingView.setVisibility(View.GONE);
                    mIsPrepared = true;
                    mDuration = mVideoView.getDuration();

                    if (!isRestart) {
                        mVideoView.seekTo(mVideoView.getDuration() * mVideoPosition / 100);
                    }

                    if (mDuration == 0) {
                        mIsLive = true;
                        mSeekBar.setEnabled(false);
                    } else {
                        mIsLive = false;
                        mSeekBar.setEnabled(true);
                    }
                    mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);
                    Log.d(TAG, "player duration :" + mDuration);
                }
            }
        });

        mVideoView.setOnEventListener(new ISnailPlayerEventNotification() {
            @Override
            public boolean notify(ISnailPlayer mp, EventType what, int extra) {
                if (what == EventType.PLAYER_EVENT_BUFFERING) {
                    Log.i(TAG, "PLAYER_EVENT_BUFFERING");
                    mBufferingView.setVisibility(View.VISIBLE);
                } else if (what == EventType.PLAYER_EVENT_BUFFERED) {
                    Log.i(TAG, "PLAYER_EVENT_BUFFERED");
                    mBufferingView.setVisibility(View.GONE);
                } else if (what == EventType.PLAYER_EVENT_FINISHED) {
                    Log.d(TAG, "PLAYER_EVENT_FINISHED ");
                    mIsPrepared = false;
                    mBufferingView.setVisibility(View.GONE);
                }
                return true;
            }
        });

        mVideoView.setOnErrorListener(new ISnailPlayerErrorNotification() {
            @Override
            public void onError(ISnailPlayer mp, ISnailPlayer.ErrorType error, int extra) {
                if (error.equals("PLAYER_ERROR_EXIT")) {
                    LogUtil.i("11111111111" + error);
                }
            }
        });


        /************************网络监听*******************************************************************/
        mNetReceiver = SnailNetReceiver.getInstance();
        mNetChangedListener = new NetStateChangedListener() {

            @Override
            public void onNetStateChanged(SnailNetReceiver.NetState netCode) {
                switch (netCode) {
                    case NET_2G:
                    case NET_3G:
                    case NET_4G:
                        UIUtils.showTip("当前在非wifi状态下,注意流量~>_<~");
                        break;
                    case NET_UNKNOWN:
                        UIUtils.showTip("当前在非wifi状态下,注意流量~>_<~");
                        break;
                    case NET_NO:
                        mVideoView.pause();
                        showNetErrorDialog();
                        break;
                    default:
                }
            }
        };
    }


    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/7/27
     * @description 播放器内的手势识别
     */
    private void playGesture() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mGestureDetector = new GestureDetector(this, new OnGestureListener() {
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
                Log.i(TAG, " video_width:" + video_width);
                float nCurrentX = e2.getRawX();
                float nCurrentY = e2.getRawY();

                int movePosX = (int) Math.abs(distanceX);
                int movePosY = (int) Math.abs(distanceY);
                float _percent = (nFristY - nCurrentY) / video_height;


                if (nFristX > video_width * 7 / 8) {
                    Log.i(TAG, "right");
                    onVolumeSlide(_percent);
                } else if (nFristX < video_width / 8) {
                    Log.i(TAG, "Left");
                    onBrightnessSlide(_percent);
                } else {
                    float phi = distanceX * 360 / video_width;
                    float theta = distanceY * mFov / video_height;
                    mVideoView.setTouchInfo(phi, theta);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("position", currentPersent());
        setResult(101, intent);
        super.onBackPressed();
    }

    /******************************************我是华丽的分割线**********************************************************************
     */

    //初始化播放器模式
    private void initPlayMode() {
        Intent intent = getIntent();
        mPlayUrl = intent.getStringExtra(VedioContants.PlayUrl);
        vedioode = intent.getIntExtra(VedioContants.PLEAR_MODE, 4);
        desc = intent.getStringExtra(VedioContants.Desc);
        mVid = intent.getIntExtra(VedioContants.Vid, 0);
        mVideoPosition = intent.getIntExtra(VedioContants.Position, 0);
        TextView title = (TextView) findViewById(R.id.tv_play_title);
        title.setText(desc);

        if (vedioode == VedioContants.TWO_D_VEDIO) {
            mEyesMode = SNVR_SINGLE_EYES_MODE;
            mProjectionType = SNVR_PROJ_PLANE;
        } else if (vedioode == VedioContants.ALL_VIEW_VEDIO) {
            mEyesMode = SNVR_SINGLE_EYES_MODE;
            mProjectionType = SNVR_PROJ_SPHERE;
        }
        mVideoView.setProjectionType(mProjectionType);
        mVideoView.setEyesMode(mEyesMode);
        if (playType == VedioContants.Video) {
            List<VodbyTopicBean.ResultBean.VodInfosBean> urlList = new Gson().fromJson(mPlayUrl, new TypeToken<List<VodbyTopicBean.ResultBean.VodInfosBean>>() {
            }.getType());
            for (int i = 0; i < urlList.size(); i++) {
                if (urlList.get(i).getDefinition() == 45) {
                    mVideoView.setVideoPath(urlList.get(i).getUrl());
                    return;
                } else if (urlList.get(i).getDefinition() == 47) {
                    mVideoView.setVideoPath(urlList.get(i).getUrl());
                    return;
                }
            }
        } else if (playType == VedioContants.Living) {
            mVideoView.setVideoPath(mPlayUrl);
        }

    }

    /**
     * 计时重新发送验证码
     */
    private void timekeeping() {

        recLen = 3;
        mTimer = new Timer();
        // UI thread
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        recLen--;
                        if (recLen < 0) {
                            toggleMediaControlsVisiblity();
                        }
                    }
                });
            }
        };
        //从现在起过10毫秒以后，每隔1000毫秒执行一次。
        mTimer.schedule(task, 10, 1000);    // timeTask
    }


    private void popFovSetDialog(final MenuItem item) {

        DialogUtils.showSelectFovDialog(this, new DiscreteSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                        if (fromUser) {
                            mVideoView.setPlayFov(value);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
//                            mVideoView.setPlayFov(seekBar.getValue());
                    }
                }, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int angle = (int) v.getTag();
                        mFov = angle;
                        mVideoView.setPlayFov(mFov);
                    }
                }
                , new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVideoView.setPlayFov(mFov);
                    }
                });

    }


    private void popScaleDialog(final MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(new String[]{"X0.5", "X1.0",
                        "X2.0"}, mScale,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (0 == which) {
                            item.setIcon(R.drawable.x_05);
                        } else if (1 == which) {
                            item.setIcon(R.drawable.x_10);
                        } else if (2 == which) {
                            item.setIcon(R.drawable.x_20);
                        }
                        mScale = which;
                        mVideoView.setScale(mScale);
                        uiutils.setPreferenceKeyIntValue(
                                getApplicationContext(),
                                Definition.KEY_SCALE, mScale);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel", null);
        AlertDialog myDialog = builder.create();
        myDialog.setTitle("Select Scale Mode");
        myDialog.show();

    }

    private void popSensorModeDialog(final MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(new String[]{"Sensor", "Touch",
                        "Double Sensor"}, mNavigationMode,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (SNVR_NAVIGATION_TOUCH == which) {
                            item.setIcon(R.drawable.touch);
                        } else if (SNVR_NAVIGATION_BOTH == which) {
                            item.setIcon(R.drawable.both);
                        } else if (SNVR_NAVIGATION_SENSOR == which) {
                            item.setIcon(R.drawable.sensor);
                        }
                        mNavigationMode = which;
                        mVideoView.setNavigationmode(mNavigationMode);
                        uiutils.setPreferenceKeyIntValue(
                                getApplicationContext(),
                                Definition.KEY_SENSORMODE, mNavigationMode);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel", null);
        AlertDialog myDialog = builder.create();
        myDialog.setTitle("Select Sensor Mode");
        myDialog.show();

    }

    private void popEyesModeDialog(final MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(new String[]{"single eyes",
                        "double eyes"}, mEyesMode,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (SNVR_SINGLE_EYES_MODE == which) {
                            item.setIcon(R.drawable.vn_double_eye);
                        } else if (SNVR_DOUBLE_EYES_MODE == which) {
                            item.setIcon(R.drawable.vn_double_eye_light);
                        }

                        mEyesMode = which;
                        mVideoView.setEyesMode(mEyesMode);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel", null);
        AlertDialog myDialog = builder.create();
        myDialog.setTitle("Select Eyes Mode");
        myDialog.show();

    }

    private void popProjectionTypeDialog(final MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(
                new String[]{"Plane", "Sphere", "Dome"}, mProjectionType,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (SNVR_PROJ_PLANE == which) {
                            item.setIcon(R.drawable.vn_projection_plane);
                        } else if (SNVR_PROJ_SPHERE == which) {
                            item.setIcon(R.drawable.vn_projection_sphere);
                        } else if (SNVR_PROJ_DOME == which) {
                            item.setIcon(R.drawable.vn_projection_dome);
                        }

                        mProjectionType = which;
                        Log.d(TAG, "selected projection = " + mProjectionType);
                        mVideoView.setProjectionType(mProjectionType);
                        uiutils.setPreferenceKeyIntValue(
                                getApplicationContext(),
                                Definition.KEY_PROJECTIONTYPE, mProjectionType);
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton("Cancel", null);
        AlertDialog myDialog = builder.create();
        myDialog.setTitle("Select projection type");
        myDialog.show();
    }

    private void popDisplayModeDialog(final MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(new String[]{"2D", "3D Side By Side",
                        "3D Over/Under"}, mVideoSpliceFormat,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (SNVR_VIDEO_SPLICE_FMT_2D == which) {
                            item.setIcon(R.drawable.vn_display_mono);
                        } else if (SNVR_VIDEO_SPLICE_FMT_3D_SBS == which) {
                            item.setIcon(R.drawable.vn_display_side_by_side);
                        } else if (SNVR_VIDEO_SPLICE_FMT_3D_OVU == which) {
                            item.setIcon(R.drawable.vn_display_over_under);
                        }
                        mVideoSpliceFormat = which;
                        mVideoView.setVideoSpliceFormat(mVideoSpliceFormat);
                        uiutils.setPreferenceKeyIntValue(
                                getApplicationContext(),
                                Definition.KEY_VIDEOSPLICEFORMAT,
                                mVideoSpliceFormat);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel", null);
        AlertDialog myDialog = builder.create();
        myDialog.setTitle("Select Format");
        myDialog.show();
    }

    private void showErrorDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(VideoPlayActivity.this, R.style.MyDialogStyle);
        normalDialog.setCancelable(false);
        normalDialog.setInverseBackgroundForced(true);
        normalDialog.setMessage("播放结束,谢谢观看...");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.dismiss();
                        VideoPlayActivity.this.finish();
                    }
                });
        show = normalDialog.show();
    }

    private void showNetErrorDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(VideoPlayActivity.this);
        normalDialog.setCancelable(false);
        normalDialog.setMessage("网络被拐走了...");
        normalDialog.setPositiveButton("点击重试",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetUtil.isOpenNetwork()){
                            show.dismiss();
                            mVideoView.start();
                        }else {
                            showNetErrorDialog();
                        }
                    }
                });
        show = normalDialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRestart = true;
    }

    @Override
    protected void onResume() {
        mNetReceiver.registNetBroadCast(this);
        mNetReceiver.addNetStateChangeListener(mNetChangedListener);
        if (mVideoView != null) {
            mVideoView.pause();
            mVideoView.setPlayFov(mFov);
            mVideoView.setScale(mScale);
            if (mVideoView.IsSurfaceHolderValid()) {
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        mNetReceiver.remoteNetStateChangeListener(mNetChangedListener);
        mNetReceiver.unRegistNetBroadCast(this);
        mVideoView.pause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        User user = SPUtil.getUser();
        if (user != null) {
            HttpHistory(user.getResult().getUser().getUid() + "", "" + currentPersent());
        }
        mHandler.removeMessages(SHOW_PROGRESS);
        if (mVideoView != null) {
            mVideoView.pause();
            mVideoView.stopPlayback();
            mVideoView = null;
        }
    }

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/7/27
     * @description 获取当前播放的百分比数值，用于储存历史数据
     */
    private int currentPersent() {
        int duration = mVideoView.getCurrentPosition();
        int Tduration = mVideoView.getDuration();
        int Persent;
        double i = (double) duration / Tduration;
        double v = i * 100;
        if (v < 1) {
            v = 1;
            Persent = (int) v;
        } else {
            Persent = (int) v;
        }
        LogUtil.i(Persent + "---------------");
        return Persent;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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

    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        cur_gesture_type = GESTURE_TYPE_NO;
        mOperLayout.setVisibility(View.GONE);
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
     * 保存用户历史观看数据
     */
    private void HttpHistory(String uid, String watchtime) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.History);
        requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken() + "");
        //包装请求参数
        requestParams.addBodyParameter("vid", mVid + "");//视频ID
        requestParams.addBodyParameter("uid", uid);//用户id
        requestParams.addBodyParameter("watchTime", watchtime);//用户名
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {

            }

        });
    }


    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;
        }

        // 显示
        mOperationBg.setImageResource(R.drawable.video_player_voice);
        mOperLayout.setVisibility(View.VISIBLE);
        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        // 变更进度条
        int present = index * 100 / mMaxVolume;
        Log.i(TAG, "present is:" + present);
        mOperTextView.setText(String.valueOf(present) + "%");
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;
            // 显示
        }
        mOperationBg.setImageResource(R.drawable.video_player_bright);
        mOperLayout.setVisibility(View.VISIBLE);
        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        getWindow().setAttributes(lpa);
        int present = (int) (lpa.screenBrightness * 100);
        Log.i(TAG, "present is:" + present);
        mOperTextView.setText(String.valueOf(present) + "%");

    }

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/7/27
     * @description
     */
    private void toggleMediaControlsVisiblity() {
        if (mLayoutPlayerControllerFull.getVisibility() == View.VISIBLE) {
            mLayoutPlayerControllerFull.setVisibility(View.GONE);
            mShowing = false;
            mHandler.removeMessages(SHOW_PROGRESS);
            if (mTimer != null)
                mTimer.cancel();
        } else {
            mLayoutPlayerControllerFull.setVisibility(View.VISIBLE);
            mShowing = true;
            updatePausePlay();
            mHandler.sendEmptyMessage(SHOW_PROGRESS);
            timekeeping();
        }
    }

    private static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }

    private void updatePausePlay() {
        if (mVideoView != null)
            if (mVideoView.isPlaying()) {
                mImageView_PlayPause.setBackgroundResource(R.drawable.btn_selector_player_pause_big);
            } else {
                mImageView_PlayPause.setBackgroundResource(R.drawable.btn_selector_player_play_big);
            }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            if (msg.what == SHOW_PROGRESS && mVideoView != null) {
                pos = setProgress();
                mSeekBar.setMax(mVideoView.getDuration());
                if (!mDragging && mShowing) {
                    msg = obtainMessage(SHOW_PROGRESS);
                    sendMessageDelayed(msg, 1000 - (pos % 1000));
                    updatePausePlay();
                    isBack = true;
                }
            }
        }
    };

    private long setProgress() {
        if (mVideoView == null || mDragging)
            return 0;

        int position = mVideoView.getCurrentPosition();
        int duration = mVideoView.getDuration();
        if (mSeekBar != null) {
            if (duration > 0) {
                //long pos = 1000L * position / duration;
                mSeekBar.setProgress(position);
            }
            int percent = mVideoView.getBufferPercentage();
            mSeekBar.setSecondaryProgress(percent);
        }

        mDuration = duration;

        if (mEndTime != null)
            mEndTime.setText(generateTime(mDuration));
        if (mCurrentTime != null)
            mCurrentTime.setText(generateTime(position));

        return position;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
