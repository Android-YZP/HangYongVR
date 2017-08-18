package com.hy.vrfrog.main.living.livingplay.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.hy.vrfrog.R;
import com.hy.vrfrog.main.living.im.TCGiftEntity;
import com.hy.vrfrog.main.living.im.TCSimpleUserInfo;
import com.hy.vrfrog.main.living.push.utils.TCUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.vr.cardboard.ThreadUtils.runOnUiThread;


/**
 * Created by teckjiang on 2016/8/21.
 * 直播头像列表Adapter
 */
public class TCGiftAvatarListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LinkedList<TCSimpleUserInfo> mUserAvatarList;
    Context mContext;
    //主播id
    private String mPusherId;
    //最大容纳量
    private final static int TOP_STORGE_MEMBER = 10;
    private int recLen= 6;
    private Timer mTimer  = new Timer();;
    private TimerTask task;


    public TCGiftAvatarListAdapter(Context context, String pusherId) {
        this.mContext = context;
        this.mPusherId = pusherId;
        this.mUserAvatarList = new LinkedList<>();
    }

    /**
     * 添加用户信息
     * @param userInfo 用户基本信息
     * @return 存在重复或头像为主播则返回false
     */
    public boolean addItem(TCSimpleUserInfo userInfo) {

        //去除主播头像
        if(userInfo.userid.equals(mPusherId))
            return false;

        //去重操作
        for (TCSimpleUserInfo tcSimpleUserInfo : mUserAvatarList) {
            if(tcSimpleUserInfo.userid.equals(userInfo.userid))
                return false;
        }

        //始终显示新加入item为第一位
        mUserAvatarList.add(0, userInfo);
        //超出时删除末尾项
        if(mUserAvatarList.size() > TOP_STORGE_MEMBER) {
            mUserAvatarList.remove(TOP_STORGE_MEMBER);
            notifyItemRemoved(TOP_STORGE_MEMBER);
        }
        notifyItemInserted(0);

        //定时删除，排序，



        return true;
    }


    private void TimerDelete(TCGiftEntity tcGiftEntity){
        // UI thread
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
                            removeItem(null);//6秒之后删除
                        }
                    }
                });
            }
        };
        //从现在起过10毫秒以后，每隔1000毫秒执行一次。
        mTimer.schedule(task, 10, 1000);    // timeTask
    }



    public void removeItem(String userId) {
        TCSimpleUserInfo tempUserInfo = null;

        for(TCSimpleUserInfo userInfo : mUserAvatarList)
            if(userInfo.userid.equals(userId))
                tempUserInfo = userInfo;


        if(null != tempUserInfo) {
            mUserAvatarList.remove(tempUserInfo);
            notifyDataSetChanged();
        }
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_user_avatar, parent, false);

        final AvatarViewHolder avatarViewHolder = new AvatarViewHolder(view);
        avatarViewHolder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCSimpleUserInfo userInfo = mUserAvatarList.get(avatarViewHolder.getAdapterPosition());
                Toast.makeText(mContext.getApplicationContext(),"当前点击用户： " + userInfo.userid, Toast.LENGTH_SHORT).show();
            }
        });

        return avatarViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TCUtils.showPicWithUrl(mContext, ((AvatarViewHolder)holder).ivAvatar,mUserAvatarList.get(position).headpic,
                R.drawable.face);
    }

    @Override
    public int getItemCount() {
        return mUserAvatarList != null? mUserAvatarList.size(): 0;
    }

    private class AvatarViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;

        public AvatarViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
