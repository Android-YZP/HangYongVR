package com.hy.vrfrog.main.living.livingplay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.responsebean.MessageBean;
import com.hy.vrfrog.main.living.im.TCChatEntity;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.im.TCSimpleUserInfo;
import com.hy.vrfrog.main.living.im.TimConfig;
import com.hy.vrfrog.main.living.livingplay.ui.TCBaseActivity;
import com.hy.vrfrog.main.living.livingplay.ui.TCHeartLayout;
import com.hy.vrfrog.main.living.livingplay.ui.TCInputTextMsgDialog;
import com.hy.vrfrog.main.living.livingplay.ui.TCUserAvatarListAdapter;
import com.hy.vrfrog.main.living.livingplay.utils.TCFrequeControl;
import com.hy.vrfrog.main.living.push.ui.TCChatMsgListAdapter;
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
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.dync.giftlibrary.widget.CustormAnim;
import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftModel;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSGuestLoginListener;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSUserInfo;

public class LivingPlayActivity extends TCBaseActivity implements ITXLivePlayListener {
    private static final String TAG = "--------------------";
    private TXLivePlayer mTXLivePlayer;
    private TXCloudVideoView mTXCloudVideoView;
    private TXLivePlayConfig mTXPlayConfig = new TXLivePlayConfig();
    private String mPusherNickname;
    protected String mPusherId;
    //    protected String mPlayUrl = "rtmp://10263.liveplay.myqcloud.com/live/10263_90cc6e99e089450fb5e2c5acc57574b4";
    protected String mPlayUrl = "rtmp://9250.liveplay.myqcloud.com/live/9250_e75874b6";
    private String mGroupId = "";
    private String mFileId = "";
    protected String mUserId = "";
    protected String mNickname = "";
    protected String mHeadPic = "";
    private int mUrlPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;      //根据mIsLivePlay和url判断出的播放类型，更加具体
    private boolean mPlaying = false;
    private TCInputTextMsgDialog mInputTextMsgDialog;

    private String tag = "每天都发包";
    private int mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
    private int mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
    private TXLivePlayConfig mPlayConfig;
    private EditText mEtRoomMessage;
    private Button mBtnSendMassage;
    private ListView mListViewMsg;
    private TCChatMsgListAdapter mChatMsgListAdapter;
    private ArrayList<TCChatEntity> mArrayListChatEntity = new ArrayList<>();
    protected Handler mHandler = new Handler();
    private RecyclerView mUserAvatarList;
    private TCUserAvatarListAdapter mAvatarListAdapter;
    private TCHeartLayout mHeartLayout;
    private TCFrequeControl mLikeFrequeControl;
    private Button mBtnHeartLike;
    private String mChannelId;
    private GiftControl giftControl;
    private LinearLayout llgiftparent;
    private GiftModel giftModel;
    private boolean currentStart = false;
    private Gson gson;
    private Button mSendMessage;
    private Dialog mMessageDialog;
    private EditText mMessage;
    private MessageBean messageBean;
    private String mGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_living_play);
        initRoomData();
