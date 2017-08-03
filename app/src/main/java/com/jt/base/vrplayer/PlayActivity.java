package com.jt.base.vrplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.application.User;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.RoomNumberBean;
import com.jt.base.timroomchat.TimConfig;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.VedioContants;
import com.jt.base.vrplayer.SnailNetReceiver.NetStateChangedListener;
import com.snail.media.player.ISnailPlayer;
import com.snail.media.player.ISnailPlayer.EventType;
import com.snail.media.player.ISnailPlayer.ISnailPlayerErrorNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerEventNotification;
import com.snail.media.player.ISnailPlayer.ISnailPlayerStateChangeNotification;
import com.tencent.imcore.Msg;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
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
import com.tencent.imsdk.ext.sns.TIMFriendGroup;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

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
        initPlayMode();
        initInfo();
//        TicInit();
    }

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/2
     * @description 腾讯Im聊天
     */
    private void TicInit() {
        userConfig();
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(TimConfig.Identifier2, TimConfig.UserSign2, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.i(tag, "login failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i(tag, "login succ");
                addGroup();
            }
        });


        //接受消息
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            //消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {

                for (int j = 0; j < list.size(); j++) {
                    TIMMessage msg = list.get(j);

                    for (int i = 0; i < msg.getElementCount(); ++i) {
                        TIMElem elem = msg.getElement(i);

                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();
                        Log.e(tag, "elem type: " + elemType.name());
                        if (elemType == TIMElemType.Text) {
                            String text = ((TIMTextElem) elem).getText();
                            UIUtils.showTip(text);
                        }
                    }
                }
                return false;
            }
        });
    }


    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/2
     * @description 加入群组
     */
    private void addGroup() {
        TIMGroupManager.getInstance().applyJoinGroup(TimConfig.GroupID, "some reason", new TIMCallBack() {
            @java.lang.Override
            public void onError(int code, String desc) {
                //接口返回了错误码code和错误描述desc，可用于原因
                //错误码code列表请参见错误码表
                Log.e(tag, "disconnected");
            }

            @java.lang.Override
            public void onSuccess() {
                Log.i(tag, "join group");
            }
        });


    }

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/2
     * @description 发送消息
     */
    private void sendMessage() {
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,      //会话类型：群组
                TimConfig.GroupID);//群组Id

        //构造一条消息
        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText("这是一条测试消息。。。。。。。");

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d(tag, "addElement failed");
            return;
        }

        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code含义请参见错误码表
                Log.d(tag, "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e(tag, "SendMsg ok");
            }
        });


    }

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/2
     * @description 用户配置
     */
    private void userConfig() {

        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i(tag, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新userSig重新登录SDK
                        Log.i(tag, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(tag, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(tag, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(tag, "onWifiNeedAuth");
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.i(tag, "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(tag, "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.i(tag, "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

//消息扩展用户配置
        userConfig = new TIMUserConfigMsgExt(userConfig)
                //禁用消息存储
                .enableStorage(false)
                //开启消息已读回执
                .enableReadReceipt(true);

//资料关系链扩展用户配置
        userConfig = new TIMUserConfigSnsExt(userConfig)
                //开启资料关系链本地存储
                .enableFriendshipStorage(true)
                //设置关系链变更事件监听器
                .setFriendshipProxyListener(new TIMFriendshipProxyListener() {
                    @Override
                    public void OnAddFriends(List<TIMUserProfile> users) {
                        Log.i(tag, "OnAddFriends");
                    }

                    @Override
                    public void OnDelFriends(List<String> identifiers) {
                        Log.i(tag, "OnDelFriends");
                    }

                    @Override
                    public void OnFriendProfileUpdate(List<TIMUserProfile> profiles) {
                        Log.i(tag, "OnFriendProfileUpdate");
                    }

                    @Override
                    public void OnAddFriendReqs(List<TIMSNSChangeInfo> reqs) {
                        Log.i(tag, "OnAddFriendReqs");
                    }


                });

//群组管理扩展用户配置
        userConfig = new TIMUserConfigGroupExt(userConfig)
                //开启群组资料本地存储
                .enableGroupStorage(true)
                //设置群组资料变更事件监听器
                .setGroupAssistantListener(new TIMGroupAssistantListener() {
                    @Override
                    public void onMemberJoin(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberJoin");
                    }

                    @Override
                    public void onMemberQuit(String groupId, List<String> members) {
                        Log.i(tag, "onMemberQuit");
                    }

                    @Override
                    public void onMemberUpdate(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberUpdate");
                    }

                    @Override
                    public void onGroupAdd(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupAdd");
                    }

                    @Override
                    public void onGroupDelete(String groupId) {
                        Log.i(tag, "onGroupDelete");
                    }

                    @Override
                    public void onGroupUpdate(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupUpdate");
                    }
                });

//将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);

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

//                sendMessage();
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

                float nFristX = e1.getX();
                float nFristY = e1.getY();

                // Log.i(TAG, " nFristX:" + nFristX + " nFristY:" + nFristY);

                int video_width = mVideoView.getWidth();
                int video_height = mVideoView.getHeight();
                Log.i(TAG, " video_width:" + video_width);

                float nCurrentX = e2.getRawX();
                float nCurrentY = e2.getRawY();

                int movePosX = (int) Math.abs(distanceX);
                int movePosY = (int) Math.abs(distanceY);

                // Log.i(TAG, "movePosX:" + movePosX + " movePosY:" + movePosY);

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
