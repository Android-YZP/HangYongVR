package com.hy.vrfrog.main.living.livingplay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GiftBean;
import com.hy.vrfrog.http.responsebean.GiftsBean;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.MessageBean;
import com.hy.vrfrog.http.responsebean.VideoTypeBean;
import com.hy.vrfrog.main.adapter.HomeAdapter;
import com.hy.vrfrog.main.living.im.TCChatEntity;
import com.hy.vrfrog.main.living.im.TCConstants;
import com.hy.vrfrog.main.living.im.TCSimpleUserInfo;
import com.hy.vrfrog.main.living.im.TimConfig;
import com.hy.vrfrog.main.living.livingplay.ui.GiftDialogFrament;
import com.hy.vrfrog.main.living.livingplay.ui.TCBaseActivity;
import com.hy.vrfrog.main.living.livingplay.ui.TCHeartLayout;
import com.hy.vrfrog.main.living.livingplay.ui.TCInputTextMsgDialog;
import com.hy.vrfrog.main.living.livingplay.ui.TCUserAvatarListAdapter;
import com.hy.vrfrog.main.living.livingplay.utils.TCFrequeControl;
import com.hy.vrfrog.main.living.push.ui.FragmentGiftDialog;
import com.hy.vrfrog.main.living.push.ui.Gift;
import com.hy.vrfrog.main.living.push.ui.TCChatMsgListAdapter;
import com.hy.vrfrog.main.personal.LoginActivity;
import com.hy.vrfrog.utils.BasePreferences;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;
import com.hy.vrfrog.vrplayer.LivePlayActivity;
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

import org.dync.giftlibrary.util.GiftPanelControl;
import org.dync.giftlibrary.widget.CustormAnim;
import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftModel;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSGuestLoginListener;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSUserInfo;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    public static int mGiftGroup;
    public static GiftBean.ResultBean SendGift;
    private LinearLayout mGiftLayout;
    private LinearLayout ll_portrait;
    private LinearLayout ll_landscape;
    private TextView tvGiftNum;
    private ImageView btnGift;
    private RecyclerView mRecyclerView;
    private ViewPager mViewpager;
    private LinearLayout mDotsLayout;
    private String mGifturl;
    private String mGiftName;
    private String mGiftPrice;
    private String mGid;
    private RelativeLayout mRootView;
    private String mChannelName;
    private TextView mHannelName;
    private TextView mTvChannelName;
    private TextView mTvRoomNum;
    private int mRoomNum;
    private ImageView mIvHead;
    private String mHeadFace;
    private ImageView mIvRoomImg;
    private String mRoomImg = "";
    private Button mBtnClose;
    private int mVid;
    private int mYid;
    private int recLen = 3;
    private Timer mTimer;
    private TimerTask task;
    private int giftTimes;
    private int giftTimesLast;
    private int totleNumber;
    private TextView mTvMoneyNum;
    private BasePreferences basePreferences;
    private boolean isSendLike = false;

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

        outRoom();
    }

    @Override
    public void onBackPressed() {
        showComfirmDialog(TCConstants.TIPS_MSG_STOP_PUSH, false);
    }

    public void outRoom() {
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
        //销毁动画
        if (giftControl != null) {
            giftControl.cleanAll();
        }
    }

    private void initRoomData() {
        Intent intent = getIntent();//头像，谁直播的，直播房间
        mPlayUrl = intent.getStringExtra(VedioContants.LivingPlayUrl);
        mChannelId = intent.getStringExtra(VedioContants.ChannelId);
        mGroupID = intent.getStringExtra(VedioContants.GroupID);
        mHeadFace = intent.getStringExtra(VedioContants.HeadFace);
        mChannelName = intent.getStringExtra(VedioContants.ChannelName);
        mRoomImg = intent.getStringExtra(VedioContants.RoomImg);
        mGiftGroup = intent.getIntExtra(VedioContants.GiftGroup, 0);
        mVid = intent.getIntExtra(VedioContants.Vid, 0);
        mYid = intent.getIntExtra(VedioContants.Yid, 0);
        Boolean isRoomNoData = intent.getBooleanExtra(VedioContants.RoomNoData, false);
        if (isRoomNoData) {
            showComfirmDialog("主播正在赶来的路上", true);
        }
        LogUtil.e(mGiftGroup + "=================");
    }

    /***************************************礼物*************************************************************/
    private void initData() {
        gson = new Gson();
        initMessage();
        initGift();

        //初始化房间信息
        mTvRoomNum = (TextView) findViewById(R.id.tv_room_num);
        mTvChannelName = (TextView) findViewById(R.id.tv_play_channelName);
        mIvHead = (ImageView) findViewById(R.id.iv_room_head);
        mBtnClose = (Button) findViewById(R.id.btn_close);
        mIvRoomImg = (ImageView) findViewById(R.id.iv_living_room_img);
        mTvChannelName.setText(mChannelName);
        Glide.with(this).load(mHeadFace).asBitmap().into(mIvHead);
        Glide.with(this).load(mRoomImg).asBitmap().into(mIvRoomImg);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComfirmDialog(TCConstants.TIPS_MSG_STOP_PUSH, false);
            }
        });

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
                if (SPUtil.getUser() != null) {
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
                        if (isSendLike) {
                            TCChatEntity entity = new TCChatEntity();
                            entity.setSenderName("我");
                            entity.setContext("点了一个赞");
                            entity.setType(TCConstants.TEXT_TYPE);
                            notifyMsg(entity);
                            isSendLike = true;
                        }
                    }
                } else {
                    UIUtils.showTip("请先登录");
                }


            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
