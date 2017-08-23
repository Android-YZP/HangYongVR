package com.hy.vrfrog.vrplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.RoomNumberBean;
import com.hy.vrfrog.main.living.im.TCChatEntity;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.im.TimConfig;
import com.hy.vrfrog.main.living.livingplay.LivingPlayActivity;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.vrplayer.SnailNetReceiver.NetStateChangedListener;
import com.snail.media.player.ISnailPlayer;
import com.snail.media.player.ISnailPlayer.EventType;
import com.snail.media.player.ISnailPlayer.ISnailPlayerErrorNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerEventNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerStateChangeNotification;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupSystemElem;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = "PlayActivity";
    private static final int HTTP_SUCCESS = 0;

    private SnailPlayerVideoView mVideoView;

    private ImageView mImageView_Back;
    private RelativeLayout mBufferingView;
    private TextView mTextViewBufferPercent;
    private String mPlayUrl = "";

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
    String tag = "===============";
    private GestureDetector mGestureDetector;

    private RecyclerView mRvChatRomm;
    private ArrayList<TCChatEntity> mArrayListChatEntity = new ArrayList<>();
    private ListView mListViewMsg;
    protected Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        Boolean isRoomNoData = getIntent().getBooleanExtra(VedioContants.RoomNoData, false);
        if (isRoomNoData) {
            showComfirmDialog("主播正在赶来的路上", true);
        }
            initPlayView();
            playControll();
            playGesture();
            initPlayMode();
            initInfo();
    }

    private void playGesture() {
        mBufferingView = (RelativeLayout) findViewById(R.id.id_mediaplay_buffering_view);
        mTextViewBufferPercent = (TextView) findViewById(R.id.tv_buffering);
        mBufferingView.setVisibility(View.VISIBLE);
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
                    mBufferingView.setVisibility(View.GONE);
                    mIsPrepared = true;
                    mDuration = mVideoView.getDuration();
                    if (mDuration == 0) {
                        mIsLive = true;
                    } else {
                        mIsLive = false;
                    }
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
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                Log.i(TAG, "onScroll");

//                float nFristX = e1.getX();
//                float nFristY = e1.getY();

                // Log.i(TAG, " nFristX:" + nFristX + " nFristY:" + nFristY);

                int video_width = mVideoView.getWidth();
                int video_height = mVideoView.getHeight();
                Log.i(TAG, " video_width:" + video_width);

//                float nCurrentX = e2.getRawX();
//                float nCurrentY = e2.getRawY();
//
//                int movePosX = (int) Math.abs(distanceX);
//                int movePosY = (int) Math.abs(distanceY);
//
//                // Log.i(TAG, "movePosX:" + movePosX + " movePosY:" + movePosY);

                if (mNavigationMode != SNVR_NAVIGATION_SENSOR) {
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
                UIUtils.showTip("在线人数--服务端连接失败");
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
        if (user != null && user.getResult() != null && user.getResult().getUser() != null) {
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

    /**
     * 显示确认消息
     *
     * @param msg     消息内容
     * @param isError true错误消息（必须退出） false提示消息（可选择是否退出）
     */
    public void showComfirmDialog(String msg, Boolean isError) {

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.ConfirmDialogStyle);
        builder.setCancelable(true);
        builder.setTitle(msg);

        if (!isError) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
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
                    finish();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
//        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
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
