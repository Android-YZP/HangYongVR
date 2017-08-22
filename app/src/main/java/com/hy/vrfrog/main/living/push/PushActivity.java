package com.hy.vrfrog.main.living.push;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.MessageBean;
import com.hy.vrfrog.http.responsebean.RoomNumberBean;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.main.home.activitys.VideoListActivity;
import com.hy.vrfrog.main.home.adapters.VideoListAdapter;
import com.hy.vrfrog.main.living.im.TCChatEntity;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.im.TCSimpleUserInfo;
import com.hy.vrfrog.main.living.livingplay.ui.TCHeartLayout;
import com.hy.vrfrog.main.living.livingplay.ui.TCUserAvatarListAdapter;
import com.hy.vrfrog.main.living.push.ui.BeautyDialogFragment;
import com.hy.vrfrog.main.living.push.ui.TCAudioControl;
import com.hy.vrfrog.main.living.push.ui.TCChatMsgListAdapter;
import com.hy.vrfrog.main.living.push.utils.TCUtils;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
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
import com.tencent.imsdk.TIMGroupTipsType;
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
import com.tencent.imsdk.ext.group.TIMGroupManagerExt;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.dync.giftlibrary.widget.CustormAnim;
import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftModel;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

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
    String tag = "犯得上發射點發";
    private ListView mListViewMsg;
    private TCChatMsgListAdapter mChatMsgListAdapter;
    private ArrayList<TCChatEntity> mArrayListChatEntity = new ArrayList<>();
    protected Handler mHandler = new Handler();
    private User user;
    private String imRoomId;
    private RecyclerView mUserAvatarList;
    private TCUserAvatarListAdapter mAvatarListAdapter;
    protected String mPusherId;
    private Button mSendMessage;
    private Dialog mShareDialog;
    private EditText mMessage;
    private LinearLayout llgiftparent;
    private GiftControl giftControl;
    private GiftModel giftModel;
    private boolean currentStart = false;
    private Gson gson;
    private TextView mBroadcastTime;
    private Timer mBroadcastTimer;
    private BroadcastTimerTask mBroadcastTimerTask;
    private long mSecond = 0;
    private TCHeartLayout mHeartLayout;
    private String mGroupID;
    private TextView mTvRoomNum;
    private int mRoomNum;
    private TextView mTvChannelName;
    private String mChannelName;
    private LinearLayout mLlRootOutRoom;
    private TextView mLlOutRoomTime;
    private TextView mLlOutRoomPersons;
    private TextView mLlOutRoomMoney;
    private TextView mLlOutRoomLikeNum;
    private Button mLlOutRoomOut;
    private ImageView mLlOutRoomOutBg;
    private int mFavorNumber;
    private String mRoomImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(TCConstants.USER_ID);
        mTitle = intent.getStringExtra(TCConstants.ROOM_TITLE);
        mCoverPicUrl = intent.getStringExtra(TCConstants.COVER_PIC);
        mHeadPicUrl = intent.getStringExtra(TCConstants.USER_HEADPIC);
        mNickName = intent.getStringExtra(TCConstants.USER_NICK);
        mLocation = intent.getStringExtra(TCConstants.USER_LOC);
        mListViewMsg = (ListView) findViewById(R.id.im_msg_listview);
        mPushUrl = intent.getStringExtra(VedioContants.LivingPushUrl);
        imRoomId = intent.getStringExtra(VedioContants.ChannelId);
        mChannelName = intent.getStringExtra(VedioContants.ChannelName);
        mGroupID = intent.getStringExtra(VedioContants.GroupID);
        mRoomImg = intent.getStringExtra(VedioContants.RoomImg);
        LogUtil.e(mGroupID + "=================");
        initView();
        initData();

        TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    final PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (mTXLivePusher != null) mTXLivePusher.pausePusher();
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (mTXLivePusher != null) mTXLivePusher.pausePusher();
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mTXLivePusher != null) mTXLivePusher.resumePusher();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mTXCloudVideoView.onResume();


        if (mTXLivePusher != null) {
            mTXLivePusher.resumePusher();
        }

        if (mTXLivePusher != null) {
            mTXLivePusher.resumeBGM();
        }
        startPublish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mTXCloudVideoView.onPause();
        if (mTXLivePusher != null) {
            mTXLivePusher.pauseBGM();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mTXLivePusher != null) {
//            mTXLivePusher.stopCameraPreview(false);
            mTXLivePusher.pausePusher();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        outRoom();
    }

    public void outRoom() {
        stopPublish();
        stopRecordAnimation();
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
        //销毁动画
        if (giftControl != null) {
            giftControl.cleanAll();
        }
    }


    private void initView() {
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        mFlashView = (Button) findViewById(R.id.flash_btn);
        mBtnAudioEffect = (Button) findViewById(R.id.btn_audio_effect);
        mBtnAudioClose = (Button) findViewById(R.id.btn_audio_close);
        mBtnAudioCtrl = (Button) findViewById(R.id.btn_audio_ctrl);
        mAudioCtrl = (TCAudioControl) findViewById(R.id.layoutAudioControlContainer);
        mAudioPluginLayout = (LinearLayout) findViewById(R.id.audio_plugin);
        mTvChannelName = (TextView) findViewById(R.id.tv_play_channelName);
        //点赞爱心动画
        mHeartLayout = (TCHeartLayout) findViewById(R.id.heart_layout);
        mTvRoomNum = (TextView) findViewById(R.id.tv_room_num);
    }

    private void initData() {
        initOutRoom();

        giftControl = new GiftControl(UIUtils.getContext());
        gson = new Gson();
        mChatMsgListAdapter = new TCChatMsgListAdapter(this, mListViewMsg, mArrayListChatEntity);
        mListViewMsg.setAdapter(mChatMsgListAdapter);
        mTvChannelName.setText(mChannelName);

        mBeautyDialogFragment = new BeautyDialogFragment();
        mBeautyDialogFragment.setBeautyParamsListner(mBeautyParams, this);
        //AudioControl
        mAudioCtrl.setPluginLayout(mAudioPluginLayout);
        initTCIM(SPUtil.getUser());

        //初始化观众列表
        mUserAvatarList = (RecyclerView) findViewById(R.id.rv_user_avatar);
        mUserAvatarList.setVisibility(View.VISIBLE);
        mAvatarListAdapter = new TCUserAvatarListAdapter(this, mPusherId);
        mUserAvatarList.setAdapter(mAvatarListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mUserAvatarList.setLayoutManager(linearLayoutManager);

        //计时
        mBroadcastTime = (TextView) findViewById(R.id.tv_broadcasting_time);
        mBroadcastTime.setText(String.format(Locale.US, "%s", "00:00:00"));
        startRecordAnimation();


        //初始化礼物列表
        llgiftparent = (LinearLayout) findViewById(R.id.ll_gift_parent);
        giftControl.setGiftLayout(false, llgiftparent, 4)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画

        //发消息
        mSendMessage = (Button) findViewById(R.id.message_btn);
    }


    /**
     * 初始化退出房间
     */
    private void initOutRoom() {
        mLlRootOutRoom = (LinearLayout) findViewById(R.id.ll_root_out_room);
        mLlOutRoomTime = (TextView) findViewById(R.id.tv_out_room_time);
        mLlOutRoomPersons = (TextView) findViewById(R.id.tv_out_room_persons);
        mLlOutRoomMoney = (TextView) findViewById(R.id.tv_out_room_money);
        mLlOutRoomLikeNum = (TextView) findViewById(R.id.tv_out_room_like_num);
        mLlOutRoomOut = (Button) findViewById(R.id.btn_out_room_out);
        mLlOutRoomOutBg = (ImageView) findViewById(R.id.iv_room_img);
        mLlOutRoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void outRoom(String money) {
        mLlRootOutRoom.setVisibility(View.VISIBLE);
        mLlOutRoomOutBg.setVisibility(View.VISIBLE);
        mLlOutRoomTime.setText(mBroadcastTime.getText().toString());
        mLlOutRoomPersons.setText(mRoomNum + "");
        mLlOutRoomLikeNum.setText(mFavorNumber + "");
        mLlOutRoomMoney.setText(money);
        Glide.with(UIUtils.getContext()).load(mRoomImg).asBitmap().into(mLlOutRoomOutBg);
        outRoom();
    }


    /**
     * 获取话题
     */
    private void HttpMoney() {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.getRoomMoney);
        requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken());
        //包装请求参数
        requestParams.addBodyParameter("id", SPUtil.getUser().getResult().getUser().getUid() + "");
        requestParams.addBodyParameter("uid", SPUtil.getUser().getResult().getUser().getUid() + "");

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(SPUtil.getUser().getResult().getUser().getUid() + "..." + SPUtil.getUser().getResult().getUser().getToken() + result + "-----------------------------");
                RoomNumberBean roomNumberBean = new Gson().fromJson(result, RoomNumberBean.class);
                if (roomNumberBean.getCode() == 0) {
                    outRoom(roomNumberBean.getResult() + "");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

        });
    }

    @Override
    public void onBackPressed() {
        showComfirmDialog(TCConstants.TIPS_MSG_STOP_PUSH, false);
    }

    /**
     * 开启红点与计时动画
     */
    private void startRecordAnimation() {

        //直播时间
        if (mBroadcastTimer == null) {
            mBroadcastTimer = new Timer(true);
            mBroadcastTimerTask = new BroadcastTimerTask();
            mBroadcastTimer.schedule(mBroadcastTimerTask, 1000, 1000);
        }
    }

    /**
     * 关闭红点与计时动画
     */
    private void stopRecordAnimation() {
        //直播时间
        if (null != mBroadcastTimer) {
            mBroadcastTimerTask.cancel();
        }
    }

    /**
     * 记时器
     */
    private class BroadcastTimerTask extends TimerTask {
        public void run() {
            //Log.i(TAG, "timeTask ");
            ++mSecond;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    if (!mTCSwipeAnimationController.isMoving())
                    mBroadcastTime.setText(TCUtils.formattedTime(mSecond));
                }
            });