//            Log.e(TAG, "onConfigurationChanged: " + "横屏");
            onConfigurationLandScape();

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Log.e(TAG, "onConfigurationChanged: " + "竖屏");
            onConfigurationPortrait();
        }
    }

    private void onConfigurationPortrait() {
        ll_portrait.setVisibility(View.VISIBLE);
        ll_landscape.setVisibility(View.GONE);
    }

    private void onConfigurationLandScape() {
        ll_portrait.setVisibility(View.GONE);
        ll_landscape.setVisibility(View.VISIBLE);
    }

    private void initGift() {
        giftControl = new GiftControl(UIUtils.getContext());

        //初始化礼物列表
        Button mBtnSendGift = (Button) findViewById(R.id.btn_send_gift);
        llgiftparent = (LinearLayout) findViewById(R.id.ll_gift_parent);
        mTvMoneyNum = (TextView) findViewById(R.id.toolbox_tv_num);

        giftControl.setGiftLayout(false, llgiftparent, 4)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画

        ll_portrait = (LinearLayout) findViewById(R.id.ll_portrait);
        ll_landscape = (LinearLayout) findViewById(R.id.ll_landscape);
        tvGiftNum = (TextView) findViewById(R.id.toolbox_tv_gift_num);
        btnGift = (ImageView) findViewById(R.id.toolbox_iv_face);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_gift);
        mViewpager = (ViewPager) findViewById(R.id.toolbox_pagers_face);
        mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
        mRootView = (RelativeLayout) findViewById(R.id.rl_root);
        mGiftLayout = (LinearLayout) findViewById(R.id.giftLayout);
        mGiftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里的作用是消费掉点击事件
            }
        });
        mGiftLayout.setVisibility(View.GONE);
        mBtnSendGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtil.getUser() != null) {
                    if (mGiftLayout.getVisibility() == View.VISIBLE) {
                        mGiftLayout.setVisibility(View.GONE);
                    } else {
                        mGiftLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    UIUtils.showTip("请先登录");
                }


            }
        });
        tvGiftNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showGiftDialog();
            }
        });
        basePreferences = new BasePreferences(this);
        mTvMoneyNum.setText(basePreferences.getPrefString("account"));

        btnGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mGiftName)) {
                    Toast.makeText(getApplication(), "你还没选择礼物呢", Toast.LENGTH_SHORT).show();
                } else {
                    String numStr = tvGiftNum.getText().toString();
                    if (!TextUtils.isEmpty(numStr)) {
                        int giftnum = Integer.parseInt(numStr);
                        if (giftnum == 0) {
                            return;
                        } else {
                            int account = Integer.valueOf(basePreferences.getPrefString("account"));
                            int GiftPrice = Integer.valueOf(mGiftPrice);

                            if (account - (GiftPrice * giftnum) < 0) {
                                UIUtils.showTip("蛙豆不足");
                                return;
                            }
                            basePreferences.setPrefString("account", account - (GiftPrice * giftnum));
                            mTvMoneyNum.setText(account - GiftPrice + "");


                            String phone = SPUtil.getUser().getResult().getUser().getPhone();
                            String a = phone.substring(0, 3);
                            String a1 = phone.substring(7, 11);

                            //这里最好不要直接new对象//直接显示礼物
                            giftModel = new GiftModel();
                            LogUtil.e(mGifturl + "," + mGid + "++++++++++++++");

                            //setSendUserPic为自己界面显示的礼物//mGifturl礼物界面显示的礼物
                            giftModel.setGiftId(mGid).setGiftName(mGiftName).setGiftCount(giftnum).setGiftPic(mGifturl)
                                    .setSendUserId("1234").setSendUserName(a + a1)
                                    .setSendUserPic(mGifturl).setSendGiftTime(System.currentTimeMillis())
                                    .setCurrentStart(currentStart);


                            if (currentStart) {
                                giftModel.setHitCombo(giftnum);
                            }
                            giftControl.loadGift(giftModel);

                            sendGift(giftnum);

                            ++giftTimes;
                            if (task == null) {
                                timekeeping();
                            } else {
                                recLen = 3;
                            }
                            giftTimesLast = giftTimes % 10;
                            totleNumber = giftnum + totleNumber;

                            if (giftTimesLast == 0 && giftTimes >= 10) {
                                httpGiftMoney(totleNumber + "");
                                totleNumber = totleNumber - giftTimes * giftnum;
                                giftTimes = giftTimes - 10;
                                LogUtil.e(totleNumber + "");
                            }
                            LogUtil.e(giftTimesLast + "," + totleNumber + "," + giftTimes + "===========================" + giftnum);

                        }
                    }
                }
            }
        });
        HttpGetGift(mGroupId);
    }


    /**
     * 转化为送礼物的模型
     *
     * @param datas
     * @return
     */
    private List<GiftModel> toGiftModel(List<GiftBean.ResultBean> datas) {
        List<GiftModel> giftModels = new ArrayList<>();
        GiftModel giftModel;
        for (int i = 0; i < datas.size(); i++) {
            GiftBean.ResultBean giftListBean = datas.get(i);
            giftModel = new GiftModel();
            giftModel
                    .setGiftId(giftListBean.getId() + "")
                    .setSendUserPic(HttpURL.IV_GIFT_HOST + giftListBean.getIcon())
                    .setGiftName(giftListBean.getName())
                    .setGiftPic(HttpURL.IV_GIFT_HOST + giftListBean.getGif())
                    .setGiftPrice(giftListBean.getPrice() + "");
            LogUtil.e("123456" + HttpURL.IV_GIFT_HOST + giftListBean.getIcon());
            giftModels.add(giftModel);
        }
        return giftModels;
    }


    /**
     * 请求礼物列表
     */
    private void HttpGetGift(String giftGroup) {
        LogUtil.e("请求侧边栏列表");
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.getGift);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("groupId", giftGroup);
        requestParams.addBodyParameter("order", "sort");
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("礼物-----", result);
                GiftBean giftBean = new Gson().fromJson(result, GiftBean.class);
                if (giftBean.getCode() == 0) {
                    GiftPanelControl giftPanelControl = new GiftPanelControl(LivingPlayActivity.this, mViewpager, mRecyclerView, mDotsLayout);
                    List<GiftModel> giftModels = toGiftModel(giftBean.getResult());
                    giftPanelControl.init(giftModels);//这里如果为null则加载本地礼物图片
                    giftPanelControl.setGiftListener(new GiftPanelControl.GiftListener() {
                        @Override
                        public void getGiftInfo(String giftPic, String giftName, String giftPrice, String gid) {
                            mGifturl = giftPic;
                            mGiftName = giftName;
                            mGiftPrice = giftPrice;
                            mGid = gid;
                            LogUtil.e(mGifturl + "+++++++++++++++++++");


                            if (totleNumber != 0) {
                                httpGiftMoney(totleNumber + "");
                            }
                            giftTimes = 0;
                            giftTimesLast = 0;
                            totleNumber = 0;

                        }
                    });

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

        });
    }


    private void httpGiftMoney(String num) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        if (SPUtil.getUser() != null) {
            RequestParams requestParams = new RequestParams(HttpURL.Pay);
            requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid", SPUtil.getUser().getResult().getUser().getUid() + "");
            requestParams.addBodyParameter("type", 4 + "");
            requestParams.addBodyParameter("vid", mVid + "");
            requestParams.addBodyParameter("money", mGiftPrice + "");
            requestParams.addBodyParameter("yid", mYid + "");
            requestParams.addBodyParameter("gid", mGid);
            requestParams.addBodyParameter("num", num);

            LogUtil.i("个人直播支付token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("个人直播支付uid = " + SPUtil.getUser().getResult().getUser().getUid());
            LogUtil.i("个人直播支付vid = " + mVid + "");
            LogUtil.i("个人直播支付yid = " + mYid);
            LogUtil.i("个人直播支付money = " + mGiftPrice + "");
            LogUtil.i("个人直播支付type = " + 3);
            LogUtil.i("个人直播支付mGid = " + mGid);

            //获取数据
            x.http().post(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    LogUtil.i("支付 = " + result);

                    GiveRewardBean giveBean = new Gson().fromJson(result, GiveRewardBean.class);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {


                }

                @Override
                public void onFinished() {

                }
            });

        } else {
            UIUtils.showTip("请登陆");
        }


    }

    /**
     * 计时重新发送验证码
     */
    private void timekeeping() {
        mTimer = new Timer();
        // UI thread
        // UI thread
        //时间到了就可以再次发送了
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        recLen--;
                        if (recLen < 0) {
                            mTimer.cancel();
                            if (totleNumber > 0)
                                LogUtil.i(totleNumber + "===========================");
                            httpGiftMoney(totleNumber + "");
                        }
                    }
                });
            }
        };
        //从现在起过10毫秒以后，每隔1000毫秒执行一次。
        mTimer.schedule(task, 10, 1000);    // timeTask
    }


    private void showGiftDialog() {
        final GiftDialogFrament giftDialogFrament = new GiftDialogFrament();
        giftDialogFrament.show(getFragmentManager(), "GiftDialogFrament");
        giftDialogFrament.setGiftListener(new GiftDialogFrament.GiftListener() {
            @Override
            public void giftNum(String giftNum) {
                tvGiftNum.setText(giftNum);
                giftDialogFrament.dismiss();
            }
        });
    }

    private void sendGift(int giftnum) {
        MessageBean messageBean = new MessageBean();
        messageBean.setUserAction(VedioContants.AVIMCMD_Custom_Gift);
        messageBean.setGiftCount(giftnum);
        messageBean.setGiftName(mGiftName);
        messageBean.setGiftPic(mGifturl);
        messageBean.setUserId(mGid);
        sendMessage(gson.toJson(messageBean), VedioContants.AVIMCMD_Custom_Gift);
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mGiftLayout.getVisibility() == View.VISIBLE) {
                    mGiftLayout.setVisibility(View.GONE);
                }
                break;
        }
        return super.onTouchEvent(event);
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
                if (SPUtil.getUser() != null) {
                    showMessageDialog();
                } else {
                    UIUtils.showTip("请先登录");
                }


            }
        });

        mEtRoomMessage = (EditText) findViewById(R.id.et_room_message);
        mBtnSendMassage = (Button) findViewById(R.id.btn_send_massage);
        mBtnSendMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = mEtRoomMessage.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    MessageBean messageBean = new MessageBean();
                    messageBean.setUserAction(VedioContants.AVIMCMD_Custom_Text);
                    messageBean.setMsg(message);
                    messageBean.setGiftPic(mGifturl);
                    sendMessage(gson.toJson(messageBean), VedioContants.AVIMCMD_Custom_Text);
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
                MessageBean messageBean = new MessageBean();
                messageBean.setUserAction(VedioContants.AVIMCMD_Custom_Text);
                messageBean.setMsg(message);
                messageBean.setGiftPic(mGifturl);
                sendMessage(gson.toJson(messageBean), VedioContants.AVIMCMD_Custom_Text);
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
                    UIUtils.showTip("游客登陆");
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

                    LogUtil.e(msg.getSender() + "-----------------");

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
                                    LogUtil.e(text + "-----------------");
                                    TCChatEntity entity = new TCChatEntity();
                                    entity.setSenderName(msg.getSenderProfile().getNickName());
                                    entity.setContext(messageBean.getMsg());
                                    entity.setType(TCConstants.TEXT_TYPE);
                                    notifyMsg(entity);

                                } else if (messageBean.getUserAction() == VedioContants.AVIMCMD_Custom_Gift) {

                                    showGift(messageBean, msg.getSenderProfile().getNickName() + "");
                                } else if (messageBean.getUserAction() == VedioContants.AVIMCMD_Custom_Exit) {
                                    if (isLiving(LivingPlayActivity.this)) {
                                        showComfirmDialog("主播关播了,下次再来", true);
                                    } else {
                                        finish();
                                    }
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

    private static boolean isLiving(AppCompatActivity activity) {

        if (activity == null) {
            Log.d("wisely", "activity == null");
            return false;
        }

        if (activity.isFinishing()) {
            Log.d("wisely", "activity is finishing");
            return false;
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {//android 4.2
//
//            if (activity.isDestroyed()) {
//                Log.d("wisely", "activity is destroy");
//                return false;
//            }
//        }

        return true;
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
                        showComfirmDialog("您的账号在其他地方登陆，请重新进入直播间", true);
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
                    UIUtils.hideKeyBoard(mMessage);
                    mMessageDialog.dismiss();

                    TCChatEntity entity = new TCChatEntity();
                    entity.setSenderName("我:");
                    entity.setContext(gson.fromJson(message, MessageBean.class).getMsg());
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

    /**
     * 显示确认消息
     *
     * @param msg     消息内容
     * @param isError true错误消息（必须退出） false提示消息（可选择是否退出）
     */
    public void showComfirmDialog(String msg, Boolean isError) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LivingPlayActivity.this, R.style.ConfirmDialogStyle);
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
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            mIvRoomImg.setVisibility(View.GONE);
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