//        initPlay();
        startPlay();
        TicInit(SPUtil.getUser());
        initData();

        //在这里停留，让列表界面卡住几百毫秒，给sdk一点预加载的时间，形成秒开的视觉效果
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TIMGroupManager.getInstance().quitGroup(mGroupID, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
        stopPlay(false);
    }

    private void initRoomData() {
        Intent intent = getIntent();//头像，谁直播的，直播房间
        mPlayUrl = intent.getStringExtra(VedioContants.LivingPlayUrl);
        mChannelId = intent.getStringExtra(VedioContants.ChannelId);
        mGroupID = intent.getStringExtra(VedioContants.GroupID);
        LogUtil.e(mGroupID + "=================");
    }


    private void initData() {
        gson = new Gson();
        initMessage();
        //初始化观众列表
        mUserAvatarList = (RecyclerView) findViewById(R.id.rv_user_avatar);
        mUserAvatarList.setVisibility(View.VISIBLE);
        mAvatarListAdapter = new TCUserAvatarListAdapter(this, mPusherId);
        mUserAvatarList.setAdapter(mAvatarListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mUserAvatarList.setLayoutManager(linearLayoutManager);
        //点赞爱心动画
        mHeartLayout = (TCHeartLayout) findViewById(R.id.heart_layout);
        mBtnHeartLike = (Button) findViewById(R.id.btn_living_like);
        mBtnHeartLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeartLayout != null) {
                    mHeartLayout.addFavor();
                }

                //点赞发送请求限制
                if (mLikeFrequeControl == null) {
                    mLikeFrequeControl = new TCFrequeControl();
                    mLikeFrequeControl.init(2, 1);//一秒内允许2次触发
                }

                if (mLikeFrequeControl.canTrigger()) {//发送IM点赞的消息
                    sendLikeMessage();
                }
            }
        });

        //初始化礼物列表
        Button mBtnSendGift = (Button) findViewById(R.id.btn_send_gift);
        llgiftparent = (LinearLayout) findViewById(R.id.ll_gift_parent);
        giftControl = new GiftControl(this);

        giftControl.setGiftLayout(false, llgiftparent, 4)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画

        mBtnSendGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageBean messageBean = new MessageBean();
                messageBean.setGiftCount(1);
                messageBean.setUserAction(VedioContants.AVIMCMD_Custom_Gift);
                messageBean.setGiftName("棒棒糖");
                messageBean.setMsg(1 + "");
                messageBean.setGiftPic("https://raw.githubusercontent.com/DyncKathline/LiveGiftLayout/master/giftlibrary/src/main/assets/p/001.png");
                messageBean.setHeadPic("https://raw.githubusercontent.com/DyncKathline/LiveGiftLayout/master/giftlibrary/src/main/assets/p/002.png");
                messageBean.setNickName("小嘉倪姬");
                messageBean.setUserId("小嘉倪姬");
                showGift(messageBean);
                sendMessage(gson.toJson(messageBean), VedioContants.AVIMCMD_Custom_Gift);
            }
        });

    }

    /**
     * 发送点赞消息
     */
    private void sendLikeMessage() {
        MessageBean messageBean = new MessageBean();
        messageBean.setUserAction(VedioContants.AVIMCMD_Custom_Like);
        sendMessage(gson.toJson(messageBean), VedioContants.AVIMCMD_Custom_Like);
    }


    /**
     * 发送消息
     */
    private void initMessage() {
        //发消息
        mSendMessage = (Button) findViewById(R.id.message_btn);
        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessageDialog();
            }
        });

        mEtRoomMessage = (EditText) findViewById(R.id.et_room_message);
        mBtnSendMassage = (Button) findViewById(R.id.btn_send_massage);
        mBtnSendMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEtRoomMessage.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    sendMessage(message, VedioContants.AVIMCMD_Custom_Text);
                }
            }
        });
    }

    /****************************发送消息***************************************/
    /**
     * 显示消息弹出框
     */
    private void showMessageDialog() {
        if (mMessageDialog == null) {
            initMessageDialog();
        }
        if (mMessage != null)
            showKeyBoard(mMessage);
        mMessageDialog.show();
    }


    /**
     * 初始化消息弹出框
     */
    private void initMessageDialog() {
        mMessageDialog = new Dialog(this, R.style.dialog_bottom_full);
        mMessageDialog.setCanceledOnTouchOutside(true);
        mMessageDialog.setCancelable(true);
        Window window = mMessageDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(this, R.layout.dialog_customize, null);
        mMessage = (EditText) view.findViewById(R.id.edit_text);
        Button sendMessage = (Button) view.findViewById(R.id.btn_send_massage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessage.getText().toString();
                sendMessage(message, VedioContants.AVIMCMD_Custom_Text);
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
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


    private void showGift(MessageBean messageBean) {
        giftModel = new GiftModel();
        giftModel.setGiftId(messageBean.getMsg())
                .setGiftName(messageBean.getGiftName())
                .setGiftCount(messageBean.getGiftCount())
                .setGiftPic("https://raw.githubusercontent.com/DyncKathline/LiveGiftLayout/master/giftlibrary/src/main/assets/p/000.png")
                .setSendUserId(messageBean.getUserId())
                .setSendUserName(messageBean.getNickName())
                .setSendUserPic(messageBean.getHeadPic())
                .setSendGiftTime(System.currentTimeMillis())
                .setCurrentStart(currentStart);
        if (currentStart) {
            giftModel.setHitCombo(1);
        }
        giftControl.loadGift(giftModel);
        Log.d("TAG", "onClick: " + giftControl.getShowingGiftLayoutCount());
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

    /**
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/2
     * @description 腾讯Im聊天
     */
    private void TicInit(User user) {
        mListViewMsg = (ListView) findViewById(R.id.im_msg_listview);
        mChatMsgListAdapter = new TCChatMsgListAdapter(this, mListViewMsg, mArrayListChatEntity);
        mListViewMsg.setAdapter(mChatMsgListAdapter);
        initTXLogin(user);
        Message();
    }


    private void initTXLogin(User user) {
        if (user != null) {
            userConfig();
            // identifier为用户名，userSig 为用户登录凭证
            TIMManager.getInstance().login(user.getResult().getToken(), user.getResult().getUsersig(), new TIMCallBack() {
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
        } else {
            TLSLoginHelper.getInstance().TLSGuestLogin(new TLSGuestLoginListener() {
                @Override
                public void OnGuestLoginSuccess(TLSUserInfo tlsUserInfo) {
                    UIUtils.showTip("游客登陸");
                    addGroup();
                }

                @Override
                public void OnGuestLoginFail(TLSErrInfo tlsErrInfo) {

                }

                @Override
                public void OnGuestLoginTimeout(TLSErrInfo tlsErrInfo) {

                }
            });
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
                                    entity.setSenderName(messageBean.getNickName());
                                    entity.setContext(messageBean.getMsg());
                                    entity.setType(TCConstants.TEXT_TYPE);
                                    notifyMsg(entity);
                                } else if (messageBean.getUserAction() == VedioContants.AVIMCMD_Custom_Gift) {
                                    showGift(messageBean);
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
                        } else if (elem.getTipsType() == TIMGroupTipsType.Quit) {

                            mAvatarListAdapter.removeItem(elem.getOpUserInfo().getIdentifier());

                            TCChatEntity entity = new TCChatEntity();
                            entity.setSenderName("通知");
                            entity.setContext(elem.getOpUserInfo().getNickName() + "退出直播");
                            entity.setType(TCConstants.MEMBER_ENTER);
                            notifyMsg(entity);
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
     * @date 创建于 2017/8/2
     * @description 加入群组
     */
    private void addGroup() {
        TIMGroupManager.getInstance().applyJoinGroup(mGroupID, "some reason", new TIMCallBack() {
            @java.lang.Override
            public void onError(int code, String desc) {
                //接口返回了错误码code和错误描述desc，可用于原因
                //错误码code列表请参见错误码表
                Log.e(tag, "disconnected");
            }

            @java.lang.Override
            public void onSuccess() {
                Log.i(tag, "join group");
                getMember();
            }
        });
    }


    /**
     * @param message
     * @version 2.0
     * @author 姚中平
     * @date 创建于 2017/8/2
     * @description 发送消息
     */
    private void sendMessage(String message, final int MessageType) {
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
                    UIUtils.hideKeyBoard(mMessage);
                    mMessageDialog.dismiss();
                    TCChatEntity entity = new TCChatEntity();
                    entity.setSenderName("我:");
                    entity.setContext(((TIMTextElem) msg.getElement(0)).getText());
                    entity.setType(TCConstants.TEXT_TYPE);
                    notifyMsg(entity);
                }

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

//    private void initPlay() {
//        //mPlayerView即step1中添加的界面view
//        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
//        //创建player对象
//        TXLivePlayer mLivePlayer = new TXLivePlayer(this);
//        //关键player对象与界面view
//        mLivePlayer.setPlayListener(this);
//        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
//        mLivePlayer.setPlayerView(mTXCloudVideoView);
//        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);//铺满全屏
//        mLivePlayer.enableHardwareDecode(false);
//        TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();
//        //自动模式
//        mPlayConfig.setAutoAdjustCacheTime(true);
//        mPlayConfig.setMinAutoAdjustCacheTime(1);
//        mPlayConfig.setMaxAutoAdjustCacheTime(5);
//        mLivePlayer.setConfig(mPlayConfig);
//        mLivePlayer.startPlay(mPlayUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);//推荐FLV
//    }

    protected void startPlay() {

        if (mTXLivePlayer == null) {
            mTXLivePlayer = new TXLivePlayer(this);
        }
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.clearLog();
        }
        //mPlayerView即step1中添加的界面view
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
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
    public void onPlayEvent(int event, Bundle param) {
        LogUtil.e(event + "+++++++++++++++++++++++");

        if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
            showErrorAndQuit(TCConstants.ERROR_MSG_NET_DISCONNECTED);
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        Log.d(TAG, "Current status: " + status.toString());
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.setLogText(status, null, 0);
        }

        if (status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) > status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT)) {
            if (mTXLivePlayer != null)
                mTXLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
        } else if (mTXLivePlayer != null)
            mTXLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
    }


}