//            if (MySelfInfo.getInstance().getIdStatus() == TCConstants.HOST)
//                mHandler.sendEmptyMessage(UPDAT_WALL_TIME_TIMER_TASK);
        }
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
            mTXPushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_HARDWARE);
            mTXLivePusher.setConfig(mTXPushConfig);
        }

        mAudioCtrl.setPusher(mTXLivePusher);
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.setVisibility(View.VISIBLE);
            mTXCloudVideoView.clearLog();
        }
//        mBeautySeekBar.setProgress(100);

        //设置视频质量：高清
        mTXLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION);
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

    private void notifyMsg(final TCChatEntity entity) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                if(entity.getType() == TCConstants.PRAISE) {
//                    if(mArrayListChatEntity.contains(entity))
//                        return;
//                }

                if (mArrayListChatEntity.size() > 1000) {
                    while (mArrayListChatEntity.size() > 900) {
                        mArrayListChatEntity.remove(0);
                    }
                }

                mArrayListChatEntity.add(entity);
                mChatMsgListAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initTCIM(User user) {
        // identifier为用户名，userSig 为用户登录凭证
        initTXLogin(user);
        Message();
    }


    private void initTXLogin(User user) {
        if (user != null) {
            userConfig();
            TIMManager.getInstance().login(user.getResult().getToken(), user.getResult().getUsersig(), new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    //错误码code和错误描述desc，可用于定位请求失败原因
                    Log.i(tag, "login failed. code: " + code + " errmsg: " + desc);
                }

                @Override
                public void onSuccess() {
                    Log.i(tag, "login succ");
//                    createGroupParam();
                    addGroup();
                }
            });
        }
    }

    private void Message() {
        //接受消息
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            //消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                for (int j = 0; j < list.size(); j++) {
                    TIMMessage msg = list.get(j);
                    for (int i = 0; i < msg.getElementCount(); ++i) {
                        TIMElem elem = msg.getElement(i);
                        MessageBean messageBean;
                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();
                        if (elemType == TIMElemType.Text) {
                            //获取文本信息
                            String text = ((TIMTextElem) elem).getText();

                            try {
                                messageBean = gson.fromJson(text, MessageBean.class);
                            } catch (Exception e) {
                                return false;
                            }

                            if (messageBean != null) {
                                if (messageBean.getUserAction() == VedioContants.AVIMCMD_Custom_Text) {
                                    TCChatEntity entity = new TCChatEntity();
                                    entity.setSenderName(msg.getSenderProfile().getNickName());
                                    entity.setContext(messageBean.getMsg());
                                    entity.setType(TCConstants.TEXT_TYPE);
                                    notifyMsg(entity);

                                } else if (messageBean.getUserAction() == VedioContants.AVIMCMD_Custom_Gift) {
                                    showGift(messageBean, msg.getSenderProfile().getNickName());

                                } else if (messageBean.getUserAction() == VedioContants.AVIMCMD_Custom_Like) {
                                    mHeartLayout.addFavor();
                                    mFavorNumber++;
                                }
                            }


                        } else if (elemType == TIMElemType.GroupSystem) {
                            String groupName = ((TIMGroupSystemElem) elem).getOpReason();

                        } else if (elemType == TIMElemType.GroupTips) {
                            String groupName = ((TIMGroupTipsElem) elem).getGroupName();

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
                        if (elem.getTipsType() == TIMGroupTipsType.Join) {

                            TCSimpleUserInfo tcSimpleUserInfo = new TCSimpleUserInfo(elem.getOpUserInfo().getIdentifier()
                                    , elem.getOpUserInfo().getNickName(), elem.getOpUserInfo().getFaceUrl());
                            mAvatarListAdapter.addItem(tcSimpleUserInfo);

                            TCChatEntity entity = new TCChatEntity();
                            entity.setSenderName("通知");
                            entity.setContext(elem.getOpUserInfo().getNickName() + "加入直播");
                            entity.setType(TCConstants.MEMBER_ENTER);
                            notifyMsg(entity);

                            //人数增加
                            ++mRoomNum;
                            mTvRoomNum.setText(mRoomNum + "人");

                        } else if (elem.getTipsType() == TIMGroupTipsType.Quit) {
                            mAvatarListAdapter.removeItem(elem.getOpUserInfo().getIdentifier());

                            TCChatEntity entity = new TCChatEntity();
                            entity.setSenderName("通知");
                            entity.setContext(elem.getOpUserInfo().getNickName() + "退出直播");
                            entity.setType(TCConstants.MEMBER_ENTER);
                            notifyMsg(entity);

                            //人数减少
                            --mRoomNum;
                            if (mRoomNum <= 0) mRoomNum = 0;
                            mTvRoomNum.setText(mRoomNum + "人");
                        }
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

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/11
     * @description 创建群组
     */
    private void createGroupParam() {
        //创建公开群，且不自定义群ID
        TIMGroupManager.CreateGroupParam param = new TIMGroupManager.CreateGroupParam("AVChatRoom", "group");
        //指定群简介
        param.setIntroduction("hello world");
        //指定群公告
        param.setNotification("welcome to our group");

        //创建群组
        TIMGroupManager.getInstance().createGroup(param, new TIMValueCallBack<String>() {
            @Override
            public void onError(int code, String desc) {
                Log.i(tag, "create group failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(String s) {
                Log.i(tag, "create group succ, groupId:" + s);
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
        TIMGroupManager.getInstance().applyJoinGroup(mGroupID, "some reason", new TIMCallBack() {
            @java.lang.Override
            public void onError(int code, String desc) {
                //接口返回了错误码code和错误描述desc，可用于原因
                //错误码code列表请参见错误码表
                Log.e(tag, "disconnected" + code);
            }

            @java.lang.Override
            public void onSuccess() {
                Log.i(tag, "join group");
                getMember();
            }
        });
    }


    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/15
     * @description 获取群里有多少人, 初始化头像
     */
    public void getMember() {
        TIMGroupManagerExt.getInstance().getGroupMembers(mGroupID, new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                mRoomNum = timGroupMemberInfos.size() + mRoomNum;
                mTvRoomNum.setText(mRoomNum + "人");

                for (int i = 0; i < timGroupMemberInfos.size(); i++) {
                    Log.e(tag, "user: " + timGroupMemberInfos.get(i).getUser() +
                            "join time: " + timGroupMemberInfos.get(i).getJoinTime() +
                            "role: " + timGroupMemberInfos.get(i).getRole());
                    TCSimpleUserInfo tcSimpleUserInfo = new TCSimpleUserInfo(timGroupMemberInfos.get(i).getJoinTime() + ""
                            , timGroupMemberInfos.get(i).getUser(), HttpURL.NOR_IV_HOST);

                    mAvatarListAdapter.addItem(tcSimpleUserInfo);
                }


            }
        });

    }


    private void showGift(MessageBean messageBean, String nickName) {
        //setSendUserPic为自己界面显示的礼物//mGifturl礼物界面显示的礼物
        giftModel = new GiftModel();
        giftModel.setGiftId(messageBean.getUserId())
                .setGiftName(messageBean.getGiftName())
                .setGiftCount(messageBean.getGiftCount())
                .setGiftPic(messageBean.getGiftPic())
                .setSendUserPic(messageBean.getGiftPic())
                .setSendUserId(nickName)
                .setSendUserName(nickName + "")
                .setSendGiftTime(System.currentTimeMillis())
                .setCurrentStart(currentStart);
        if (currentStart) {
            giftModel.setHitCombo(1);
        }
        giftControl.loadGift(giftModel);
        Log.d("TAG", "onClick: " + giftControl.getShowingGiftLayoutCount());
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
            case R.id.message_btn:
                showMessageDialog();

                break;
            default:
                break;
        }
    }


    /****************************发送消息***************************************/
    /**
     * 显示消息弹出框
     */
    private void showMessageDialog() {
        if (mShareDialog == null) {
            initMessageDialog();
        }
        if (mMessage != null)
            showKeyBoard(mMessage);
        mShareDialog.show();
    }


    /**
     * 初始化消息弹出框
     */
    private void initMessageDialog() {
        mShareDialog = new Dialog(this, R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setCancelable(true);
        Window window = mShareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(this, R.layout.dialog_customize, null);
        mMessage = (EditText) view.findViewById(R.id.edit_text);
        Button sendMessage = (Button) view.findViewById(R.id.btn_send_massage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessage.getText().toString();
                MessageBean messageBean = new MessageBean();
                messageBean.setUserAction(VedioContants.AVIMCMD_Custom_Text);
                messageBean.setMsg(message);
                sendMessage(gson.toJson(messageBean), VedioContants.AVIMCMD_Custom_Text);
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    /**
     * @param message
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/2
     * @description 发送消息
     */
    private void sendMessage(final String message, final int MessageType) {
        if (TextUtils.isEmpty(message)) {
            UIUtils.showTip("发送内容不能为空");
            return;
        }

        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,      //会话类型：群组
                mGroupID);//群组Id

        //构造一条消息
        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(message);

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
                //消息回显
                if (MessageType == VedioContants.AVIMCMD_Custom_Text) {
                    if (mMessage != null) mMessage.setText("");

                    TCChatEntity entity = new TCChatEntity();
                    entity.setSenderName("我:");
                    entity.setContext(gson.fromJson(message, MessageBean.class).getMsg());
                    entity.setType(TCConstants.TEXT_TYPE);
                    notifyMsg(entity);
                }

            }
        });
    }


    private void showKeyBoard(final EditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);
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
            mTXPushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_AUTO);
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
                    HttpMoney();
                    MessageBean messageBean = new MessageBean();
                    messageBean.setUserAction(VedioContants.AVIMCMD_Custom_Exit);
                    sendMessage(gson.toJson(messageBean), VedioContants.AVIMCMD_Custom_Exit);

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
