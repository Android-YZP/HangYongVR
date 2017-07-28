package com.jt.base.vrplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jt.base.R;
import com.jt.base.application.User;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.ForgetYzmBean;
import com.jt.base.http.responsebean.ResetPasswordBean;
import com.jt.base.http.responsebean.RoomNumberBean;
import com.jt.base.http.responsebean.VodbyTopicBean;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.SnailNetReceiver.NetStateChangedListener;
import com.jt.base.vrplayer.seekbar.DiscreteSeekBar;
import com.jt.base.vrplayer.utils.DialogUtils;
import com.snail.media.player.IMediaPlayer;
import com.snail.media.player.ISnailPlayer;
import com.snail.media.player.ISnailPlayer.EventType;
import com.snail.media.player.ISnailPlayer.ISnailPlayerErrorNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerEventNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerStateChangeNotification;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = "PlayActivity";
    private static final int HTTP_SUCCESS = 0;

    private SnailPlayerVideoView mVideoView;

    private ImageView mImageView_Back;
    private TextView mTextView_VideoUrl;

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

    // media_meta 信息展示页面
    private ImageView mImageView_MediaMeta;
    private TextView mTextView_MediaMeta;
    private String mMediaMeta;

    private ImageView mImageView_MediaInfo;
    private ImageView mImageView_ResetAngle;
    private TextView mTextView_MediaInfo;

    private TextView mErroText;

    private RelativeLayout mLayoutPlayerControllerFull;

    private SeekBar mSeekBar;
    private TextView mCurrentTime;
    private TextView mEndTime;
    private int mDuration;
    private boolean mDragging;


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
    private TextView mTvRoomNumber;
    private String mRoomId;
    private Timer mTimer;
    private TimerTask task;
    private int recLen;
    private boolean isRoomNumberOut = false;
    private int mMalu;
    private int mVid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        initPlayView();
        playControll();
        playGesture();
        LogUtil.i(mPlayUrl + "");
        initPlayMode();
        initInfo();
    }

    private void playGesture() {
        mBufferingView = (RelativeLayout) findViewById(R.id.id_mediaplay_buffering_view);
        mTextViewBufferPercent = (TextView) findViewById(R.id.tv_buffering);
        mBufferingView.setVisibility(View.GONE);
    }

    private void playControll() {
        mImageView_Back = (ImageView) findViewById(R.id.iv_play_back);
        mImageView_Back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayActivity.this.finish();
            }
        });
    }

    private void initPlayView() {
        mVideoView = (SnailPlayerVideoView) findViewById(R.id.id_videoview);
        mVideoView.setVideoPlayerType(ISnailPlayer.PlayerType.PLAYER_TYPE_SNAIL_VR);
        mVideoView.setPlayFov(mFov);
        mVideoView.setProjectionType(mProjectionType);
        mVideoView.setNavigationmode(mNavigationMode);
        mVideoView.setVideoSpliceFormat(mVideoSpliceFormat);
        mVideoView.setScale(PlayActivity.SCALE_10);
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
                    if (mDuration == 0) {
                        mIsLive = true;
                    } else {
                        mIsLive = false;
                    }
                    Log.d(TAG, "player duration :" + mDuration);
                }
            }
        });

        mVideoView.setOnEventListener(new ISnailPlayerEventNotification() {

            @Override
            public boolean notify(ISnailPlayer mp, ISnailPlayer.EventType what, int extra) {
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
                    showErrorDialog();
                }
            }
        });


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
                        // Log.i(Constants.LOG_TAG, "未知网络");
                        UIUtils.showTip("当前在非wifi状态下,注意流量~>_<~");
                        break;
                    case NET_NO:
                        // Log.i(Constants.LOG_TAG, "未知网络");
                        showNetErrorDialog();
                        break;
                    default:
                }
            }
        };
    }


    /******************************************我是华丽的分割线***********************************************************************/


    /**
     * 计时重新请求人数
     */
    private void timekeeping() {
        if (isRoomNumberOut) return;
        recLen = 10;
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
                            mTimer.cancel();
                            HttpOnLineNumber(mRoomId, 0);
                        }
                    }
                });
            }
        };
        //从现在起过10毫秒以后，每隔1000毫秒执行一次。
        mTimer.schedule(task, 10, 1000);    // timeTask
    }


    /**
     * 在线人数
     * id 房间id
     * number 0,查看在线人数，-1，退出，1，进入
     */
    private void HttpOnLineNumber(String id, int number) {
        mTvRoomNumber = (TextView) findViewById(R.id.tv_play_room_number);
        /**
         * 重置密码
         */
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.RoomOnLineNumber);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("num", number + "");//用户名
        requestParams.addBodyParameter("id", id);//验证码
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                RoomNumberBean roomNumberBean = new Gson().fromJson(result, RoomNumberBean.class);
                if (roomNumberBean.getCode() == HTTP_SUCCESS) {
                    int RoomNumber = roomNumberBean.getResult();
                    mTvRoomNumber.setText("在线人数 " + RoomNumber);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
                super.onFinished();
                timekeeping();
            }
        });
    }


    private void initInfo() {
        Intent intent = getIntent();
        mPlayUrl = intent.getStringExtra(VedioContants.PlayUrl);
        ImageView ivHead = (ImageView) findViewById(R.id.iv_room_head);
        TextView tvUserName = (TextView) findViewById(R.id.tv_room_person_name);
        String HeadImg = intent.getStringExtra(VedioContants.KEY_PLAY_HEAD);
        String UserName = intent.getStringExtra(VedioContants.KEY_PLAY_USERNAME);
        mRoomId = intent.getStringExtra(VedioContants.KEY_PLAY_ID);
        ImageOptions imageOptions = new ImageOptions.Builder().setCircular(true).build(); //淡入效果
        if (!TextUtils.isEmpty(HeadImg))//加载圆形头像
            x.image().bind(ivHead, HeadImg, imageOptions, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    UIUtils.showTip("头像加载失败");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
        tvUserName.setText(UserName);
        //获取在线人数
        HttpOnLineNumber(mRoomId, 1);
        mVideoView.setVideoPath(mPlayUrl);
    }

    //初始化播放器模式
    private void initPlayMode() {
        int vedioode = getIntent().getIntExtra(Definition.PLEAR_MODE, 4);
        mVid = getIntent().getIntExtra("vid", 0);
        if (vedioode == VedioContants.TWO_D_VEDIO) {
            mEyesMode = PlayActivity.SNVR_SINGLE_EYES_MODE;
            mProjectionType = PlayActivity.SNVR_PROJ_PLANE;
            mMalu = 45;
        } else if (vedioode == VedioContants.ALL_VIEW_VEDIO) {
            mEyesMode = PlayActivity.SNVR_SINGLE_EYES_MODE;
            mProjectionType = PlayActivity.SNVR_PROJ_SPHERE;
            mMalu = 46;
        }
        mVideoView.setProjectionType(mProjectionType);
        mVideoView.setEyesMode(mEyesMode);
    }

    private void showErrorDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(PlayActivity.this, R.style.MyDialogStyle);
        normalDialog.setCancelable(false);
        normalDialog.setInverseBackgroundForced(true);
        normalDialog.setMessage("播放结束,谢谢观看...");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.dismiss();
                        PlayActivity.this.finish();
                    }
                });
        show = normalDialog.show();
    }

    private void showNetErrorDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(PlayActivity.this, R.style.MyDialogStyle);
        normalDialog.setCancelable(false);
        normalDialog.setMessage("网络被拐走了...");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.dismiss();
                        PlayActivity.this.finish();
                    }
                });
        show = normalDialog.show();
    }



    @Override
    protected void onResume() {
        mNetReceiver.registNetBroadCast(this);
        mNetReceiver.addNetStateChangeListener(mNetChangedListener);
        if (mVideoView != null) {
            mVideoView.setPlayFov(mFov);
            mVideoView.setScale(mScale);
            if (mVideoView.IsSurfaceHolderValid()) {
                mVideoView.resetUrl();
            }
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        mNetReceiver.remoteNetStateChangeListener(mNetChangedListener);
        mNetReceiver.unRegistNetBroadCast(this);
        Log.d(TAG, "onPause()");
//		pausePostion = mVideoView.getCurrentPosition();
//		mVideoView.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
//        mNetReceiver.remoteNetStateChangeListener(mNetChangedListener);
//        mNetReceiver.unRegistNetBroadCast(this);
        Log.d(TAG, "onPause()");
        pausePostion = mVideoView.getCurrentPosition();
        mVideoView.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stop();
            mVideoView.stopPlayback();
            mVideoView = null;
        }
        //退出房间
        isRoomNumberOut = true;//退出房间,结束获取在线人数
        HttpOnLineNumber(mRoomId, -1);
        User user = SPUtil.getUser();
        if (user != null) {
            HttpHistory(user.getResult().getUser().getUid() + "", "");
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
        requestParams.addBodyParameter("vid", mRoomId + "");//视频ID
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
